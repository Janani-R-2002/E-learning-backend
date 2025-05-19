package com.elearning.dao;

import com.elearning.model.User;
import com.elearning.util.HibernateUtil;
import org.hibernate.*;

public class UserDAO {
    public void register(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
    }

    public User login(String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<User> query = session.createQuery("FROM User WHERE email = :email AND password = :pass", User.class);
        query.setParameter("email", email);
        query.setParameter("pass", password);
        User user = query.uniqueResult();
        session.close();
        return user;
    }
}
