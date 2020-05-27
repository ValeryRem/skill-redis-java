import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private CourseType type;

    private String description;

    @Column(name = "teacher_id", insertable = false, updatable = false)
    private Integer teacherId;

    @Column(name = "students_count", nullable = true)
    private Integer studentsCount;
    private int price;

    @Column(name = "price_per_hour", nullable = true)
    private Float pricePerHour;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Teacher teacher;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desription) {
        this.description = desription;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(float pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "subscriptions", joinColumns = {@JoinColumn(name = "course_id")}, inverseJoinColumns = {@JoinColumn(name = "student_id")})
//    private List<Student> students;
//    public List<Student> getStudents() {
//        return students;
//    }
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Purchase> purchases;
//    public List<Purchase> getPurchases() {
//        return purchases;
//    }
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "subscriptions", joinColumns = {@JoinColumn(name = "course_id")}, inverseJoinColumns = {
//           @JoinColumn(name = "subscription_date", insertable = false, updatable = false)})
//    private List<Subscription> subscriptions;
//    public List<Subscription> getSubscriptions() {
//        return subscriptions;
//    }

//    public Teacher getTeacher() {
//        return teacher;
//    }
//    public Integer getTeacherId() {
//        return teacherId;
//    }

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "courses", joinColumns = {@JoinColumn(name = "name")}, inverseJoinColumns = {@JoinColumn(name = "teacher_id")})
//    private List<Teacher> teachers;
//    public List<Teacher> getTeachers() {
//        return teachers;
//    }
}
