import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "linkedPurchaseList")
public class LinkedPurchaseList {
    @EmbeddedId
    private SubscriptionId id = new SubscriptionId();

    @Column(name = "subscription_date")
    private Date subscriptionDate;

//    @Column(name = "student_id", insertable = false, updatable = false)
//    private Integer studentId = id.getStudent().getId();
//
//    @Column(name = "course_id", insertable = false, updatable = false)
//    private Integer courseId = id.getCourse().getId();

    public Integer getStudentId() {
        return id.getStudent().getId();
    }

    public Integer getCourseId() {
        return id.getCourse().getId();
    }

    public SubscriptionId getId() {
        return id;
    }

    public void setId(SubscriptionId id) {
        this.id = id;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
