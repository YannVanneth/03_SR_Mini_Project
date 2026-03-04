package org.sr_g3.view;

import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.model.Product;

import java.util.Optional;

public interface ProductView {

    void run(StockManagementDao stockManagementDao);

    Product write();
    Optional<Product> read();
    Optional<Product> searchByName();
    Optional<Product> updateProduct();
    void gotoView();

    boolean deleteProduct();
    void backUp();
    void restore();
    void setRow();
    void unSave();
    void save();
}
