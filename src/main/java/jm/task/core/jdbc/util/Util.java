package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private Session session;

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

    public Session createHibernateConnection() {
        try {
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.url", URL);
            properties.setProperty("hibernate.connection.username", USERNAME);
            properties.setProperty("hibernate.connection.password", PASSWORD);
            properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
            properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            properties.setProperty("show_sql", "true");
            properties.setProperty("current_session_context_class", "thread");
            SessionFactory sessionFactory = new Configuration().addProperties(properties).addAnnotatedClass(User.class).buildSessionFactory();
            session = sessionFactory.openSession().getSession();
            return session;
        } catch (HibernateException e) {
            System.err.println("Ошибка при создании соединения с базой, проверьте каракули которые вы написали в properties");
            throw new RuntimeException();
        }
    }
}
