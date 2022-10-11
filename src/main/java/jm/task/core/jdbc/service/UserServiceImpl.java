package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final String URL = "jdbc:mysql://localhost:3306/schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();

    public void createUsersTable() {
        userDaoJDBC.createUsersTable();
    }
        public void dropUsersTable() {
        userDaoJDBC.dropUsersTable();
        }

        public void saveUser(String name, String lastName,byte age){
        userDaoJDBC.saveUser(name, lastName, age);
        }

        public void removeUserById ( long id){
        userDaoJDBC.removeUserById(id);
        }

        public List<User> getAllUsers () {
            List<User> list;
            list = userDaoJDBC.getAllUsers();
            return list;
        }

        public void cleanUsersTable () {
        userDaoJDBC.cleanUsersTable();
        }
    }
