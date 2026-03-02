package org.sr_g3.dao;

import org.sr_g3.model.Product;

import java.util.List;

public interface StockManagementDao {

    List<Product> fetchStock(int limit, int offset);

    void addStock(Product product);

    void updateStock(Product product);

    void deleteStock(long id);




}
