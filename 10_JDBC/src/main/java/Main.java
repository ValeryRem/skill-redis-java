import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String sqlCreateTable = "CREATE TABLE linkedPurchaseList AS SELECT student_id, course_id FROM subscriptions";
        try (Connection conn =  DBConnection.getConnection(sqlCreateTable)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select student_id, course_id FROM subscriptions");
            while (resultSet.next()) {
                Integer studentId = resultSet.getInt("student_id");
                Integer courseId = resultSet.getInt("course_id");
                System.out.printf("%s: %d, %s: %d%n", "student_id", studentId, "course_id", courseId);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
//        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
//        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();


//        Course course = session.get(Course.class, 35);
        //выбрать кол-во студентов у каждого препод-ля, первые 10
//        String sqlQuery = "select students_count, (select name from teachers where id = teacher_id) AS 'teachers'" +
//                "from courses group by teacher_id limit 10";
//        String sqlQuery2 = "select * from purchaselist limit 10"; // выбрать первые 10 строк из табл. покупок
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

//        String hql = " SELECT " +
//                "   c.name," +
//                "   t.name" +
//                " FROM Course c " +
//                " JOIN c.teacher AS t" +
//                " WHERE c.type = 'PROGRAMMING'";
//
//        List<Object[]> list = doHql(session, hql);
//        list.forEach(row -> System.out.printf("%-30s - %s%n", row[0], row[1]));
//
//        String hql2 = "FROM Course c where c.type ='PROGRAMMING'";
//        getCourseAndTeacherNamesHQL(session, hql2);

//        getTeacherInfo(session, 10);
//        showStudents(course);

//        List<Purchase> purchases = course.getPurchases();
//        System.out.println("\nPrintout of Purchase info: " + purchases.size());
//        purchases.forEach(x -> System.out.println(x.getCourseName() + " - " + x.getStudentName()));
//        showSubscriptions(course);

//        List<Teacher> teachers = course.getTeachers();
//        System.out.println("\nPrintout of Teacher info for course #" + course.getId());
//        for (Teacher teacher : teachers) {
//            System.out.println(teacher.getId() + ". " + teacher.getName());
//        }
        //Вывести в консоль все курсы по программированию  + их преподавателей: Имя_Курса -  Имя_Преподавателя.
        //select name as 'ИМЯ_КУРСА' ,(select name from teachers where id = teacher_id) as 'Имя_Преподавателя'
        //from courses where type='PROGRAMMING'

//        transaction.commit();
//        sessionFactory.close();

//        showTeachersViaDBConnection(sqlQuery);
//        showPurchasesViaDBConnection(sqlQuery2);
    }

//    private static void getCourseAndTeacherNamesHQL(Session session, String hql) {
//        try{
//            Query<Course> qry = session.createQuery(hql);
//            List<Course> list = qry.list();
//            list.forEach(course -> System.out.printf("%-35s - %s%n", course.getName(), course.getTeacher().getName()));
//        } catch (NullPointerException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private static List<Object[]> doHql(Session session, String hql) {
//        System.out.printf("---HQL----%n");
//        Query<Object[]> qry =  session.createQuery(hql);
//        return qry.getResultList();
//    }

//    private static void showStudents(Course course) {
//        System.out.printf("%n%s%s%s%d%n","Number of participants at the course ", course.getName(), ": ", course.getStudents().size());
//        List<Student> studentList = course.getStudents();
//        for(Student student : studentList) {
//            System.out.printf("%d%s%s%n", student.getId(), ". ", student.getName());
//        }
//    }
//
//    private static void showSubscriptions(Course course) {
//        List<Subscription> subscriptions = course.getSubscriptions();
//        System.out.println("\nPrintout of Subscription info for course #" + course.getId());
//        for (Subscription subscription : subscriptions) {
//            System.out.println(subscription.getSubscriptionId() + ". " + subscription.getSubscriptionDate());
//        }
//    }
//
//    private static void showPurchasesViaDBConnection(String sqlQuery) {
//        Connection connection = DBConnection.getConnection(sqlQuery);
//        try (Statement statement = connection.createStatement()) {
//            System.out.println("\nPrintout of Purchase info:");
//            ResultSet resultSet = statement.executeQuery(sqlQuery);
//            while (resultSet.next()) {
//                String purchaseId = resultSet.getString("purchase_id");
//                String studentName = resultSet.getString("student_name");
//                String subscriptionDate = resultSet.getString("subscription_date");
//                System.out.println(purchaseId + ". " + studentName + " - " + subscriptionDate);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void showTeachersViaDBConnection(String sqlQuery) {
//        Connection connection = DBConnection.getConnection(sqlQuery);
//        try (Statement statement = connection.createStatement()) {
//            System.out.println("\nPrintout of Teachers load by students:");
//            ResultSet resultSet = statement.executeQuery(sqlQuery);
//            while (resultSet.next()) {
//                String teacher = resultSet.getString("teachers");
//                String studentsCount = resultSet.getString("students_count");
//                System.out.println(teacher + " - " + studentsCount);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void getTeacherInfo(Session session, int rowsToPrintout) {
//        System.out.println("\nPrintout of Teacher info");
//        int i = 0;
//        int teacherId;
//        try {
//            Course course;
//            while (i < rowsToPrintout) {
//                i++;
//                course = session.get(Course.class, i);
//                if (course != null ) {
//                    teacherId = course.getTeacherId();
//                    Teacher t = session.get(Teacher.class, teacherId);
//                    System.out.printf("%d. %s - %d - %s%n ", course.getId(), t.getName(), t.getSalary(), course.getName());
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
