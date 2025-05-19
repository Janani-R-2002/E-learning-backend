package com.elearning;

import com.elearning.dao.CourseDAO;
import com.elearning.dao.EnrollmentDAO;
import com.elearning.dao.UserDAO;
import com.elearning.model.Course;
import com.elearning.model.Enrollment;
import com.elearning.model.User;

import java.util.List;
import java.util.Scanner;

public class App {
    static Scanner sc = new Scanner(System.in);
    static UserDAO userDAO = new UserDAO();
    static CourseDAO courseDAO = new CourseDAO();
    static EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    public static void main(String[] args) {
        insertSampleCourses();  // Run once to add demo courses

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: register(); break;
                case 2:
                    User user = login();
                    if (user != null) {
                        userMenu(user);
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;
                case 3: System.exit(0);
            }
        }
    }

    static void register() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userDAO.register(user);
        System.out.println("Registered successfully!");
    }

    static User login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        return userDAO.login(email, password);
    }

    static void userMenu(User user) {
        while (true) {
            System.out.println("\n1. View All Courses\n2. Enroll in Course\n3. View Enrolled Courses\n4. Logout");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: viewCourses(); break;
                case 2: enroll(user); break;
                case 3: viewEnrolledCourses(user); break;
                case 4: return;
            }
        }
    }

    static void viewCourses() {
        List<Course> courses = courseDAO.getAllCourses();
        System.out.println("\n--- Available Courses ---");
        for (Course c : courses) {
            System.out.println(c.getId() + ". " + c.getTitle() + " - " + c.getDescription());
        }
    }

    static void enroll(User user) {
        viewCourses();
        System.out.print("Enter Course ID to enroll: ");
        int courseId = sc.nextInt();
        Course course = courseDAO.getCourse(courseId);
        if (course != null) {
            Enrollment e = new Enrollment();
            e.setUser(user);
            e.setCourse(course);
            enrollmentDAO.enroll(e);
            System.out.println("Enrolled in course: " + course.getTitle());
        } else {
            System.out.println("Invalid Course ID.");
        }
    }

    static void viewEnrolledCourses(User user) {
        List<Enrollment> enrollments = enrollmentDAO.getEnrollments(user.getId());
        System.out.println("\n--- Your Enrolled Courses ---");
        for (Enrollment e : enrollments) {
            Course c = e.getCourse();
            System.out.println(c.getTitle() + " - " + c.getDescription());
        }
    }

    static void insertSampleCourses() {
        List<Course> courses = courseDAO.getAllCourses();
        if (courses.isEmpty()) {
            Course c1 = new Course("Java Basics", "Learn the fundamentals of Java.");
            Course c2 = new Course("Web Development", "HTML, CSS, JavaScript for building websites.");
            Course c3 = new Course("Spring Boot", "Build REST APIs using Spring Boot.");

            courseDAO.addCourse(c1);
            courseDAO.addCourse(c2);
            courseDAO.addCourse(c3);
        }
    }
}
