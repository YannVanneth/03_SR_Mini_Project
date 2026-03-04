package org.sr_g3.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionUtil {


    public static Connection getDbCon() throws SQLException, ClassNotFoundException {
//
        Dotenv dotenv = Dotenv.load();
        //set up db connection
        String hostname = dotenv.get("DB_HOST");
        String database = dotenv.get("DB_NAME");
        String username = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

//        String hostname = "localhost:5436";
//        String database = "postgres";
//        String username = "postgres";
//        String password = "password123";

        String url = "jdbc:postgresql://" + hostname + "/" + database;

        //return connection
        return DriverManager.getConnection(url, username, password);

    }
}
