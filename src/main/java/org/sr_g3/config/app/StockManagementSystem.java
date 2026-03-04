package org.sr_g3.config.app;

import org.sr_g3.controller.StockController;
import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.service.StockManagementFunctionality;
import org.sr_g3.utils.Colors;
import org.sr_g3.utils.Console;
import org.sr_g3.utils.ProductTableDesign;
import org.sr_g3.utils.Validator;

import java.util.*;

public class StockManagementSystem implements StockManagementFunctionality {

    private final StockController stockController = new StockController();
    private final StockManagementDao stockManagementDao = stockController.getStockManagementDao();

    public void run(String[] args) {

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
                        gotoView();
                        break inputMenuBlock;
                    }

                    // Write
                    case "W" -> {
                        stockController.write();
                        break inputMenuBlock;
                    }

                    // Read (id)
                    case "R" -> {
                        stockController.read();
                        break inputMenuBlock;
                    }

                    // Update
                    case "U" -> {
                        stockController.update();
                        break inputMenuBlock;
                    }

                    // Delete
                    case "D" -> {
                        stockController.delete();
                        break inputMenuBlock;
                    }

                    // Search (name)
                    case "S" -> {
                        stockController.searchByName();
                        break inputMenuBlock;
                    }

                    // Save
                    case "SA" -> {
                        stockController.save();
                        break inputMenuBlock;
                    }

                    // Unsaved
                    case "UN" -> {
                        stockController.unSaved();
                        break inputMenuBlock;
                    }

                    // Backup
                    case "BA" -> {
                        backup();
                        break inputMenuBlock;
                    }

                    // Restore
                    case "RE" -> {
                        restore();
                        break inputMenuBlock;
                    }

                    // Set rows
                    case "SE" -> {
                        setRow(limit);
                        break inputMenuBlock;
                    }

                    // Exit
                    case "E" -> {
                        exit();
                        break program;
                    }

                    // default
                    default -> Console.printErrorMessage("Invalid text! please choose only text in menu option.");
                }
            }
        }
    }

    @Override
    public void save() {

    }

    @Override
    public void unSaved() {

    }

    @Override
    public void backup() {

    }

    @Override
    public void restore() {

    }

    public void exit() {

    }

    public void gotoView(){

    }

    public void setRow(int limit) {
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
    }
}