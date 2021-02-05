package be.vdab.repositories;

import be.vdab.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Integer countLikesByPost_Id(Long postId);
    void deleteAllByPost_Id(Long PostId);
    Like findLikeByPost_IdAndUser_Username(Long postId, String username);
}
