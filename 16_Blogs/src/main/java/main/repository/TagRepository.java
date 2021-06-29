package main.repository;

import main.entity.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository <Tag, Integer>  {
    @Query(value = "SELECT t FROM Tag t WHERE t.tagName = ?1")
    Optional<Tag> findTagByName(String tagName);

}
