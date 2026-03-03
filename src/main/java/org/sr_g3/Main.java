package org.sr_g3;

import org.sr_g3.config.app.StockManagementSystem;
import org.sr_g3.dao.StockManagmentDaoImpl;
import org.sr_g3.utils.ProductTableDesign;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        StockManagmentDaoImpl stockManagementDao = new StockManagmentDaoImpl();


        ProductTableDesign.printTable(stockManagementDao.fetchStock(5,0),1,1);

        sc.nextLine();
        ProductTableDesign.printTable(stockManagementDao.fetchStock(5,5),1,1);

        StockManagementSystem.run(args);
    }
}