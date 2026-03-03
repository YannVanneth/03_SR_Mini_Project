package org.sr_g3;

import org.sr_g3.config.app.StockManagementSystem;
import org.sr_g3.utils.DbBackupRestoreUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

//        DbBackupRestoreUtil.backupPGSQL(3);

//        DbBackupRestoreUtil.restorePGSQL();

        DbBackupRestoreUtil.getVersion();

        StockManagementSystem.run(args);

    }
}