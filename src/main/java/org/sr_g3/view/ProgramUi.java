package org.sr_g3.view;

import org.sr_g3.config.app.StockManagementSystem;
import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.dao.StockManagementDaoImpl;
import org.sr_g3.utils.Colors;
import org.sr_g3.utils.Console;
import org.sr_g3.utils.ProductTableDesign;
import org.sr_g3.utils.Validator;
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
            ProductTableDesign.printTable(
                    stockManagementDao.fetchStock(limit, offset),
                    currentPage,
                    totalPages,
                    totalRecords
            );
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
                        system.write();
                        break inputMenuBlock;
                    }
                    // Read (id)
                    case "R" -> {
                        system.readById();
                        break inputMenuBlock;
                    }

                    // Update
                    case "U" -> {
                        system.updateProduct();
                        break inputMenuBlock;
                    }

                    // Delete
                    case "D" -> {
                        System.out.println("delete");
                        break inputMenuBlock;
                    }

                    // Search (name)
                    case "S" -> {
                        system.searchByName();
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
