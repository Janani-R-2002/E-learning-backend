package com.elearning.dao;

import com.elearning.model.Enrollment;
import com.elearning.util.HibernateUtil;
import org.hibernate.*;

import java.util.List;

public class EnrollmentDAO {
    public void enroll(Enrollment enrollment) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(enrollment);
        tx.commit();
        session.close();
    }

    public List<Enrollment> getEnrollments(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Enrollment> query = session.createQuery("FROM Enrollment WHERE user.id = :uid", Enrollment.class);
        query.setParameter("uid", userId);
        List<Enrollment> list = query.list();
        session.close();
        return list;
    }
}
