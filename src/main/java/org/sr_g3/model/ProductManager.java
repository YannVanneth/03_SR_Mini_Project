package org.sr_g3.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManager {
    private static final List<Product> productList =  new ArrayList<>();
    private static final Map<Long, Product> updatedProductList =  new HashMap<>();
    private static final Map<Long, Product> deletedProductList =  new HashMap<>();

    public static Map<Long, Product> getDeletedProductList() {
        return deletedProductList;
    }

    public static Map<Long, Product> getUpdatedProductList() {
        return updatedProductList;
    }

    public static void addUpdatedProduct(Long id, Product product) { updatedProductList.put(id, product); }

    public static void addDeletedProduct(Long id, Product product) { deletedProductList.put(id, product); }

    public static void addProduct(Product product){ productList.add(product); }
    public static List<Product> getProductList(){ return productList; }

    public static void clearDeletedProductList(){ deletedProductList.clear(); }
    public static void clearUpdatedProductList(){ updatedProductList.clear(); }
    public static void clearProductList(){ productList.clear(); }


}