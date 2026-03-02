package org.sr_g3.utils;

public abstract class Validator {

    public static String CharacterRule(){
        return "[A-Za-z]+";
    }

    public static String numberRule(){
        return "[0-9]+";
    }

    public static String phoneNumberRule(){
        return "^0\\d{9}$";
    }

    public static String genderRule(){
        return "(Male|Female|FEMALE|MALE|male|female)";
    }

    public static String nameRule(){
        return "[A-Za-z]{3,15} [A-Za-z]{3,15}";
    }

    public static String dateOfBirthRule(){
        return "\\d{2}-\\d{2}-\\d{4}";
    }

    public static boolean validateDateOfBirth(String date) {

        String[] dataarr = date.split("-");

        int month = Integer.parseInt(dataarr[1]);
        int day = Integer.parseInt(dataarr[0]);
        int year = Integer.parseInt(dataarr[2]);

        if (month < 1 || month > 12) {
            Console.printErrorMessage("Invalid month");
            return false;
        }

        boolean isLeapYear = false;
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            isLeapYear = true;
        }

        int maxDays = 0;

        if (month == 1 || month == 3 || month == 5 || month == 7 ||
                month == 8 || month == 10 || month == 12) {

            maxDays = 31;

        } else if (month == 4 || month == 6 || month == 9 || month == 11) {

            maxDays = 30;

        } else if (month == 2) {

            if (isLeapYear) {
                maxDays = 29;
            } else {
                maxDays = 28;
            }
        }

        if (day < 1 || day > maxDays) {
            Console.printErrorMessage("Invalid day for the given month");
            return false;
        }

        java.time.LocalDate today = java.time.LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();

        if (year > currentYear ||
                (year == currentYear && month > currentMonth) ||
                (year == currentYear && month == currentMonth && day > currentDay)) {

            Console.printErrorMessage("Birth date cannot be in the future");
            return false;
        }

        int age = currentYear - year;

        if (month > currentMonth ||
                (month == currentMonth && day > currentDay)) {
            age--;
        }

        if (age < 18) {
            Console.printErrorMessage("User must be at least 18 years old");
            return false;
        }

        return true;
    }

    public static String FloatRule(){
        return "(\\d+|\\d.\\d+)";
    }

}
