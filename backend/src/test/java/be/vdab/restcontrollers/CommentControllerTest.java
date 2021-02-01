package be.vdab.restcontrollers;

import be.vdab.domain.Comment;
import be.vdab.domain.Post;
import be.vdab.domain.User;
import be.vdab.repositories.CommentRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import be.vdab.services.CommentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentControllerTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentController commentController;
    @Autowired
    UserRepository userRepository;

    // region setup
    @BeforeEach
    void setup(){
    }
    @AfterEach()
    void breakDown(){
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }
    // endregion

    // region test createComment
    @Test
    void testAddCommentNull(){
        ResponseEntity<Comment> response = commentController.postAddComment(null);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testAddCommentWithContentEmpty() {
        Comment comment = new Comment.CommentBuilder()
                .withContent("")
                .build();

        ResponseEntity<Comment> response = commentController.postAddComment(comment);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }
    @Test
    void testAddCommentWithContent(){
        Comment comment = new Comment.CommentBuilder()
                .withContent("commentContent")
                .build();

        ResponseEntity<Comment> response = commentController.postAddComment(comment);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }
    @Test
    void testAddCommentWithPost(){
        User user = new User.UserBuilder()
                .withUsername("name")
                .withPassword("pass")
                .withEmail("mail@gmail.com")
                .build();
        User createdUser = userRepository.save(user);

        Post post = new Post.PostBuilder()
                .withUser(createdUser)
                .withTitle("postTitle")
                .withContent("postContent")
                .build();
        Post createdPost = postRepository.save(post);

        Comment comment = new Comment.CommentBuilder()
                .withPost(createdPost)
                .build();
        ResponseEntity<Comment> response = commentController.postAddComment(comment);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }
    @Test
    void testAddCommentWithPostCommentEmpty(){
        User user = new User.UserBuilder()
                .withUsername("name")
                .withPassword("pass")
                .withEmail("mail@gmail.com")
                .build();
        User createdUser = userRepository.save(user);

        Post post = new Post.PostBuilder()
                .withUser(createdUser)
                .withTitle("postTitle")
                .withContent("postContent")
                .build();
        Post createdPost = postRepository.save(post);

        Comment comment = new Comment.CommentBuilder()
                .withPost(createdPost)
                .withContent("")
                .build();
        ResponseEntity<Comment> response = commentController.postAddComment(comment);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }
    @Test
    void testAddCommentWithPostComment(){
        User user = new User.UserBuilder()
                .withUsername("name")
                .withPassword("pass")
                .withEmail("mail@gmail.com")
                .build();
        User createdUser = userRepository.save(user);

        Post post = new Post.PostBuilder()
                .withUser(createdUser)
                .withTitle("postTitle")
                .withContent("postContent")
                .build();
        Post createdPost = postRepository.save(post);

        Comment comment = new Comment.CommentBuilder()
                .withPost(createdPost)
                .withContent("commentContent")
                .build();
        ResponseEntity<Comment> response = commentController.postAddComment(comment);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }


    // endregion

}
