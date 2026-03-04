package org.sr_g3.view.impl;

import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.model.Product;
import org.sr_g3.utils.Colors;
import org.sr_g3.utils.Console;
import org.sr_g3.utils.ProductTableDesign;
import org.sr_g3.utils.Validator;
import org.sr_g3.utils.*;
import org.sr_g3.view.ProductView;

import java.util.Optional;

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

    }

    @Override
    public void save() {

    }
}
