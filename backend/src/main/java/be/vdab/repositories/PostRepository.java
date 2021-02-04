package be.vdab.repositories;

import be.vdab.domain.Category;
import be.vdab.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByTitle(String title);
    List<Post> findPostsByContentLike(String content);
    List<Post> findPostsByCategoryLike(Category category);
    List<Post> findPostsByCategory_Name(String categoryName);

    @Modifying
    @Transactional
    @Query("update Post p set p.title = :title, p.content = :content, p.category = :category where p.id = :id")
    void updatePost(@Param(value = "id") Long id,
                         @Param(value = "title") String title,
                         @Param(value = "content") String content,
                         @Param(value = "category") Category category);

}
