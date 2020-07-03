package main.model;

import javax.persistence.*;

@Entity
public class Tourist {
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tourist")
    private int id;
    private int seat;
    private String birthday;

    public Tourist(String name, int id, int seat, String birthday) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
