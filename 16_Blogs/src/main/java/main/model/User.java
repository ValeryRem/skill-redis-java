package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Status is mandatory")
    @Column(name = "is_moderator")
    private Integer isModerator;
    @Column(name = "reg_time")
    private Date regTime;
    private String email;
    private String password;
    private String code;
    private String photo;
}
