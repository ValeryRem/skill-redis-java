import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "linkedPurchaseList")
public class LinkedPurchaseList {
    @EmbeddedId
    private Purchase.Key key;

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
