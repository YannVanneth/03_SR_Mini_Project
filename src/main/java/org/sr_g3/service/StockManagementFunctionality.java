package org.sr_g3.service;

public interface StockManagementFunctionality {
    void save();
    void unSaved();
    void backup();
    void restore();
    void exit();
    void setRow();

    void write();

    void readById();

    void searchByName();

    void updateProduct();
}
