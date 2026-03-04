package org.sr_g3.model;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private final List<Product> productList =  new ArrayList<>();
    private final List<Product> updatedProductList =  new ArrayList<>();

    public List<Product> getDeletedProductList() {
        return deletedProductList;
    }

    public List<Product> getUpdatedProductList() {
        return updatedProductList;
    }

    public void addUpdatedProduct(Product product) { updatedProductList.add(product); }

    public void addDeletedProduct(Product product) { deletedProductList.add(product); }

    private final List<Product> deletedProductList =  new ArrayList<>();

    public void addProduct(Product product){ productList.add(product); }
    public List<Product> getProductList(){ return productList; }


}