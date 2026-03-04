package org.sr_g3.view;

import org.sr_g3.config.app.StockManagementSystem;
import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.dao.StockManagementDaoImpl;
import org.sr_g3.model.Product;
import org.sr_g3.utils.Colors;
import org.sr_g3.utils.Console;
import org.sr_g3.utils.ProductTableDesign;
import org.sr_g3.utils.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProgramUi {

    static StockManagementDao stockManagementDao = new StockManagementDaoImpl();
    private static final StockManagementSystem system = new StockManagementSystem();

    public static void run() {

        int limit = 5;
        int currentPage = 1;

        program:
        while (true) {
            int offset = (currentPage - 1) * limit;
            int totalRecords = stockManagementDao.countTotalRecords();
            int totalPages = (int) Math.ceil((double) totalRecords / limit);

            System.out.println();
            Console.print("Stock Management System", "=", 57, Colors.GREEN, Colors.BLUE);
            ProductTableDesign.printTable(stockManagementDao.fetchStock(limit,offset), currentPage, totalPages);
            Console.displayTableMenu();

            inputMenuBlock:
            while (true) {
                String menuInput = Console.input("Please Choose an Option() : ", Validator.CharacterRule(), "Invalid input! please enter only text.");

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
                        System.out.println("goto-page");
                        break inputMenuBlock;
                    }

                    // Write
                    case "W" -> {
                        System.out.println();
                        StockManagementDao dao = new StockManagementDaoImpl();
                        long nextId = dao.getNextProductId();

                        if (nextId <= 0) {
                            Console.printErrorMessage("Could not determine next product ID.");
                            break inputMenuBlock;
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
                                break inputMenuBlock;
                            }


                            String qtyStr = Console.input(
                                    "Enter quantity: ",
                                    Validator.numberRule(),
                                    "Invalid quantity! Enter non-negative integer"
                            );
                            int quantity = Integer.parseInt(qtyStr);
                            if (quantity < 0) {
                                Console.printErrorMessage("Quantity cannot be negative");
                                break inputMenuBlock;
                            }

                            Product newProduct = new Product();
                            newProduct.setProduct_id(nextId);
                            newProduct.setName(name);
                            newProduct.setUnit_price(price);
                            newProduct.setQuantity(quantity);
                            newProduct.setImported_date(LocalDate.now());

                            StockManagementSystem.addPendingInsert(newProduct);



                        } catch (Exception e) {
                            Console.printErrorMessage("Error: " + e.getMessage());
                        }

                        System.out.println("Enter to continue.....");
                        new Scanner(System.in).nextLine();

                        break inputMenuBlock;
                    }
                    // Read (id)
                    case "R" -> {
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
                                break inputMenuBlock;
                            }
                        } catch (NumberFormatException e) {
                            Console.printErrorMessage("Invalid ID format.");
                            break inputMenuBlock;
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

                        break inputMenuBlock;
                    }

                    // Update
                    case "U" -> {
                        System.out.println("update");
                        break inputMenuBlock;
                    }

                    // Delete
                    case "D" -> {
                        System.out.println("delete");
                        break inputMenuBlock;
                    }

                    // Search (name)
                    case "S" -> {
                        System.out.println("search-by-name");
                        break inputMenuBlock;
                    }

                    // Save
                    case "SA" -> {
                        system.save();
                        break inputMenuBlock;
                    }

                    // Unsaved
                    case "UN" -> {
                        system.unSaved();
                        break inputMenuBlock;
                    }

                    // Backup
                    case "BA" -> {
                        System.out.println("backup");
                        break inputMenuBlock;
                    }

                    // Restore
                    case "RE" -> {
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
}
