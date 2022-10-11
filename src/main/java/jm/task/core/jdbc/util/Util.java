package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection createNewConnection() {
        Connection connection;
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к БД");
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Соединение успешно установлено!");
        return connection;
    }
}
