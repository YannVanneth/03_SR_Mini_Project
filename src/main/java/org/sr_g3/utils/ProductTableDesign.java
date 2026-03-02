package org.sr_g3.utils;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.sr_g3.model.Product;

import java.util.List;

public class ProductTableDesign {

    public static void printTable(List<Product> products,
                                  int currentPage,
                                  int totalPages) {

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
        table.addCell("ID", center);
        table.addCell("Name", center);
        table.addCell("Unit Price", center);
        table.addCell("QTY", center);
        table.addCell("Import Date", center);

        // ===== Data =====
        for (Product product : products) {
            table.addCell(String.valueOf(product.getProduct_id()), center);
            table.addCell(product.getName(), center);
            table.addCell(String.valueOf(product.getUnit_price()), center);
            table.addCell(String.valueOf(product.getQuantity()), center);
            table.addCell(String.valueOf(product.getImported_date()), center);
        }

        // ===== Footer=====
        table.addCell("Page : " + currentPage + " of " + totalPages, center, 2);
        table.addCell("Total Record : " + products.size(), center, 3);

        System.out.println(table.render());
    }
}
