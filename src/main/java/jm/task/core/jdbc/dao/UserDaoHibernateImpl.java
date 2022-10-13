package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Util util = new Util();
    private Session session;
    Transaction transaction;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            session = util.createHibernateConnection();
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastname` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));").executeUpdate();
            System.out.println("Таблица успешно создана");
        } catch (Exception e) {
            System.err.println("Возникла ошибка при создании таблицы пользователей");
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = util.createHibernateConnection();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            System.err.println("Возникла ошибка при удалении таблицы пользователей");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = util.createHibernateConnection();
            session.beginTransaction();
            User user = new User();
            user.setAge(age);
            user.setName(name);
            user.setLastName(lastName);
            session.save(user);
            session.getTransaction().commit();
            session.close();
            System.out.println("Пользователь " + name + " " + lastName + " успешно добавлен");
        } catch (HibernateException e) {
            System.err.println("Ошибка при создании пользователя");
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = util.createHibernateConnection();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            session.close();
            System.out.println("Пользователь с ID " + id + " удален успешно");
        } catch (HibernateException e) {
            System.err.println("Ошибка при удалении пользователя");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            session = util.createHibernateConnection();
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            criteriaQuery.from(User.class);
            List<User> list = session.createQuery(criteriaQuery).getResultList();
            list.forEach(System.out::println);
            session.close();
            return list;
        } catch (HibernateException e) {
            System.err.println("Ошибка при получении списка пользователей");
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = util.createHibernateConnection();
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate(); //просто потому что это 1 строка
            session.close();
            System.out.println("Таблица успешно очищена");
        } catch (HibernateException e) {
            System.err.println("Ошибка при очистке таблицы");
        }
    }
}
