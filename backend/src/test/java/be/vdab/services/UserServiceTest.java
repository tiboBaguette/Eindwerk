package be.vdab.services;

import be.vdab.BackendApplication;
import be.vdab.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BackendApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSaveUser(){

        User user = new User.UserBuilder()
                .withUsername("")
                .withEmail("")
                .withPassword("")
                .build();

        User createdUser = userService.saveUser(user);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(1,createdUser.getId());
            Assertions.assertEquals("",createdUser.getUsername());
            Assertions.assertEquals("",createdUser.getEmail());
            Assertions.assertEquals("",createdUser.getPassword());
        });


    }



}
