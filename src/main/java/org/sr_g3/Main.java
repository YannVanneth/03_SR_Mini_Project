package org.sr_g3;

import org.sr_g3.config.app.StockManagementSystem;
import org.sr_g3.controller.StockController;
import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.dao.StockManagmentDaoImpl;
import org.sr_g3.utils.DbBackupRestoreUtil;
import org.sr_g3.view.impl.ProgramUi;

public class Main {
    public static void main(String[] args) throws Exception {
        DbBackupRestoreUtil.getVersion();
        StockManagementDao stockManagementDao = new StockManagmentDaoImpl();
        new ProgramUi().run(stockManagementDao);
    }
}