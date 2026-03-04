package org.sr_g3.utils;

import java.awt.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class Console {

    public static void print(String message){
        System.out.println(message);
    }

    public static void waitEnter() {
        System.out.println();
        System.out.println(Colors.YELLOW + "Press enter to continue..." + Colors.WHITE);
        new Scanner(System.in).nextLine();
    }

    public static void print(String message,String pattern, int length){
        print(pattern.repeat(length/2) + " "  + message + " " + pattern.repeat(length/2));
    }

    public static void print(String message, String pattern, int length, String headerColor, String lineColor){

        if(headerColor == null || lineColor == null || pattern == null || message == null){
            printErrorMessage("Invalid Input");
            return;
        }

        if(!lineColor.startsWith("\u001B") || !headerColor.startsWith("\u001B")){
            printErrorMessage("Invalid Color format");
            return;
        }

        print(lineColor + pattern.repeat(length/2)
                + headerColor + " " + message + " "
                + lineColor + pattern.repeat(length/2) + Colors.WHITE);
    }

    public static void print(String message,String leftPattern, String rightPattern, int length){
        print(leftPattern.repeat(length/2)
                + " " + message + " "
                + rightPattern.repeat(length/2));
    }

    public static void printListOption(String ...options){
        for(int i = 0; i < options.length; i++){
            print((i + 1) + ". " + options[i]);
        }
    }

    public static void printErrorMessage(String message){
        System.out.println(Colors.RED +  "[!] " + message + Colors.WHITE);
    }

    public static void printSuccessMessage(String message){
        System.out.println(Colors.GREEN +  "[✔] " + message + Colors.WHITE);
    }

    public static void printSystemMessage(String message){
        System.out.println(Colors.WHITE + "[ "
                + Colors.YELLOW + "SYSTEM" + Colors.WHITE + " ] " + message);
    }

    public static String input(String message, String rule, String errorMessage){

        String temp;
        Scanner in = new Scanner(System.in);

        if(message == null || message.isEmpty()
                || rule == null || rule.isEmpty()) return null;

        do {
            System.out.print(message.contains("->") ? message : "-> " + message);
            temp = in.nextLine();
            temp = temp.matches(rule) ? temp : null;

            if (temp == null) printErrorMessage(errorMessage);
        } while (temp == null);

        return temp;
    }

    public static String input(String message, String rule){
        return input(message, rule, "Invalid input");
    }

    public static void displayTableMenu() {
        List<String> tableMenuOperation = Arrays.asList("Next Page", "Previous Page", "First Page", "Last Page", "Goto");
        List<String> dataMenuOperation = Arrays.asList("Write", "Read (id)", "Update", "Delete", "Search (name)");
        List<String> dataMenuOperation2 = Arrays.asList("Save", "Unsaved", "Backup", "Restore","Set rows");
        print("Menu", "-", 75, Colors.YELLOW, Colors.PINK);
        tableMenuOperation.forEach((menu) -> System.out.print(Colors.GREEN + menu.charAt(0) + ". " + Colors.WHITE + menu + " ".repeat(5)));
        System.out.println("\n");
        dataMenuOperation.forEach((menu) -> System.out.print(Colors.GREEN + menu.charAt(0) + ") " + Colors.WHITE + menu + " ".repeat(6)));
        System.out.println();
        dataMenuOperation2.forEach((menu) -> System.out.print(Colors.GREEN + menu.substring(0,2) + ") " + Colors.WHITE + menu + " ".repeat(6)));
        System.out.println("\n" + Colors.GREEN + "Exit".charAt(0) + ") " + Colors.WHITE + "Exit");
        System.out.println(Colors.YELLOW + "-".repeat(81) + Colors.WHITE);
    }
}