package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "parent_id")
    private Integer parentId;
    @NotBlank(message = "Post_id is mandatory")
    @Column(name = "post_id")
    private Integer postId;
    @NotBlank(message = "Id of the voter is mandatory")
    @Column(name = "user_id")
    private Integer userId;
    @NotBlank(message = "Time of the comment is mandatory")
    private Date time;
    @NotBlank(message = "Text of the comment is mandatory")
    private String text;
}
