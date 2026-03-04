package org.sr_g3.view.impl;

import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.dao.StockManagementDaoImpl;
import org.sr_g3.model.Product;
import org.sr_g3.model.ProductManager;
import org.sr_g3.utils.Colors;
import org.sr_g3.utils.Console;
import org.sr_g3.utils.ProductTableDesign;
import org.sr_g3.utils.Validator;
import org.sr_g3.utils.*;
import org.sr_g3.view.ProductView;

import java.time.LocalDate;
import java.util.*;

public class ProgramUi implements ProductView {

//    private final DbBackupRestoreUtil dbBackupRestoreUtil = new  DbBackupRestoreUtil();

    public void run(StockManagementDao stockManagementDao) {

        int limit = 5;
        int currentPage = 1;

        program:
        while (true) {
            int offset = (currentPage - 1) * limit;
            int totalRecords = stockManagementDao.countTotalRecords();
            int totalPages = (int) Math.ceil((double) totalRecords / limit);

            System.out.println();
            Console.print("Stock Management System", "=", 57, Colors.GREEN, Colors.BLUE);
            ProductTableDesign.printTable(stockManagementDao.fetchStock(limit,offset), currentPage, totalPages, totalRecords);
            Console.displayTableMenu();

            inputMenuBlock:
            while (true) {
                String menuInput = Console.input("Please Choose an Option() : ", Validator.CharacterRule(),
                        "Invalid input! please enter only text.");

                if (menuInput == null) {
                    Console.printErrorMessage("Input cannot be empty.");
                    continue;
                }

                switch (menuInput.trim().toUpperCase()) {

                    // Next Page
                    case "N" -> {
                        if (currentPage < totalPages) currentPage++;
                        else Console.printErrorMessage("You are already on the last page.");
                        break inputMenuBlock;
                    }

                    // Previous Page
                    case "P" -> {
                        if (currentPage > 1) currentPage--;
                        else Console.printErrorMessage("You are already on the first page.");
                        break inputMenuBlock;
                    }

                    // First Page
                    case "F" -> {
                        currentPage = 1;
                        break inputMenuBlock;
                    }

                    // Last Page
                    case "L" -> {
                        currentPage = totalPages;
                        break inputMenuBlock;
                    }

                    // Goto
                    case "G" -> {
                        String np = Console.input("Enter page that you want to go : ", Validator.numberRule());

                        currentPage = Integer.parseInt(np);

                        break inputMenuBlock;
                    }

                    // Write
                    case "W" -> {
                        write();
                        break inputMenuBlock;
                    }

                    // Read (id)
                    case "R" -> {
                        read();
                        break inputMenuBlock;
                    }

                    // Update
                    case "U" -> {
                        updateProduct();
                        break inputMenuBlock;
                    }

                    // Delete
                    case "D" -> {
                        deleteProduct();
                        break inputMenuBlock;
                    }
                    // Search (name)
                    case "S" -> {
                        searchByName();
                        break inputMenuBlock;
                    }

                    // Save
                    case "SA" -> {
//                        system.save();
                        save();
                        break inputMenuBlock;
                    }

                    // Unsaved
                    case "UN" -> {
//                        system.unSaved();
                        unSave();
                        break inputMenuBlock;
                    }

                    // Backup
                    case "BA" -> {
//                        dbBackupRestoreUtil.backupPGSQL(dbBackupRestoreUtil.getVersion());
                        System.out.println("backup");
                        break inputMenuBlock;
                    }

                    // Restore
                    case "RE" -> {
//                        dbBackupRestoreUtil.showBackupMenu();
                        System.out.println("restore");
                        break inputMenuBlock;
                    }

                    // Set rows
                    case "SE" -> {
                        String strNewRowPerPage;
                        while (true) {
                            strNewRowPerPage = Console.input("Please input number of row per page : ", Validator.numberRule(), "Please enter number only");
                            if (strNewRowPerPage == null) {
                                continue;
                            }
                            if (Integer.parseInt(strNewRowPerPage) > 0 && Integer.parseInt(strNewRowPerPage) < 100) break;
                            Console.printErrorMessage("Number must be bigger than 0 and must be smaller than 100.");
                        }
                        limit = Integer.parseInt(strNewRowPerPage);
                        break inputMenuBlock;
                    }

                    // Exit
                    case "E" -> {
                        Console.printSystemMessage("Exiting Application...");
                        break program;
                    }

                    // default
                    default -> Console.printErrorMessage("Invalid text! please choose only text in menu option.");
                }
            }
        }
    }

