package be.vdab.repositories;

import be.vdab.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Integer countLikesByPost_Id(Long postId);
    void deleteAllByPost_Id(Long PostId);
    //TODO: add @Modifying, @Transactional, @Query(...) to fix unreliable remove call
    void deleteLikeByPost_IdAndUser_Username(Long postId, String username);
    Like findLikeByPost_IdAndUser_Username(Long postId, String username);
}
