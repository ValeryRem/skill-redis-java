import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Course course = session.get(Course.class, 5);
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

        System.out.println("\nPrintout of Purchase info");
        Course course1 = session.get(Course.class, 3);
        List<Purchase> purchases = course1.getPurchases();
        for (Purchase purchase : purchases) {
            System.out.println(purchase.getCourseName() + " - " + purchase.getStudentName());
        }

        System.out.println("\nPrintout of Subscription info");
        Course course2 = session.get(Course.class, 20);
        List<Subscription> subscriptions = course2.getSubscriptions();
        for (Subscription subscription : subscriptions) {
            System.out.println(subscription.getSubscriptionId() + " - " + subscription.getSubscriptionDate());
        }

        transaction.commit();
        sessionFactory.close();
    }
}
