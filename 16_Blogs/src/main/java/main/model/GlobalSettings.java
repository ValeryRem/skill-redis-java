package main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class GlobalSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Capture code is mandatory")
    private String code;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Value of the vote is mandatory")
    private String value;
}
