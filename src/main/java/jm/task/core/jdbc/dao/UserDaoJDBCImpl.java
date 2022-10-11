package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = util.createNewConnection(); Statement statement = connection.createStatement()) {
            statement.addBatch("CREATE TABLE IF NOT EXISTS `schema`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastname` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));");
            statement.executeBatch();
            System.out.println("База данных успешно создана/подключена");
        } catch (SQLException e) {
            System.err.println("Error");
        }

    }

    public void dropUsersTable() {
        try (Connection connection = util.createNewConnection(); Statement statement = connection.createStatement()) {
            statement.addBatch("DROP TABLE IF EXISTS `schema`.`users`;");
            statement.executeBatch();
            System.out.println("Удалено");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы");
        } ;
    }

    public void saveUser(String name, String lastName, byte age) {
        String COMMAND = "INSERT INTO `schema`.users (name, lastname, age) VALUES (?, ?, ?);";
        try (Connection connection = util.createNewConnection(); PreparedStatement preparedStatement = connection.prepareStatement(COMMAND)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            System.out.println("Добавлен пользователь " + name + " " + lastName);
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя");
        } ;
    }

    public void removeUserById(long id) {
        String COMMAND = "DELETE FROM `schema`.users WHERE ID = ?;";
        try (Connection connection = util.createNewConnection(); PreparedStatement preparedStatement = connection.prepareStatement(COMMAND)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            System.out.println("Пользователь с ID " + id + " удален");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя");
        } ;
    }

    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();
        String COMMAND = "SELECT * FROM `schema`.users;";
        try (Connection connection = util.createNewConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(COMMAND);
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                User user = new User();
                user.setId((long) id);
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                System.out.println(user.toString());
                list.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Проблема при получении списка пользователей");
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = util.createNewConnection(); Statement statement = connection.createStatement()) {
            statement.addBatch("TRUNCATE TABLE users");
            statement.executeBatch();
            System.out.println("Таблица Users очищена");
        } catch (SQLException e) {
            System.out.println("Возникла проблема при удалении таблицы");
        }
    }
}
