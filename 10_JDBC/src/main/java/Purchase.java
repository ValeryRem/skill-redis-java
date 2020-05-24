import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "purchaselist")
public class Purchase {

    @EmbeddedId
    private Key key;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name")
    private String courseName;
    private int price;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    @Embeddable
    public class Key implements Serializable {

        static final long serialVersionUID = 1L;
        private String studentName;
        private String courseName;
        public Key() {
        }

        public Key(String studentName, String courseName) {
            this.studentName = studentName;
            this.courseName = courseName;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getCourseName() {
            return courseName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key that = (Key) o;
            return Objects.equals(getStudentName(), that.getStudentName()) &&
                    Objects.equals(getCourseName(), that.getCourseName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getStudentName(), getCourseName());
        }

    }

//    public int getPurchaseId() {
//        return purchaseId;
//    }
//
//    public void setPurchaseId(int purchaseId) {
//        this.purchaseId = purchaseId;
//    }
}
