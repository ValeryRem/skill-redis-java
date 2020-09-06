package main.model;

import javax.persistence.*;

@Entity
public class Tourist {
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String seat;
    private String birthday;

    public Tourist () {
    }

    public Tourist(String name, String birthday, String seat) {
        this.name = name;
        this.birthday = birthday;
        this.seat = seat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
            this.seat = seat;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        if(birthday.matches("\\d{4}-\\d{2}-\\d{2}")) {
            this.birthday = birthday;
        }
    }
}
