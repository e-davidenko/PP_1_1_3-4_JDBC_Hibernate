package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        Util util = new Util();
        util.createHibernateConnection();
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ben", "Biden", (byte) 40);
        userService.saveUser("Joe", "Say", (byte) 20);
        userService.saveUser("Steve", "True", (byte) 30);
        userService.saveUser("Kate", "May", (byte) 70);
        userService.getAllUsers();
        // userService.removeUserById(9);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
