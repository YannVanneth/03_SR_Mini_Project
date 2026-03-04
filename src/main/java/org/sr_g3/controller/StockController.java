package org.sr_g3.controller;

import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.dao.StockManagementDaoImpl;
import org.sr_g3.service.ProductController;
import org.sr_g3.service.ProductService;
import org.sr_g3.service.impl.ProductServiceImpl;
import org.sr_g3.view.ProductView;
import org.sr_g3.view.impl.ProgramUi;

public class StockController implements ProductController {
    private final ProductView programUi = new ProgramUi();
    private final StockManagementDao stockManagementDao = new StockManagementDaoImpl();
    private final ProductService productService = new ProductServiceImpl(stockManagementDao);

    public StockManagementDao getStockManagementDao() { return stockManagementDao; }

    @Override
    public void write(){
        var pro = programUi.write();
        productService.create(pro);
    }

    @Override
    public void update() {
        programUi.updateProduct();
    }

    @Override
    public void delete() {
        programUi.deleteProduct();
    }

    @Override
    public void searchByName() {
        programUi.searchByName();
    }

    @Override
    public void read(){
        programUi.read();
    }

    @Override
    public void save() {
        programUi.save();
    }

    @Override
    public void unSaved() {
        programUi.unSave();
    }

    @Override
    public void backup() {

    }

    @Override
    public void restore() {

    }
}
