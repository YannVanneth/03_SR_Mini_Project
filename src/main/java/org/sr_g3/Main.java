package org.sr_g3;

import org.sr_g3.config.app.StockManagementSystem;
import org.sr_g3.utils.DbBackupRestoreUtil;

public class Main {
    public static void main(String[] args) throws Exception {
        DbBackupRestoreUtil.getVersion();
        new StockManagementSystem().run(args);
    }
}