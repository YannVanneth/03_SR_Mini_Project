package org.sr_g3.config.app;

import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.dao.StockManagementDaoImpl;
import org.sr_g3.model.Product;
import org.sr_g3.service.StockManagementFunctionality;
import org.sr_g3.utils.Colors;
import org.sr_g3.utils.Console;
import org.sr_g3.utils.ProductTableDesign;
import org.sr_g3.utils.Validator;
import org.sr_g3.view.ProgramUi;

import java.time.LocalDate;
import java.util.*;

public class StockManagementSystem implements StockManagementFunctionality {
    private static final List<Product> pendingInserts = new ArrayList<>();
    private static final Map<Long, Product> pendingUpdates = new HashMap<>();
    public static void addPendingInsert(Product product) {
        pendingInserts.add(product);
    }
    public static void addPendingUpdate(Long id, Product updatedProduct) {
        pendingUpdates.put(id, updatedProduct);
    }
    public static void run(String[] args) throws Exception {
        ProgramUi.run();
    }
    public static List<Product> getPendingInserts() {
        return new ArrayList<>(pendingInserts);
    }
    public static Map<Long, Product> getPendingUpdates() {
        return new HashMap<>(pendingUpdates);
    }

    public static void clearPendingChanges() {
        pendingInserts.clear();
        pendingUpdates.clear();
    }
    public static void clearAllPending() {
        pendingInserts.clear();
        pendingUpdates.clear();
    }

