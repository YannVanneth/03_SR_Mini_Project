package org.sr_g3;

import org.sr_g3.config.app.StockManagementSystem;
import org.sr_g3.dao.StockManagmentDaoImpl;
import org.sr_g3.model.Product;
import org.sr_g3.utils.ProductTableDesign;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StockManagmentDaoImpl stockManagementDao = new StockManagmentDaoImpl();


        ProductTableDesign.printTable(stockManagementDao.fetchStock(30,0),1,1);
        stockManagementDao.updateStock(new Product(15,"Boom69",12.2d,12, java.time.LocalDate.now()));


        ProductTableDesign.printTable(stockManagementDao.fetchStock(30,0),1,1);


        LocalDate LocalDate = null;
//        stockManagementDao.addStock(new Product("Boom",12.2d,12, java.time.LocalDate.now()));
//        ProductTableDesign.printTable(stockManagementDao.fetchStock(6,0),1,1);
//        stockManagementDao.deleteStockById(15);
//        ProductTableDesign.printTable(stockManagementDao.fetchStock(30,0),1,1);

        StockManagementSystem.run(args);
    }
}