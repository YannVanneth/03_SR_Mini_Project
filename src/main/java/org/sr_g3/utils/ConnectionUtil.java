package org.sr_g3.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection getDbCon() throws SQLException, ClassNotFoundException {
        //set up db connection
        String hostname = "localhost";
        String name = "postgres";
        String password = "admin123";
        String database = "stockmanagementdb";
        String url = "jdbc:postgresql://" + hostname + "/" + database;

        //return connection
        return DriverManager.getConnection(url, name, password);

    }
}
