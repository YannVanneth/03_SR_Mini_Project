package org.sr_g3;

import org.sr_g3.config.app.StockManagementSystem;
import org.sr_g3.dao.StockManagmentDaoImpl;
import org.sr_g3.utils.ProductTableDesign;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StockManagementSystem.run(args);
    }
}