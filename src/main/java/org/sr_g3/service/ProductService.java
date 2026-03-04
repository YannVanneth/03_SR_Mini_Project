package org.sr_g3.service;

import org.sr_g3.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService extends StockManagementFunctionality{

    // crud service
    void create(Product product);
    void update(long id, Product newProduct);
    void delete(long id);
    Optional<List<Product>> getProducts(int limit, int offset);
    Optional<Product> getProductById(long id);
    Optional<List<Product>> getProductByName(String name);

}