    @Override
    public void save() {

        if (pendingInserts.isEmpty() && pendingUpdates.isEmpty()) {
            Console.printSystemMessage("No pending changes to save.");
            return;
        }

        Console.print("Saving pending changes...", "-", 60, Colors.YELLOW, Colors.GREEN);

        boolean success = true;
        int insertedCount = 0;
        int updatedCount = 0;

        StockManagementDao dao = new StockManagementDaoImpl();


        for (Product product : pendingInserts) {
            try {
                dao.addStock(product);
                insertedCount++;
            } catch (Exception e) {
                Console.printErrorMessage("Failed to insert product: " + product.getName() + " → " + e.getMessage());
                success = false;
            }
        }


        for (Map.Entry<Long, Product> entry : pendingUpdates.entrySet()) {
            Long id = entry.getKey();
            Product updated = entry.getValue();

            try {
                dao.updateStock(id, updated);
                updatedCount++;
            } catch (Exception e) {
                Console.printErrorMessage("Failed to update product ID " + id + " → " + e.getMessage());
                success = false;
            }
        }


        if (success) {
            Console.printSuccessMessage("SAVE SUCCESSFUL!");
            Console.printSystemMessage("Inserted: " + insertedCount + " | Updated: " + updatedCount);
            clearAllPending();
        } else {
            Console.printErrorMessage("Some changes could not be saved. Please check messages above.");

        }

        System.out.println("Press Enter to continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    @Override
    public  void unSaved() {
        while (true) {
            Console.print("Unsaved Changes", "-", 60, Colors.YELLOW, Colors.PINK);

            System.out.println("'ui' for viewing insert products and 'uu' for viewing update products or 'b' for back to menu");

            String option = Console.input("Enter your option : ",
                    "[uU][iI]|[uU][uU]|[bB]",
                    "Invalid! Please use ui, uu or b only.");

            if (option == null) continue;

            switch (option.trim().toLowerCase()) {
                case "ui" -> showPendingInserts();
                case "uu" -> showPendingUpdates();
                case "b" -> {
                    return;
                }
                default -> Console.printErrorMessage("Invalid option!");
            }
        }
    }
    private static void showPendingInserts() {
        List<Product> list = getPendingInserts();

        if (list.isEmpty()) {
            Console.printSystemMessage("No pending INSERT products yet.");
            System.out.print("Enter to continue...");
            new Scanner(System.in).nextLine();
            return;
        }

        ProductTableDesign.printPendingTable(list, "PENDING INSERT PRODUCTS");
        System.out.print("Enter to continue...");
        new Scanner(System.in).nextLine();
    }

    private static void showPendingUpdates() {
        Map<Long, Product> map = getPendingUpdates();

        if (map.isEmpty()) {
            Console.printSystemMessage("No pending UPDATE products yet.");
            System.out.print("Enter to continue...");
            new Scanner(System.in).nextLine();
            return;
        }

        List<Product> list = new ArrayList<>();
        for (var entry : map.entrySet()) {
            Product p = entry.getValue();
            p.setProduct_id(entry.getKey());
            list.add(p);
        }

        ProductTableDesign.printPendingTable(list, "PENDING UPDATE PRODUCTS");
        System.out.print("Enter to continue...");
        new Scanner(System.in).nextLine();
    }

    @Override
    public void backup() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void setRow() {
    }
    @Override
    public void write() {
        System.out.println();

        StockManagementDao dao = new StockManagementDaoImpl();
        long nextId = dao.getNextProductId();

        if (nextId <= 0) {
            Console.printErrorMessage("Could not determine next product ID.");
            System.out.println("Enter to continue.....");
            new Scanner(System.in).nextLine();
            return;
        }

        System.out.println("ID: " + nextId);

        try {

            String name = Console.input(
                    "Input Product Name : ",
                    "[A-Za-z0-9\\s\\-\\.\\,]{3,100}",
                    "Invalid name! Use 3-100 characters (letters, numbers, space, - . , allowed)"
            ).trim();


            String priceStr = Console.input(
                    "Enter price: ",
                    Validator.floatRule(),
                    "Invalid price! Enter positive number (e.g. 12.5)"
            );
            double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                Console.printErrorMessage("Price must be greater than 0.");
                System.out.println("Enter to continue.....");
                new Scanner(System.in).nextLine();
                return;
            }


            String qtyStr = Console.input(
                    "Enter quantity: ",
                    Validator.numberRule(),
                    "Invalid quantity! Enter non-negative whole number"
            );
            int quantity = Integer.parseInt(qtyStr);
            if (quantity < 0) {
                Console.printErrorMessage("Quantity cannot be negative.");
                System.out.println("Enter to continue.....");
                new Scanner(System.in).nextLine();
                return;
            }

            // Create product
            Product newProduct = new Product();
            newProduct.setProduct_id(nextId);
            newProduct.setName(name);
            newProduct.setUnit_price(price);
            newProduct.setQuantity(quantity);
            newProduct.setImported_date(LocalDate.now());

            addPendingInsert(newProduct);


        } catch (NumberFormatException e) {
            Console.printErrorMessage("Invalid number format.");
        } catch (Exception e) {
            Console.printErrorMessage("Error adding product: " + e.getMessage());
        }

        System.out.println("Enter to continue.....");
        new Scanner(System.in).nextLine();
    }
    @Override
    public void readById() {
        System.out.println(); // spacing

        String idStr = Console.input(
                "Please input id to get record : ",
                Validator.numberRule(),
                "Please enter a valid positive number"
        );

        long id;
        try {
            id = Long.parseLong(idStr);
            if (id <= 0) {
                Console.printErrorMessage("ID must be a positive number.");
                System.out.println("\nEnter to continue...");
                new Scanner(System.in).nextLine();
                return;
            }
        } catch (NumberFormatException e) {
            Console.printErrorMessage("Invalid ID format.");
            System.out.println("\nEnter to continue...");
            new Scanner(System.in).nextLine();
            return;
        }

        StockManagementDao dao = new StockManagementDaoImpl();
        Optional<Product> optionalProduct = dao.getProductById(id);

        if (optionalProduct.isPresent()) {
            ProductTableDesign.printSingleProduct(optionalProduct.get());
        } else {
            Console.printErrorMessage("No product found with ID: " + id);
        }

        System.out.println("\nEnter to continue...");
        new Scanner(System.in).nextLine();
    }
    @Override
    public void searchByName() {
        System.out.println();

        String searchName = Console.input(
                "Input Product Name : ",
                "[A-Za-z0-9\\s\\-\\.\\,]{1,100}",
                "Invalid name! Use 1-100 characters (letters, numbers, space, - . , allowed)"
        ).trim();

        if (searchName.isEmpty()) {
            Console.printErrorMessage("Name cannot be empty.");
            System.out.println("\nEnter to continue...");
            new Scanner(System.in).nextLine();
            return;
        }

        StockManagementDao dao = new StockManagementDaoImpl();
        Optional<List<Product>> optionalResults = dao.searchByName(searchName);

        if (optionalResults.isPresent() && !optionalResults.get().isEmpty()) {
            List<Product> results = optionalResults.get();
            ProductTableDesign.printSearchResults(results, searchName);
        } else {
            Console.printErrorMessage("No products found matching '" + searchName + "'");
        }

        System.out.println("\nEnter to continue...");
        new Scanner(System.in).nextLine();
    }
    @Override
    public void updateProduct() {
        System.out.println();
        String idStr = Console.input(
                "Input ID to update: ",
                Validator.numberRule(),
                "Please enter a valid positive number"
        );

        long id;
        try {
            id = Long.parseLong(idStr);
            if (id <= 0) {
                Console.printErrorMessage("ID must be a positive number.");
                System.out.println("\nEnter to continue...");
                new Scanner(System.in).nextLine();
                return;
            }
        } catch (NumberFormatException e) {
            Console.printErrorMessage("Invalid ID format.");
            System.out.println("\nEnter to continue...");
            new Scanner(System.in).nextLine();
            return;
        }

        StockManagementDao dao = new StockManagementDaoImpl();
        Optional<Product> optionalProduct = dao.getProductById(id);

        if (optionalProduct.isEmpty()) {
            Console.printErrorMessage("No product found with ID: " + id);
            System.out.println("\nEnter to continue...");
            new Scanner(System.in).nextLine();
            return;
        }

        Product current = optionalProduct.get();

        List<Product> singleList = Collections.singletonList(current);
        ProductTableDesign.printSearchResults(singleList, "Current Product (ID: " + id + ")");

        while (true) {
            System.out.println();
            System.out.println("1. Name   2. Unit Price   3. Qty   4. All Field   5. Exit");
            String choiceStr = Console.input(
                    "Choose an option to update : ",
                    "[1-5]",
                    "Please choose 1 to 5 only"
            );

            int choice = Integer.parseInt(choiceStr);

            if (choice == 5) {
                System.out.println("Exit update.");
                System.out.println("\nEnter to continue...");
                new Scanner(System.in).nextLine();
                return;
            }

            Product updated = new Product();
            updated.setProduct_id(current.getProduct_id());
            updated.setName(current.getName());
            updated.setUnit_price(current.getUnit_price());
            updated.setQuantity(current.getQuantity());
            updated.setImported_date(current.getImported_date());

            boolean valid = true;

            switch (choice) {
                case 1:
                    String newName = Console.input(
                            "Input Product Name : ",
                            "[A-Za-z0-9\\s\\-\\.\\,]{3,100}",
                            "Invalid name"
                    ).trim();
                    updated.setName(newName);
                    break;

                case 2:
                    String priceStr = Console.input(
                            "Enter price: ",
                            Validator.floatRule(),
                            "Invalid price"
                    );
                    double newPrice = 0;
                    try {
                        newPrice = Double.parseDouble(priceStr);
                        if (newPrice <= 0) {
                            Console.printErrorMessage("Price must be > 0");
                            valid = false;
                        } else {
                            updated.setUnit_price(newPrice);
                        }
                    } catch (NumberFormatException e) {
                        Console.printErrorMessage("Invalid price format");
                        valid = false;
                    }
                    break;

                case 3:
                    String qtyStr = Console.input(
                            "Input Quantity: ",
                            Validator.numberRule(),
                            "Invalid quantity"
                    );
                    int newQty = 0;
                    try {
                        newQty = Integer.parseInt(qtyStr);
                        if (newQty < 0) {
                            Console.printErrorMessage("Quantity cannot be negative");
                            valid = false;
                        } else {
                            updated.setQuantity(newQty);
                        }
                    } catch (NumberFormatException e) {
                        Console.printErrorMessage("Invalid quantity format");
                        valid = false;
                    }
                    break;

                case 4:
                    String allName = Console.input(
                            "Input Product Name : ",
                            "[A-Za-z0-9\\s\\-\\.\\,]{3,100}",
                            "Invalid name"
                    ).trim();

                    double allPrice = 0;
                    boolean priceValid = true;
                    String allPriceStr = Console.input(
                            "Enter price: ",
                            Validator.floatRule(),
                            "Invalid price"
                    );
                    try {
                        allPrice = Double.parseDouble(allPriceStr);
                        if (allPrice <= 0) {
                            Console.printErrorMessage("Price must be > 0");
                            priceValid = false;
                        }
                    } catch (NumberFormatException e) {
                        Console.printErrorMessage("Invalid price format");
                        priceValid = false;
                    }

                    int allQty = 0;
                    boolean qtyValid = true;
                    String allQtyStr = Console.input(
                            "Input Quantity: ",
                            Validator.numberRule(),
                            "Invalid quantity"
                    );
                    try {
                        allQty = Integer.parseInt(allQtyStr);
                        if (allQty < 0) {
                            Console.printErrorMessage("Quantity cannot be negative");
                            qtyValid = false;
                        }
                    } catch (NumberFormatException e) {
                        Console.printErrorMessage("Invalid quantity format");
                        qtyValid = false;
                    }

                    if (priceValid && qtyValid) {
                        updated.setName(allName);
                        updated.setUnit_price(allPrice);
                        updated.setQuantity(allQty);
                    } else {
                        valid = false;
                    }
                    break;

                default:
                    Console.printErrorMessage("Invalid choice");
                    valid = false;
                    break;
            }

            if (valid) {
                addPendingUpdate(id, updated);
                Console.printSuccessMessage("update Successfully");
                System.out.println("\nEnter to continue...");
                new Scanner(System.in).nextLine();
                return;
            }
        }
    }
}