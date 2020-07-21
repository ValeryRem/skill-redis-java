package main.model;

import javax.persistence.*;

@Entity
public class Tourist {
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tourist")
    private Integer id;
    private String seat;
    private String birthday;

    public Tourist () {

    }
    public Tourist(String name, Integer id, String seat, String birthday) {
        this.name = name;
        this.id = id;
        this.seat = seat;
        this.birthday = birthday;
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
        this.birthday = birthday;
    }
}
