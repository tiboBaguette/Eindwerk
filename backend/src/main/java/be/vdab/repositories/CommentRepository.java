package be.vdab.repositories;

import be.vdab.domain.Category;
import be.vdab.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
