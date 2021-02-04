package src.test.java.be.vdab.restcontrollers;

import be.vdab.BackendApplication;
import be.vdab.domain.Comment;
import be.vdab.domain.Post;
import be.vdab.domain.User;
import be.vdab.dtos.CommentDTO;
import be.vdab.repositories.CommentRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import be.vdab.restcontrollers.CommentController;
import be.vdab.services.CommentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
@ActiveProfiles(value = "local")
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
        ResponseEntity<CommentDTO> response = commentController.postAddComment(null);
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

        ResponseEntity<CommentDTO> response = commentController.postAddComment(comment);
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

        ResponseEntity<CommentDTO> response = commentController.postAddComment(comment);
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
        ResponseEntity<CommentDTO> response = commentController.postAddComment(comment);
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
        ResponseEntity<CommentDTO> response = commentController.postAddComment(comment);
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
        ResponseEntity<CommentDTO> response = commentController.postAddComment(comment);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }


    // endregion

    // region test getCommentsByPostID
    @Test
    void testGetByPostIDNull(){
        // make a comment
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
        commentRepository.save(comment);
        // try to find comment

        ResponseEntity<Iterable<CommentDTO>> response = commentController.getComments(null);
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<CommentDTO> comments = (List<CommentDTO>) response.getBody();
                    assertTrue(comments.isEmpty());
                }
        );
    }
    @Test
    void testGetByPostIDInvalid(){
        // make a comment
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
        commentRepository.save(comment);
        // try to find comment
        ResponseEntity<Iterable<CommentDTO>> response = commentController.getComments(-10L); // -10L should never be a valid index
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<CommentDTO> comments = (List<CommentDTO>) response.getBody();
                    assertTrue(comments.isEmpty());
                }
        );
    }
    @Test
    void testGetByPostIDValidOne(){
        // make a comment
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
        commentRepository.save(comment);
        // try to find comment
        long foundPostID = postRepository.findAll().get(0).getId(); // find a valid postID

        ResponseEntity<Iterable<CommentDTO>> response = commentController.getComments(foundPostID);
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<CommentDTO> comments = (List<CommentDTO>) response.getBody();
                    assertEquals(1,comments.size());
                }
        );
    }
    @Test
    void testGetByPostIDValidTwo(){
        // make 2 comments on the same post
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

        Comment comment1 = new Comment.CommentBuilder()
                .withPost(createdPost)
                .withContent("commentContent")
                .build();
        commentRepository.save(comment1);

        Comment comment2 = new Comment.CommentBuilder()
                .withPost(createdPost)
                .withContent("anotherCommentContent")
                .build();
        commentRepository.save(comment2);

        // try to find comment
        long foundPostID = postRepository.findAll().get(0).getId(); // find a valid postID

        ResponseEntity<Iterable<CommentDTO>> response = commentController.getComments(foundPostID);
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<CommentDTO> comments = (List<CommentDTO>) response.getBody();
                    assertEquals(2,comments.size());
                }
        );
    }
    // endregion
}
