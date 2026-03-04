package org.sr_g3.config.app;

import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.dao.StockManagementDaoImpl;
import org.sr_g3.model.Product;
import org.sr_g3.service.StockManagementFunctionality;
import org.sr_g3.utils.Colors;
import org.sr_g3.utils.Console;
import org.sr_g3.utils.ProductTableDesign;
import org.sr_g3.view.ProgramUi;

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
}