    public Product write(){
        System.out.println();
        StockManagementDao dao = new StockManagementDaoImpl();
        long nextId = dao.getNextProductId();

        if (nextId <= 0) {
            Console.printErrorMessage("Could not determine next product ID.");
            return null;
        }

        System.out.println("ID: " + nextId);

        try {
            String name = Console.input(
                    "Input Product Name : ",
                    "[A-Za-z0-9\\s\\-\\.\\,]{3,100}",
                    "Invalid name! (3-100 chars, letters/numbers/space/-.,)"
            ).trim();


            String priceStr = Console.input(
                    "Enter price: ",
                    Validator.floatRule(),
                    "Invalid price! Enter positive number"
            );
            double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                Console.printErrorMessage("Price must be > 0");
                return null;
            }


            String qtyStr = Console.input(
                    "Enter quantity: ",
                    Validator.numberRule(),
                    "Invalid quantity! Enter non-negative integer"
            );
            int quantity = Integer.parseInt(qtyStr);

            if (quantity < 0) {
                Console.printErrorMessage("Quantity cannot be negative");
                return null;
            }

            Product newProduct = new Product();
            newProduct.setProduct_id(nextId);
            newProduct.setName(name);
            newProduct.setUnit_price(price);
            newProduct.setQuantity(quantity);
            newProduct.setImported_date(LocalDate.now());

            return newProduct;

        } catch (Exception e) {
            Console.printErrorMessage("Error: " + e.getMessage());
        }

        System.out.println("Enter to continue.....");
        new Scanner(System.in).nextLine();

        return null;
    }

    @Override
    public Optional<Product> read() {
        System.out.println();

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
                return Optional.empty();
            }
        } catch (NumberFormatException e) {
            Console.printErrorMessage("Invalid ID format.");
            return Optional.empty();
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
        return Optional.empty();
    }

    @Override
    public Optional<Product> searchByName() {
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
            return Optional.empty();
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
        return Optional.empty();
    }

    @Override
    public Optional<Product> updateProduct() {
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
                return Optional.empty();
            }
        } catch (NumberFormatException e) {
            Console.printErrorMessage("Invalid ID format.");
            System.out.println("\nEnter to continue...");
            new Scanner(System.in).nextLine();
            return Optional.empty();
        }

        StockManagementDao dao = new StockManagementDaoImpl();
        Optional<Product> optionalProduct = dao.getProductById(id);

        if (optionalProduct.isEmpty()) {
            Console.printErrorMessage("No product found with ID: " + id);
            System.out.println("\nEnter to continue...");
            new Scanner(System.in).nextLine();
            return Optional.empty();
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
                return Optional.empty();
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
                ProductManager.addUpdatedProduct(id, updated);
                Console.printSuccessMessage("update Successfully");
                System.out.println("\nEnter to continue...");
                new Scanner(System.in).nextLine();
                return Optional.empty();
            }
        }
    }

    @Override
    public void gotoView() {

    }

    @Override
    public boolean deleteProduct() {
        return false;
    }

    @Override
    public void backUp() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void setRow() {

    }

    @Override
    public void unSave() {
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

    @Override
    public void save() {
        if (ProductManager.getProductList().isEmpty() && ProductManager.getUpdatedProductList().isEmpty()) {
            Console.printSystemMessage("No pending changes to save.");
            return;
        }

        Console.print("Saving pending changes...", "-", 60, Colors.YELLOW, Colors.GREEN);

        boolean success = true;
        int insertedCount = 0;
        int updatedCount = 0;

        StockManagementDao dao = new StockManagementDaoImpl();


        for (Product product : ProductManager.getProductList()) {
            try {
                dao.addStock(product);
                insertedCount++;
            } catch (Exception e) {
                Console.printErrorMessage("Failed to insert product: " + product.getName() + " → " + e.getMessage());
                success = false;
            }
        }


        for (Map.Entry<Long, Product> entry : ProductManager.getUpdatedProductList().entrySet()) {
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
            ProductManager.clearProductList();
        } else {
            Console.printErrorMessage("Some changes could not be saved. Please check messages above.");

        }

        System.out.println("Press Enter to continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    private void showPendingInserts() {
        List<Product> list = ProductManager.getProductList();

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

    private void showPendingUpdates() {
        Map<Long, Product> map = ProductManager.getUpdatedProductList();

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


}
