package ru.geekbrains.chat.DBonnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DBQueries {

    public static Set<User> findAllUsers() {
        Connection connection = DBConnector.connect();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");

            ResultSet rs = statement.executeQuery();
            Set<User> users = new HashSet<>();

            while (rs.next()) {
                users.add(
                        new User(
                                rs.getString("login"),
                                rs.getString("password"),
                                rs.getString("nickname")

                        )
                );
            }

            return users;
        } catch (SQLException e) {
            throw new RuntimeException("SWW", e);
        } finally {
            DBConnector.close(connection);
        }
    }
    public static String findUser(String login, String  password) {
        Connection connection = DBConnector.connect();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT nickname FROM users WHERE login = ? AND password = ?"
            );

            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
//                resultSet.next();
                return resultSet.getString("nickname");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("SWW", e);
        } finally {
            DBConnector.close(connection);
        }
    }
    public static String changeNickname(String login, String  password, String newNickname){
        Connection connection = DBConnector.connect();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET nickname = ? " +
                            "WHERE login = ? AND password = ? ;"

            );

            statement.setString(1, newNickname);
            statement.setString(2, login);
            statement.setString(3, password);

             statement.executeUpdate();

            connection.commit();

            return findUser(login, password);
        } catch (SQLException e) {
            DBConnector.rollback(connection);
            throw new RuntimeException("SWW", e);
        } finally {
            DBConnector.close(connection);
        }
    }
    }

