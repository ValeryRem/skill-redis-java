package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name ="is_active")
    @NotNull(message = "isActive status is mandatory")
    private Integer isActive;
    @Column(name ="moderation_status")
    @NotNull(message = "moderation_status is mandatory")
    private final ModerationStatus moderationStatus;
    @Column(name ="moderator_id")
    private Integer moderatorId;
    @NotNull(message = "time of post is mandatory")
    private Date time;
    @Column(name ="user_id")
    @NotNull(message ="userId is mandatory")
    private Integer userId;
    @NotNull(message ="title is mandatory")
    private String title;
    @NotNull(message ="text is mandatory")
    private String text;
    @NotNull(message = "viewCount is mandatory")
    @Column(name ="view_count")
    private Integer viewCount;

    public Posts() {
        this.moderationStatus = ModerationStatus.NEW;
    }
}
