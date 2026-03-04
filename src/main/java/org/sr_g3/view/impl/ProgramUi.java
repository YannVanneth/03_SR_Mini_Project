package org.sr_g3.view.impl;

import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.model.Product;
import org.sr_g3.utils.Colors;
import org.sr_g3.utils.Console;
import org.sr_g3.utils.ProductTableDesign;
import org.sr_g3.utils.Validator;
import org.sr_g3.utils.*;
import org.sr_g3.view.ProductView;

import java.awt.*;
import java.lang.invoke.StringConcatFactory;
import java.util.Optional;
import java.util.Scanner;

public class ProgramUi implements ProductView {

    private final DbBackupRestoreUtil dbBackupRestoreUtil = new  DbBackupRestoreUtil();

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
                        System.out.println("goto-page");
                        break inputMenuBlock;
                    }

                    // Write
                    case "W" -> {
                        write();
                        break inputMenuBlock;
                    }

                    // Read (id)
                    case "R" -> {
                        System.out.println("read-by-id");
                        break inputMenuBlock;
                    }

                    // Update
                    case "U" -> {
                        System.out.println("update");
                        break inputMenuBlock;
                    }

                    // Delete
                    case "D" -> {
                        deleteProduct(stockManagementDao);
                        break inputMenuBlock;
                    }

                    // Search (name)
                    case "S" -> {
                        System.out.println("search-by-name");
                        break inputMenuBlock;
                    }

                    // Save
                    case "SA" -> {
                        System.out.println("save");
                        break inputMenuBlock;
                    }

                    // Unsaved
                    case "UN" -> {
                        System.out.println("unsaved");
                        break inputMenuBlock;
                    }

                    // Backup
                    case "BA" -> {
                        dbBackupRestoreUtil.backupPGSQL(dbBackupRestoreUtil.getVersion());

                        System.out.println("backup");
                        break inputMenuBlock;
                    }

                    // Restore
                    case "RE" -> {
                        dbBackupRestoreUtil.showBackupMenu();
                        break inputMenuBlock;
                    }

                    // Set rows
                    case "SE" -> {
                        limit = setRow();
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

        Product model = new Product();

        Console.print("Write Product", "=", 50);
        String name = Console.input("Please enter name  : ", Validator.stringRule());
        String price = Console.input("Please enter price : ", Validator.floatRule());
        String qty = Console.input("Please enter quantity : ", Validator.numberRule());
        Console.print(Colors.YELLOW + "Enter to be continue....." + Colors.WHITE);

        model.setName(name);
        model.setQuantity(Integer.parseInt(qty));
        model.setUnit_price(Double.parseDouble(price));

        return model;
    }

    @Override
    public Optional<Product> read() {
        return Optional.empty();
    }

    @Override
    public Optional<Product> searchByName() {
        return Optional.empty();
    }

    @Override
    public Optional<Product> updateProduct() {
        return Optional.empty();
    }

    @Override
    public void gotoView() {

    }

    @Override
    public void deleteProduct(StockManagementDao stockManagementDao) {
        String id;
        Optional<Product> product;

        out:
        while (true) {
            id = Console.input(Colors.YELLOW + "Please input id to delete : " + Colors.WHITE, Validator.numberRule(), "Invalid input! Please enter only number");
            if (id == null) {
                continue;
            }

            if (Integer.parseInt(id) <= 0) {
                Console.printErrorMessage("ID must be greater than 0.");
            } else {
                long productId = Long.parseLong(id);
                System.out.println(productId);
                product = stockManagementDao.getProductById(productId);
                System.out.println(product);
                if (product.isPresent()) {
                    ProductTableDesign.printSingleProduct(product.get());
                    while (true) {
                        String commitDelete = Console.input("Are you sure you want to delete product id : ", Validator.CharacterRule(), "Please enter text only.");
                        if (commitDelete == null) continue;
                        if (commitDelete.equalsIgnoreCase("y")) {
                            stockManagementDao.deleteStockById(productId);
                            Console.printSuccessMessage("Delete Successfully!");
                            Console.waitEnter();
                            break out;
                        }
                        else if (commitDelete.equalsIgnoreCase("n")) {
                            Console.printSystemMessage("Cancel Deleting...");
                            Console.waitEnter();
                            break out;
                        }
                        else {
                            Console.printErrorMessage("Please enter only y/n.");
                        }
                    }
                } else {
                    Console.printErrorMessage("Item do not exist.");
                }
            }
        }
    }

    @Override
    public void backUp() {

    }

    @Override
    public void restore() {

    }

    @Override
    public int setRow() {
        String strNewRowPerPage;
        while (true) {
            strNewRowPerPage = Console.input("Please input number of row per page : ", Validator.numberRule(), "Please enter number only");
            if (strNewRowPerPage == null) {
                continue;
            }
            if (Integer.parseInt(strNewRowPerPage) > 0 && Integer.parseInt(strNewRowPerPage) < 100) break;
            Console.printErrorMessage("Number must be bigger than 0 and must be smaller than 100.");
        }
        return Integer.parseInt(strNewRowPerPage);
    }

    @Override
    public void unSave() {

    }

    @Override
    public void save() {

    }
}
