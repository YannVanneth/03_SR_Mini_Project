package org.sr_g3;

import org.sr_g3.config.app.StockManagementSystem;
import org.sr_g3.dao.StockManagementDaoImpl;
import org.sr_g3.utils.ProductTableDesign;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        StockManagementDaoImpl stockManagementDao = new StockManagementDaoImpl();


        ProductTableDesign.printTable(stockManagementDao.fetchStock(30,0),1,1);
        stockManagementDao.updateStock(new Product(15,"Boom69",12.2d,12, java.time.LocalDate.now()));



        ProductTableDesign.printTable(stockManagementDao.fetchStock(30,0),1,1);


//        stockManagementDao.addStock(new Product("Boom",12.2d,12, java.time.LocalDate.now()));
//        ProductTableDesign.printTable(stockManagementDao.fetchStock(6,0),1,1);
//        stockManagementDao.deleteStockById(15);
//        ProductTableDesign.printTable(stockManagementDao.fetchStock(30,0),1,1);

        StockManagementSystem.run(args);
    }
}