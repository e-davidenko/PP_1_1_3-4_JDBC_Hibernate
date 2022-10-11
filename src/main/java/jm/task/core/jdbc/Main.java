package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ben", "Biden", (byte) 40);
        userService.saveUser("Joe", "Say", (byte) 20);
        userService.saveUser("Steve", "True", (byte) 30);
        userService.saveUser("Kate", "May", (byte) 70);
        userService.removeUserById(9L);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
