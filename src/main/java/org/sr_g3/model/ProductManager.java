package org.sr_g3.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManager {
    private final List<Product> productList =  new ArrayList<>();
    private final Map<Long, Product> updatedProductList =  new HashMap<>();

    public List<Product> getDeletedProductList() {
        return deletedProductList;
    }

    public Map<Long, Product> getUpdatedProductList() {
        return updatedProductList;
    }

    public void addUpdatedProduct(Long id, Product product) { updatedProductList.put(id ,product); }

    public void addDeletedProduct(Product product) { deletedProductList.add(product); }

    private final List<Product> deletedProductList =  new ArrayList<>();

    public void addProduct(Product product){ productList.add(product); }
    public List<Product> getProductList(){ return productList; }


}