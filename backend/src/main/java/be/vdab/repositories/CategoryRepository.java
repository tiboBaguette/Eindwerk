package be.vdab.repositories;

import be.vdab.domain.Category;
import be.vdab.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
