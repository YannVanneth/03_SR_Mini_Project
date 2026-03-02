package org.sr_g3.view;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.sr_g3.utils.Colors;
import org.sr_g3.utils.Console;
import org.sr_g3.utils.Validator;

import java.util.Arrays;
import java.util.List;


public class ProgramUi {

    public static void run() {

    }

    public static void main(String[] args) {

        List<String> tableHeaders = Arrays.asList("ID", "Name", "Unit Price", "Qty", "Import Date");

        Table table = new Table(5, BorderStyle.UNICODE_ROUND_BOX, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);

        table.setColumnWidth(0, 15, 25);
        table.setColumnWidth(1, 25, 25);
        table.setColumnWidth(2, 20, 25);
        table.setColumnWidth(3, 18, 25);
        table.setColumnWidth(4, 20, 25);

        tableHeaders.forEach(str -> table.addCell(Colors.BLUE + str + Colors.WHITE, cellStyle));

        program:
        while (true) {
            Console.print("Stock Management System", "=", 80, Colors.GREEN, Colors.BLUE);
            System.out.println(table.render());

            inputMenuBlock:
            while (true) {
                System.out.println();
                String menuInput = Console.input("Please Choose an Option() : ", Validator.CharacterRule(), "Invalid input! please enter only text.");

                if (menuInput == null) {
                    Console.printErrorMessage("Input cannot be empty.");
                    continue;
                }

                switch (menuInput.trim().toUpperCase()) {

                    // Next Page
                    case "N" -> {
                        System.out.println("next-page");
                        break inputMenuBlock;
                    }

                    // Previous Page
                    case "P" -> {
                        System.out.println("previous-page");
                        break inputMenuBlock;
                    }

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
