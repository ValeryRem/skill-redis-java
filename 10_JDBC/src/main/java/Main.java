import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.management.Query;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Course course = session.get(Course.class, 35);
        String sqlQuery = "select students_count, (select name from teachers where id = teacher_id) AS 'teachers'" +
                "from courses group by teacher_id limit 10";
        String sqlQuery2 = "select * from purchaselist limit 10";

        //        course.setName("Макраме");
//        course.setType(CourseType.DESIGN);
//        course.setTeacherId(1);
//        session.save(course);
//       course = session.get(Course.class, 48);
//        System.out.println(course.getId() + ". " + course.getName());
//        course = session.get(Course.class, 49);
//        System.out.printf("%d%s%s%n", course.getTeacher().getId(), ". ", course.getTeacher().getName());
//        Student students = session.get(Student.class, 5);
//        System.out.println(students.getId() + ". " + students.getName() + ". " + students.getRegistrationDate());

        System.out.printf("%s%s%s%d%n","Number of participants at the course ", course.getName(), ": ", course.getStudents().size());
        List<Student> studentList = course.getStudents();
        for(Student student : studentList) {
            System.out.printf("%d%s%s%n", student.getId(), ". ", student.getName());
        }

//        List<Purchase> purchases = course.getPurchases();
//        System.out.println("\nPrintout of Purchase info: " + purchases.size());
//        purchases.forEach(x -> System.out.println(x.getCourseName() + " - " + x.getStudentName()));

        List<Subscription> subscriptions = course.getSubscriptions();
        System.out.println("\nPrintout of Subscription info for course #" + course.getId());
        for (Subscription subscription : subscriptions) {
            System.out.println(subscription.getSubscriptionId() + ". " + subscription.getSubscriptionDate());
        }

//        List<Teacher> teachers = course.getTeachers();
//        System.out.println("\nPrintout of Teacher info for course #" + course.getId());
//        for (Teacher teacher : teachers) {
//            System.out.println(teacher.getId() + ". " + teacher.getName());
//        }

        transaction.commit();
        sessionFactory.close();


        Connection connection = DBConnection.getConnection(sqlQuery);
        try (Statement statement = connection.createStatement()) {
                    System.out.println("\nPrintout of Teachers load by students:");
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                String teachers = resultSet.getString("teachers");
                String studentsCount = resultSet.getString("students_count");
                System.out.println(teachers + " - " + studentsCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Connection connection2 = DBConnection.getConnection(sqlQuery2);
        try (Statement statement = connection2.createStatement()) {
            System.out.println("\nPrintout of Purchase info:");
            ResultSet resultSet2 = statement.executeQuery(sqlQuery2);
            while (resultSet2.next()) {
                String purchaseId = resultSet2.getString("purchase_id");
                String studentName = resultSet2.getString("student_name");
                String subscriptionDate = resultSet2.getString("subscription_date");
                System.out.println(purchaseId + ". " + studentName + " - " + subscriptionDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
