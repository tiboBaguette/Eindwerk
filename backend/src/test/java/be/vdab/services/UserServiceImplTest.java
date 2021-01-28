package be.vdab.services;

import be.vdab.BackendApplication;
import be.vdab.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BackendApplication.class)
class UserServiceImplTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void saveUser() {
        userRepository.findAll();
    }
}
