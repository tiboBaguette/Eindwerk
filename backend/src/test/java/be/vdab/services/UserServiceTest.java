package be.vdab.services;

import be.vdab.BackendApplication;
import be.vdab.domain.User;
import be.vdab.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BackendApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User()
                .setUsername("")
                .setEmail("")
                .setPassword("");

        userRepository.save(user);
    }

    @Test
    public void testSaveUser(){
        //TODO: make testSaveUser with username, mail, password or combination
        User user = new User()
                .setUsername("")
                .setEmail("")
                .setPassword("");

        User createdUser = userService.saveUser(user);

        Assertions.assertAll(
                () -> {Assertions.assertEquals(this.user.getId()+1,createdUser.getId());},
                () -> {Assertions.assertEquals("",createdUser.getUsername());},
                () -> {Assertions.assertEquals("",createdUser.getEmail());},
                () -> {Assertions.assertEquals("",createdUser.getPassword());}
        );
    }

    @Test
    public void testSaveUserWithUsername(){
        //TODO: make testSaveUser with username, mail, password or combination
        User user = new User()
                .setUsername("bernard")
                .setEmail("")
                .setPassword("");

        System.out.println(user.getId());
        User createdUser = userService.saveUser(user);
        System.out.println(createdUser.getId());

//        this.user.getId()+1,


        Assertions.assertAll(
                () -> {Assertions.assertNotNull(createdUser.getId());},
                () -> {Assertions.assertEquals("bernard",createdUser.getUsername());},
                () -> {Assertions.assertEquals("",createdUser.getEmail());},
                () -> {Assertions.assertEquals("",createdUser.getPassword());}
        );

    }

    @Test
    public void testFindUserByID(){

//        User userFound = userService.findUserById(1);
//
//        Assertions.assertAll(
//                () -> {Assertions.assertEquals(user.getId(),userFound.getId());},
//                () -> {Assertions.assertEquals("",user.getUsername());},
//                () -> {Assertions.assertEquals("fout",user.getEmail());},
//                () -> {Assertions.assertEquals("",user.getPassword());}
//        );


    }



}
