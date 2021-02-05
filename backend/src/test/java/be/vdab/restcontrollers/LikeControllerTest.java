package src.test.java.be.vdab.restcontrollers;

import be.vdab.BackendApplication;
import be.vdab.domain.Like;
import be.vdab.domain.Post;
import be.vdab.domain.User;
import be.vdab.repositories.LikeRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import be.vdab.restcontrollers.LikeController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpServerErrorException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
@ActiveProfiles(value = "local")
class LikeControllerTest {

    @Autowired
    LikeController likeController;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;


    // region setup
    @BeforeEach
    void setup(){
        // make post
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
        postRepository.save(post);
    }

    @AfterEach
    void breakDown(){
        likeRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }
    // endregion

    // region testAddLike
    @Test
    void testAddLikeNull(){
        ResponseEntity<String> response = likeController.postAddLike(null,null);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }
    @Test
    void testAddLikeOnlyPostID(){
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        ResponseEntity<String> response = likeController.postAddLike(post.getId(),null);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }
    @Test
    void testAddLikeOnlyUsername(){
        User user = userRepository.findAll().get(0); // get createdUser from setup

        ResponseEntity<String> response = likeController.postAddLike(null,user.getUsername());
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }
    @Test
    void testAddLikeInvalidPostID(){
        User user = userRepository.findAll().get(0); // get createdUser from setup

        ResponseEntity<String> response = likeController.postAddLike(-10L, user.getUsername());
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }
    @Test
    void testAddLikeInvalidUsername(){
        Post post = postRepository.findAll().get(0); // get createdPost from setup

        ResponseEntity<String> response = likeController.postAddLike(post.getId(), "thisUsernameDoesNotExist");
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }
    @Test
    void testAddLikeValid(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        Post post = postRepository.findAll().get(0); // get createdPost from setup

        ResponseEntity<String> response = likeController.postAddLike(post.getId(),user.getUsername());
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }

    // endregion

    // region testRemoveLike
    @Test
    void testRemoveNull(){
        ResponseEntity<String> response = likeController.postRemoveLike(null,null);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }
    @Test
    void testRemoveOnlyPostID(){
        Post post = postRepository.findAll().get(0); // get createdPost from setup

        ResponseEntity<String> response = likeController.postRemoveLike(post.getId(), null);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );

    }
    @Test
    void testRemoveOnlyUsername(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        ResponseEntity<String> response = likeController.postRemoveLike(null, user.getUsername());
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );

    }
    @Test
    void testRemoveInvalidPostID(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        ResponseEntity<String> response = likeController.postRemoveLike(-10L,user.getUsername());
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );

    }
    @Test
    void testRemoveInvalidUsername(){
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        ResponseEntity<String> response = likeController.postRemoveLike(post.getId(), "thisUsernameDoesNotExist");
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );

    }
    @Test
    void testRemoveValid(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        Post post = postRepository.findAll().get(0); // get createdPost from setup

        likeRepository.save(new Like(post,user));

        ResponseEntity<String> response = likeController.postRemoveLike(post.getId(), user.getUsername());
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );

    }
    // endregion

    // region testGetLikes
    @Test
    void testGetLikesNull(){
        ResponseEntity<Integer> response = likeController.getGetLikes(null);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }
    @Test
    void testGetLikesInvalidPostID(){
        ResponseEntity<Integer> response = likeController.getGetLikes(-10L);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }
    @Test
    void testGetLikesValidPostIDZero(){
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        ResponseEntity<Integer> response = likeController.getGetLikes(post.getId());
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(0,response.getBody())

        );
    }
    @Test
    void testGetLikesValidPostIDOne(){
        Post post = postRepository.findAll().get(0); // get createdPost from setup

        User user2 = new User.UserBuilder()
                .withUsername("newusername")
                .withPassword("newPassword")
                .withEmail("newMail@Gmail.com")
                .build();
        userRepository.save(user2);
        likeRepository.save(new Like(post,user2)); // create a like

        ResponseEntity<Integer> response = likeController.getGetLikes(post.getId());
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(1,response.getBody())
        );
    }
    // endregion

}
