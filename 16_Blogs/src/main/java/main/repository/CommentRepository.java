package main.repository;

import main.entity.Post;
import main.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<PostComment, Integer> {
        @Query("SELECT pc FROM PostComment pc WHERE pc.post_id = ?1")
        List<PostComment> findCommentsByPostId (int postId);

        @Query("SELECT count(pc) FROM PostComment pc WHERE pc.post_id = ?1")
        int getCommentCountByPostId(int postId);
}
