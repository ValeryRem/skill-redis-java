import javax.persistence.*;
import java.util.Date;

@Embeddable
@Table(name = "Subscriptions")
public class Subscription {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "subscription_id")
//    private int subscriptionId;

    @Column(name = "student_id")
    private int studentId;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

//    public int getSubscriptionId() {
//        return subscriptionId;
//    }
//
//    public void setSubscriptionId(int subscriptionId) {
//        this.subscriptionId = subscriptionId;
//    }
}
