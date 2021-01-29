package be.vdab.services;

import be.vdab.BackendApplication;
import be.vdab.domain.Post;
import be.vdab.domain.User;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class PostServiceImplTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;
    @Autowired
    UserRepository userRepository;


    // region setup
    @BeforeEach
    void setup(){

    }

    @AfterEach
    void breakDown(){
//        postRepository.deleteAll();
//        userRepository.deleteAll();
    }
    // endregion

    // region test create
    @Test
    void testCreatePostWithNull() {
        assertFalse(postService.createPost(null));
    }

    @Test
    void testCreatePostWithTitle() {
        Post post = new Post.PostBuilder()
                .withTitle("Title")
                .build();
        assertFalse(postService.createPost(post));
    }

    @Test
    void testCreatePostWithContent() {
        Post post = new Post.PostBuilder()
                .withContent("Content")
                .build();
        assertFalse(postService.createPost(post));
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
        assertFalse(postService.createPost(post));
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
        assertFalse(postService.createPost(post));

    }

    @Test
    void testCreatePostWithTitleAndContent() {
        Post post = new Post.PostBuilder()
                .withTitle("title")
                .withContent("content")
                .build();
        assertFalse(postService.createPost(post));
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
        assertFalse(postService.createPost(post));

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
        assertFalse(postService.createPost(post));

    }

    @Test
    void testCreatePostWithTitleContentAnUser() {
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser = userRepository.save(user);
        Post post = new Post.PostBuilder()
                .withTitle("title")
                .withContent("content")
                .withUser(createdUser)
                .build();
        assertTrue(postService.createPost(post));

        assertAll(
                () -> assertFalse(postRepository.findPostsByTitle("title").isEmpty())
        );
    }


    // endregion
}
