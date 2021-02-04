package be.vdab.repositories;

import be.vdab.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUserByUsernameOrEmail(String username, String email);
    User findUserByUsernameAndPassword(String username, String password);
    User findUserByUsername(String username);
}
