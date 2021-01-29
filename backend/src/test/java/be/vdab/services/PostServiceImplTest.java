package be.vdab.services;

import be.vdab.BackendApplication;
import be.vdab.domain.Post;
import be.vdab.domain.User;
import be.vdab.repositories.PostRepository;
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

    // region setup
    @BeforeEach
    void setup(){

    }

    @AfterEach
    void breakDown(){

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

        Post post = new Post.PostBuilder()
                .withUser(user)
                .build();
        assertFalse(postService.createPost(post));
    }

    @Test
    void testCreatePostWithInvalidUser() {
    }

    @Test
    void testCreatePostWithTitleAndContent() {
    }

    @Test
    void testCreatePostWithTitleAndUser() {
    }

    @Test
    void testCreatePostWithContentAndUser() {
    }

    @Test
    void testCreatePostWithTitleContentAnUser() {
    }


    // endregion
}
