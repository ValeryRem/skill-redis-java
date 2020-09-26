package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name ="is_active")
    @NotNull(message = "isActive status is mandatory")
    private Integer isActive;
    @Column(name ="moderation_status")
    @NotNull(message = "moderation_status is mandatory")
    private ModerationStatus moderationStatus = ModerationStatus.NEW;
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

    public Post(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public Post(String title, Integer id) {
        this.title = title;
        this.id = id;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    public Post addComment(PostComment comment) {
        comments.add(comment);
        comment.setPost(this);
        return this;
    }

    public Post removeComment(PostComment comment) {
        comments.remove(comment);
        comment.setPost(null);
        return this;
    }


}
