package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Util util = new Util();
    private SessionFactory sessionFactory = util.createHibernateConnection();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        if (sessionFactory != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession().getSession()) {
                transaction = session.beginTransaction();
                session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` VARCHAR(45) NOT NULL,\n" +
                        "  `lastname` VARCHAR(45) NOT NULL,\n" +
                        "  `age` INT NOT NULL,\n" +
                        "  PRIMARY KEY (`id`));").executeUpdate();
                transaction.commit();
                System.out.println("Таблица успешно создана");
            } catch (Exception e) {
                System.err.println("Возникла ошибка при создании таблицы пользователей");
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        if (sessionFactory != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession().getSession()) {
                transaction = session.beginTransaction();
                session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
                transaction.commit();
                System.out.println("Таблица очищена");
            } catch (Exception e) {
                System.err.println("Возникла ошибка при удалении таблицы пользователей");
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        if (sessionFactory != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession().getSession()) {
                transaction = session.beginTransaction();
                User user = new User(name, lastName, age);
                session.save(user);
                transaction.commit();
                System.out.println("Пользователь " + name + " " + lastName + " успешно добавлен");
            } catch (HibernateException e) {
                System.err.println("Ошибка при создании пользователя");
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        if (sessionFactory != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession().getSession()) {
                transaction = session.beginTransaction();
                User user = session.get(User.class, id);
                session.delete(user);
                transaction.commit();
                System.out.println("Пользователь с ID " + id + " удален успешно");
            } catch (HibernateException e) {
                System.err.println("Ошибка при удалении пользователя");
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        if (sessionFactory != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession().getSession()) {
                transaction = session.beginTransaction();
                Query query = session.createQuery("from User i");
                List<User> list = query.getResultList();
                list.forEach(System.out::println);
                transaction.commit();
                return list;
            } catch (HibernateException e) {
                System.err.println("Ошибка при получении списка пользователей");
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        if (sessionFactory != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession().getSession()) {
                transaction = session.beginTransaction();
                session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
                session.close();
                transaction.commit();
                System.out.println("Таблица успешно очищена");
            } catch (HibernateException e) {
                System.err.println("Ошибка при очистке таблицы");
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
