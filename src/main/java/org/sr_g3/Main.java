package org.sr_g3;

import org.sr_g3.config.app.StockManagementSystem;
import org.sr_g3.dao.StockManagementDao;
import org.sr_g3.dao.StockManagmentDaoImpl;
import org.sr_g3.model.Product;
import org.sr_g3.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        StockManagementSystem.run(args);
    }
}