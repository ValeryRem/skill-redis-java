package main.repository;

import main.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<PostComment, Integer> {
        @Query("SELECT count(pc) FROM PostComment pc WHERE pc.postId = ?1")
        Optional<Integer> findCountOfPostCommentsByPostId(int postId);
}
