package org.sr_g3.utils;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.sr_g3.model.Product;

import java.util.List;

public class ProductTableDesign {

    public static void printTable(List<Product> products, int currentPage, int totalPages, int totalCount) {

        Table table = new Table(5, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);

        // Column width
        table.setColumnWidth(0, 10, 5);   // ID
        table.setColumnWidth(1, 25, 40);  // Name
        table.setColumnWidth(2, 15, 20);  // Unit Price
        table.setColumnWidth(3, 10, 10);  // QTY
        table.setColumnWidth(4, 15, 20);  // Import Date

        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        CellStyle left = new CellStyle(CellStyle.HorizontalAlign.LEFT);
        CellStyle right = new CellStyle(CellStyle.HorizontalAlign.RIGHT);

        // ===== Header =====
        table.addCell( Colors.BLUE + "ID", center);
        table.addCell(Colors.BLUE + "Name", center);
        table.addCell(Colors.BLUE + "Unit Price", center);
        table.addCell(Colors.BLUE + "QTY", center);
        table.addCell(Colors.BLUE + "Import Date" + Colors.WHITE, center);

        // ===== Data =====
        for (Product product : products) {
            table.addCell(Colors.GREEN + String.valueOf(product.getProduct_id()), center);
            table.addCell(Colors.YELLOW + product.getName(), center);
            table.addCell(Colors.PURPLE + String.valueOf(product.getUnit_price()), center);
            table.addCell(Colors.PINK + String.valueOf(product.getQuantity()), center);
            table.addCell(Colors.GREEN + String.valueOf(product.getImported_date()) + Colors.WHITE, center);
        }

        // ===== Footer=====
        table.addCell("Page : " + Colors.YELLOW +  currentPage +  Colors.WHITE + " of " + Colors.GREEN +  totalPages, center, 2);
        table.addCell("Total Record : " + Colors.GREEN + totalCount,  center, 3);

        System.out.println(table.render());
    }

    public static void printSingleProduct(Product product) {
        if (product == null) {
            System.out.println(Colors.RED + "No product to display." + Colors.WHITE);
            return;
        }

        Table table = new Table(5, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);

        table.setColumnWidth(0, 10, 10);
        table.setColumnWidth(1, 25, 40);
        table.setColumnWidth(2, 15, 20);
        table.setColumnWidth(3, 10, 10);
        table.setColumnWidth(4, 15, 20);

        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.CENTER);

        table.addCell(Colors.BLUE + "ID", center);
        table.addCell(Colors.BLUE + "Name", center);
        table.addCell(Colors.BLUE + "Unit Price", center);
        table.addCell(Colors.BLUE + "QTY", center);
        table.addCell(Colors.BLUE + "Import Date" + Colors.WHITE, center);

        // Single row
        table.addCell(Colors.GREEN + String.valueOf(product.getProduct_id()), center);
        table.addCell(Colors.YELLOW + product.getName(), center);
        table.addCell(Colors.PURPLE + String.valueOf(product.getUnit_price()), center);
        table.addCell(Colors.PINK + String.valueOf(product.getQuantity()), center);
        table.addCell(Colors.GREEN + String.valueOf(product.getImported_date()) + Colors.WHITE, center);

        System.out.println(table.render());
    }

}
