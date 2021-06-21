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

    @Query("SELECT pv FROM PostVote pv WHERE pv.postId = ?1 AND pv.userId = ?2")
    Optional<PostVote> getOneByPostAndUser(Integer postId, int userId);

    @Query("SELECT count(pv) FROM PostVote pv WHERE pv.postId = ?1 AND pv.value = ?2")
    Optional<Integer> findCountVotesByPostId (int postId, int value);

}
