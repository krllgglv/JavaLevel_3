package ru.geekbrains.chat.DBonnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private DBConnector() {}

    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/chat", "root", "123456");

        } catch (SQLException throwables) {
            throw new RuntimeException("SWW", throwables);
        }
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
