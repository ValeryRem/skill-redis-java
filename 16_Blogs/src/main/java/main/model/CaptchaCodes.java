package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class CaptchaCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Time of release is mandatory")
    private Date time;
    @NotBlank(message = "Capture code is mandatory")
    private Integer code;
    @NotBlank(message = "Secret code is mandatory")
    @Column(name = "secret_code")
    private Integer secretCode;

}
