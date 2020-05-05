import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
//        Course course = new Course();
        Course course = session.get(Course.class, 49);
        course.setName("Самый новый курс");
        course.setType(CourseType.DESIGN);
//        course.setTeacherId(1);
        session.save(course);
        transaction.commit();
//        Course course = session.get(Course.class, 10);
//        System.out.println(course.getName());
//        Students students = session.get(Students.class, 5);
//        System.out.println(students.getId() + ". " + students.getName() + ". " + students.getRegistrationDate());
        sessionFactory.close();
    }
}
