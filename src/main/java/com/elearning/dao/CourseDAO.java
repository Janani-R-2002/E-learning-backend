package com.elearning.dao;

import com.elearning.model.Course;
import com.elearning.util.HibernateUtil;
import org.hibernate.*;

import java.util.List;

public class CourseDAO {
    public void addCourse(Course course) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(course);
        tx.commit();
        session.close();
    }

    public List<Course> getAllCourses() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Course> list = session.createQuery("FROM Course", Course.class).list();
        session.close();
        return list;
    }

    public Course getCourse(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Course course = session.get(Course.class, id);
        session.close();
        return course;
    }
}
