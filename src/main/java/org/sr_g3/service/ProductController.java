package org.sr_g3.service;

public interface ProductController extends StockManagementFunctionality {
    void read();
    void write();
    void update();
    void delete();
    void searchByName();
}
