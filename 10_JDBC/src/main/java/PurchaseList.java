import javax.persistence.*;
import javax.xml.crypto.Data;

@Entity
@Table(name = "Purchaselist")
public class PurchaseList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name", unique=true, nullable=false)
    private String courseName;
    private int price;

    @Column(name = "subscription_date")
    private Data subscriptionDate;

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

    public Data getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Data subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
