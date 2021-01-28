package be.vdab.services;

import be.vdab.BackendApplication;
import be.vdab.domain.User;
import be.vdab.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
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

    // region before/after each
    @BeforeEach
    void setUp() {
        User user = new User.UserBuilder()
                .withUsername("")
                .withEmail("")
                .withPassword("")
                .build();

        this.user = userRepository.save(user);
    }

    @AfterEach
    void clearUsers(){
        userRepository.deleteAll();
    }
    // endregion

    // region test saving
    @Test
    public void testSaveUser(){
        //TODO: make testSaveUser with username, mail, password or combination
        User user = new User.UserBuilder()
                .withUsername("")
                .withEmail("")
                .withPassword("")
                .build();

        User createdUser = userService.saveUser(user);


        Assertions.assertAll(
                () -> Assertions.assertEquals(this.user.getId()+1,createdUser.getId()),
                () -> Assertions.assertEquals("",createdUser.getUsername()),
                () -> Assertions.assertEquals("",createdUser.getEmail()),
                () -> Assertions.assertEquals("",createdUser.getPassword())
        );
    }

    @Test
    public void testSaveUserWithUsername(){
        //TODO: make testSaveUser with username, mail, password or combination
        User user = new User.UserBuilder()
                .withUsername("bernard")
                .withEmail("")
                .withPassword("")
                .build();

        System.out.println(user.getId());
        User createdUser = userService.saveUser(user);
        System.out.println(createdUser.getId());

//        this.user.getId()+1,


        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdUser.getId()),
                () -> Assertions.assertEquals("bernard",createdUser.getUsername()),
                () -> Assertions.assertEquals("",createdUser.getEmail()),
                () -> Assertions.assertEquals("",createdUser.getPassword())
        );

    }
    // endregion

    // region test register
    @Test
    public void testRegisterUserWithUniqueUser(){
        User user1 = new User.UserBuilder()
                .withUsername("thisIsAUniqueUser")
                .withEmail("thisIsAUniqueUser@Gmail.com")
                .withPassword("thisIsAUniqueUser")
                .build();
        Assertions.assertTrue(userService.registerUser(user1));

    }

    @Test
    public void testRegisterUserWithDuplicateUsername(){
        User user1 = new User.UserBuilder()
                .withUsername("Dirk")
                .withEmail("a.b@Gmail.com")
                .withPassword("myPass")
                .build();
        userService.registerUser(user1);
        User user2 = new User.UserBuilder()
                .withUsername("Dirk")
                .withEmail("")
                .withPassword("myPassword")
                .build();
        Assertions.assertFalse(userService.registerUser(user2));

    }
    @Test
    public void testRegisterUserWithDuplicateEmail(){
        User user1 = new User.UserBuilder()
                .withUsername("Dirk")
                .withEmail("a.b@Gmail.com")
                .withPassword("myPass")
                .build();
        userService.registerUser(user1);
        User user2 = new User.UserBuilder()
                .withUsername("Jan")
                .withEmail("a.b@Gmail.com")
                .withPassword("myPassword")
                .build();
        Assertions.assertFalse(userService.registerUser(user2));
    }
    @Test
    public void testRegisterUserWithDuplicateUsernameAndEmail(){
        User user1 = new User.UserBuilder()
                .withUsername("Dirk")
                .withEmail("a.b@Gmail.com")
                .withPassword("myPass")
                .build();
        userService.registerUser(user1);
        User user2 = new User.UserBuilder()
                .withUsername("Dirk")
                .withEmail("a.b@Gmail.com")
                .withPassword("myPassword")
                .build();
        Assertions.assertFalse(userService.registerUser(user2));
    }

    // endregion

    // region test login
    @Test
    public void testLoginWithEmpty() {
        User user1 = new User.UserBuilder()
                .build();
        Assertions.assertNull(userService.login(user1));
    }
    @Test
    public void testLoginWithUsername() {
        User user1 = new User.UserBuilder()
                .withUsername("Dirk")
                .withPassword("BlueBerry")
                .build();
        userRepository.save(user1);
        User user2 = new User.UserBuilder()
                .withUsername("Dirk")
                .build();
        Assertions.assertNull(userService.login(user2));
    }
    @Test
    public void testLoginWithPassword() {
        User user1 = new User.UserBuilder()
                .withUsername("Dirk")
                .withPassword("BlueBerry")
                .build();
        userRepository.save(user1);
        User user2 = new User.UserBuilder()
                .withPassword("BlueBerry")
                .build();
        Assertions.assertNull(userService.login(user2));
    }
    @Test
    public void testLoginWithRightPassword() {
        User user1 = new User.UserBuilder()
                .withUsername("Dirk")
                .withPassword("BlueBerry")
                .build();
        userRepository.save(user1);
        User user2 = new User.UserBuilder()
                .withUsername("Dirk")
                .withPassword("Blueberry")
                .build();
        Assertions.assertNotNull(userService.login(user2));
    }
    @Test
    public void testLoginWithWrongPassword() {
        User user1 = new User.UserBuilder()
                .withUsername("Dirk")
                .withPassword("BlueBerry")
                .build();
        userRepository.save(user1);
        User user2 = new User.UserBuilder()
                .withUsername("Dirk")
                .withPassword("Minecraft")
                .build();
        Assertions.assertNull(userService.login(user2));
    }






    // endregion
}
