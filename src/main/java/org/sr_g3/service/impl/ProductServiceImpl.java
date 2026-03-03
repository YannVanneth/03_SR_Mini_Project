package org.sr_g3.service.impl;

import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.model.Product;
import org.sr_g3.service.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final StockManagementDao stockManagementDao;

    public ProductServiceImpl(StockManagementDao dao){
        this.stockManagementDao = dao;
    }

    @Override
    public void create(Product product) {
        this.stockManagementDao.addStock(product);
    }

    @Override
    public void update(long id, Product newProduct) {
        this.stockManagementDao.updateStock(id, newProduct);
    }

    @Override
    public void delete(long id) {
        this.stockManagementDao.deleteStockById(id);
    }

    @Override
    public Optional<List<Product>> getProducts(int limit, int offset) {
        return Optional.ofNullable(this.stockManagementDao.fetchStock(limit, offset));
    }

    @Override
    public Optional<Product> getProductById(long id) {
        return this.stockManagementDao.getProductById(id);
    }

    @Override
    public Optional<List<Product>> getProductByName(String name) {
        return this.stockManagementDao.searchByName(name);
    }
}
