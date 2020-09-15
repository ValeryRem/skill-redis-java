package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class PostVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Id of the vote sender is mandatory")
    @Column(name = "user_id")
    private Integer userId;
    @NotBlank(message = "Post_id is mandatory")
    @Column(name = "post_id")
    private Integer post_id;
    @NotBlank(message = "Time of the vote is mandatory")
    private Date time;
    @NotBlank(message = "Value of the vote is mandatory")
    private Integer value;

}
