import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "purchaselist")
public class Purchase {

    @Id
    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name")
    private String courseName;
    private int price;



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

//    @Embeddable
//    public static class PurchaseID implements Serializable {
//        Purchase purchase = new Purchase();
//        static final long serialVersionUID = 1L;
//        @Column(name = "student_name")
//        private String studentName = purchase.getStudentName();
//        @Column(name = "course_name")
//        private String courseName = purchase.getCourseName();
//        public PurchaseID() {
//        }
//
//        public PurchaseID(String studentName, String courseName) {
//            this.studentName = studentName;
//            this.courseName = courseName;
//        }
//
//        public String getStudentName() {
//            return studentName;
//        }
//
//        public String getCourseName() {
//            return courseName;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (!(o instanceof PurchaseID)) return false;
//            PurchaseID that = (PurchaseID) o;
//            return Objects.equals(getStudentName(), that.getStudentName()) &&
//                    Objects.equals(getCourseName(), that.getCourseName());
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(getStudentName(), getCourseName());
//        }
//
//        public void setStudentName(String studentName) {
//            this.studentName = studentName;
//        }
//
//        public void setCourseName(String courseName) {
//            this.courseName = courseName;
//        }
//    }
}
