import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @Column(name = "subscription_date")
    private Date subscriptionDate;

//    @Column(name = "student_id", insertable = false, updatable = false)
//    private Integer studentId;
//
//    @Column(name = "course_id", insertable = false, updatable = false)
//    private Integer courseId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    public Integer getStudentId() {
        return student.getId();
    }

    public Integer getCourseId() {
        return course.getId();
    }

    public void setStudentId(Integer studentId) {
        student.setId(studentId);
    }

    public void setCourseId(Integer courseId) {
        course.setId(courseId);
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }
}
