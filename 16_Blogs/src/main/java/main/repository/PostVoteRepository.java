package main.repository;

import main.entity.Post;
import main.entity.PostVote;
import main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Integer> {
    @Query("SELECT pv FROM PostVote pv WHERE pv.userId = ?1")
    Collection<PostVote> findAllPostVotesByUserId (int userId);

    @Query("SELECT count(v.value) FROM PostVote v WHERE v.value = 1")
    Integer findLikeCount ();

    @Query("SELECT count(v.value) FROM PostVote v WHERE v.value = -1")
    Integer findDislikeCount ();

    @Query("SELECT count(v.value) FROM PostVote v WHERE v.value = 1 AND v.postId = ?1")
    Optional<Integer> findLikeCountByPost (int postId);

    @Query("SELECT count(v.value) FROM PostVote v WHERE v.value = -1 AND v.postId = ?1")
    Optional<Integer> findDislikeCountByPost (int postId);
}
