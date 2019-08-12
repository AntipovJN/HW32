package com.boraji.tutorial.spring.utils;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {

    private static final Logger logger = Logger.getLogger(ConnectionJDBC.class);

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "937508";

    public static Connection getConnection() {

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            return connection;
        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver not found", e);
        } catch (SQLException e) {
            logger.error("Get connection to DB imposable at the moment", e);
        }
        return null;
    }
}
