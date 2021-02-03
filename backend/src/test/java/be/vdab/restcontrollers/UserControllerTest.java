package src.test.java.be.vdab.restcontrollers;

import be.vdab.BackendApplication;
import be.vdab.domain.User;
import be.vdab.repositories.UserRepository;
import be.vdab.restcontrollers.UserController;
import be.vdab.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = BackendApplication.class)
@ActiveProfiles(value = "local")
class UserControllerTest {

    @Autowired
    UserController userController;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;


    // region before/after each
    @BeforeEach
    void setUp() {
        User user = new User.UserBuilder()
                .withUsername("Jan")
                .withEmail("a.b@Gmail.com")
                .withPassword("thisIsMyPass")
                .build();

        this.user = userRepository.save(user);
    }

    @AfterEach
    void clearUsers(){
        userRepository.deleteAll();
    }
    // endregion

    // region test register
    @Test
    void testRegisterEmpty() {
        User user1 = new User.UserBuilder().build();

        ResponseEntity<User> response = userController.postRegister(user1);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );

    }
    @Test
    void testRegisterWithUniqueUsername() {
        User user1 = new User.UserBuilder()
                .withUsername("Bert")
                .build();
        ResponseEntity<User> response = userController.postRegister(user1);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(user1.getUsername(), response.getBody().getUsername())
        );

    }
    @Test
    void testRegisterWithDuplicateUsername() {
        User user1 = new User.UserBuilder()
                .withUsername("Jan")
                .build();
        ResponseEntity<User> response = userController.postRegister(user1);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }
    @Test
    void testRegisterWithUniqueUsernameWithPassword() {
        User user1 = new User.UserBuilder()
                .withUsername("Bert")
                .withPassword("this is my pass")
                .build();
        ResponseEntity<User> response = userController.postRegister(user1);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(user1.getUsername(), response.getBody().getUsername()),
                () -> assertEquals(user1.getPassword(), response.getBody().getPassword())
        );
    }

    //endregion

    //region test login

    @Test
    void testLoginEmpty() {
        User user1 = new User.UserBuilder()
                .build();
        ResponseEntity<User> response = userController.postLogin(user1);

        assertAll(
                () -> assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testLoginWithInvalidUsername() {
        User user1 = new User.UserBuilder()
                .withUsername("ThisUserDoesNotExist")
                .build();
        ResponseEntity<User> response = userController.postLogin(user1);

        assertAll(
                () -> assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testLoginWithValidUsernameNoPass() {
        User user1 = new User.UserBuilder()
                .withUsername("Jan")
                .build();
        ResponseEntity<User> response = userController.postLogin(user1);

        assertAll(
                () -> assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testLoginWithValidUsernameInvalidPass() {
        User user1 = new User.UserBuilder()
                .withUsername("Jan")
                .withUsername("ThisIsNotThePassword")
                .build();
        ResponseEntity<User> response = userController.postLogin(user1);

        assertAll(
                () -> assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testLoginWithValidUsernameValidPass() {
        User user1 = new User.UserBuilder()
                .withUsername("Jan")
                .withPassword("thisIsMyPass")
                .build();
        ResponseEntity<User> response = userController.postLogin(user1);

        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }



    //endregion
}
