package be.vdab.services;

import be.vdab.domain.Comment;
import be.vdab.domain.Post;
import be.vdab.domain.User;
import be.vdab.repositories.CommentRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentService commentService;
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
    void testCreateCommentNull() {
        assertFalse(commentService.createComment(null));
    }
    @Test
    void testCreateCommentWithEmptyContent() {
        Comment comment = new Comment.CommentBuilder()
                .withContent("")
                .build();
        assertFalse(commentService.createComment(comment));
    }
    @Test
    void testCreateCommentWithContent() {
        Comment comment = new Comment.CommentBuilder()
                .withContent("This is a comment")
                .build();
        assertFalse(commentService.createComment(comment));
    }
    @Test
    void testCreateCommentWithPost() {
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
        assertFalse(commentService.createComment(comment));
    }

    @Test
    void testCreateCommentWithPostEmptyComment(){
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
        assertFalse(commentService.createComment(comment));
    }
    @Test
    void testCreateCommentWithPostAndComment(){
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
        assertTrue(commentService.createComment(comment));
    }


    // endregion

}
