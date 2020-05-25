import javax.persistence.*;

@Entity
@Table(name = "linkedPurchaseList")
public class LinkedPurchaseList {
    @EmbeddedId
    private SubscriptionId id = new SubscriptionId();

    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "course_id")
    private Integer courseId;

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }
}
