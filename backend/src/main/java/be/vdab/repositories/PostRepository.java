package be.vdab.repositories;

import be.vdab.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByTitle(String title);
    List<Post> findPostsByContentLike(String content);


}
