package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Util util = new Util();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = util.createHibernateConnection().openSession().getSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastname` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица успешно создана");
        } catch (Exception e) {
            System.err.println("Возникла ошибка при создании таблицы пользователей");
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = util.createHibernateConnection().openSession().getSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            System.err.println("Возникла ошибка при удалении таблицы пользователей");
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = util.createHibernateConnection().openSession().getSession();
        try {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Пользователь " + name + " " + lastName + " успешно добавлен");
        } catch (HibernateException e) {
            System.err.println("Ошибка при создании пользователя");
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = util.createHibernateConnection().openSession().getSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("Пользователь с ID " + id + " удален успешно");
        } catch (HibernateException e) {
            System.err.println("Ошибка при удалении пользователя");
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = util.createHibernateConnection().openSession().getSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from User i");
            List<User> list = query.getResultList();
            list.forEach(System.out::println);
            return list;
        } catch (HibernateException e) {
            System.err.println("Ошибка при получении списка пользователей");
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Session session = util.createHibernateConnection().openSession().getSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            session.close();
            System.out.println("Таблица успешно очищена");
        } catch (HibernateException e) {
            System.err.println("Ошибка при очистке таблицы");
        }
    }
}
