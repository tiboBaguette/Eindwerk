package be.vdab.restcontrollers;

import be.vdab.BackendApplication;
import be.vdab.domain.Post;
import be.vdab.domain.User;
import be.vdab.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class PostControllerTest {

    @Autowired
    PostController postController;
    @Autowired
    UserRepository userRepository;

    // region setup
    @BeforeEach
    void setup(){

    }

    @AfterEach
    void breakDown(){
        userRepository.deleteAll();
    }
    // endregion

    // region test create
    @Test
    void testCreatePostWithNull() {
        ResponseEntity<Post> response = postController.postPostCreate(null);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testCreatePostWithTitle() {
        Post post = new Post.PostBuilder()
                .withTitle("Title")
                .build();
        ResponseEntity<Post> response = postController.postPostCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testCreatePostWithContent() {
        Post post = new Post.PostBuilder()
                .withContent("Content")
                .build();
        ResponseEntity<Post> response = postController.postPostCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testCreatePostWithUser() {
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        userRepository.save(user);
        Post post = new Post.PostBuilder()
                .withUser(user)
                .build();
        ResponseEntity<Post> response = postController.postPostCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testCreatePostWithInvalidUser() {
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        // not saved -> does not exist on database -> invalid user
        Post post = new Post.PostBuilder()
                .withUser(user)
                .build();
        ResponseEntity<Post> response = postController.postPostCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );

    }

    @Test
    void testCreatePostWithTitleAndContent() {
        Post post = new Post.PostBuilder()
                .withTitle("title")
                .withContent("content")
                .build();
        ResponseEntity<Post> response = postController.postPostCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testCreatePostWithTitleAndUser() {
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        userRepository.save(user);
        Post post = new Post.PostBuilder()
                .withTitle("title")
                .withUser(user)
                .build();
        ResponseEntity<Post> response = postController.postPostCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );

    }

    @Test
    void testCreatePostWithContentAndUser() {
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        userRepository.save(user);
        Post post = new Post.PostBuilder()
                .withContent("content")
                .withUser(user)
                .build();
        ResponseEntity<Post> response = postController.postPostCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );

    }

    @Test
    void testCreatePostWithTitleContentAnUser() {
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        userRepository.save(user);
        Post post = new Post.PostBuilder()
                .withTitle("title")
                .withContent("content")
                .withUser(user)
                .build();
        ResponseEntity<Post> response = postController.postPostCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }


    // endregion
}
