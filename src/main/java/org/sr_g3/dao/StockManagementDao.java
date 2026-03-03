package org.sr_g3.dao;

import org.sr_g3.model.Product;

import java.util.List;
import java.util.Optional;

public interface StockManagementDao {

    List<Product> fetchStock(int limit, int offset);

    int countTotalRecords();

    void addStock(Product product);

    void updateStock(Long id, Product product);

    void deleteStockById(long id);

    int totalRow();

    Optional<Product> getProductById(Long id);

    Optional<List<Product>> searchByName(String name);
}
