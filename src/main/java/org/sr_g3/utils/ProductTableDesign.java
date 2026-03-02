package org.sr_g3.utils;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import java.util.List;

public class ProductTableDesign {

    public static void printTable(List<String[]> products,
                                  int currentPage,
                                  int totalPages) {

        Table table = new Table(5, BorderStyle.UNICODE_BOX_HEAVY_BORDER, ShownBorders.ALL);

        // Column width
        table.setColumnWidth(0, 10, 5);   // ID
        table.setColumnWidth(1, 25, 40);  // Name
        table.setColumnWidth(2, 15, 20);  // Unit Price
        table.setColumnWidth(3, 10, 10);  // QTY
        table.setColumnWidth(4, 15, 20);  // Import Date

        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);
        CellStyle left = new CellStyle(CellStyle.HorizontalAlign.left);
        CellStyle right = new CellStyle(CellStyle.HorizontalAlign.right);

        // ===== Header =====
        table.addCell("ID", center);
        table.addCell("Name", center);
        table.addCell("Unit Price", center);
        table.addCell("QTY", center);
        table.addCell("Import Date", center);

        // ===== Data =====
        for (String[] product : products) {
            table.addCell(product[0], center);
            table.addCell(product[1], left);
            table.addCell(product[2], right);
            table.addCell(product[3], right);
            table.addCell(product[4], center);
        }

        // ===== Footer=====
        table.addCell("Page : " + currentPage + " of " + totalPages, center, 2);
        table.addCell("Total Record : " + products.size(), center, 3);

        System.out.println(table.render());
    }
}
