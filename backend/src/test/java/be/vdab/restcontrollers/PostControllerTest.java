package be.vdab.restcontrollers;

import be.vdab.BackendApplication;
import be.vdab.domain.Category;
import be.vdab.domain.Post;
import be.vdab.domain.User;
import be.vdab.repositories.CategoryRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class PostControllerTest {

    @Autowired
    PostController postController;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CategoryRepository categoryRepository;


    // region setup
    @BeforeEach
    void setup(){

    }

    @AfterEach
    void breakDown(){
        postRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
    }
    // endregion

    // region test create
    @Test
    void testCreatePostWithNull() {
        ResponseEntity<Post> response = postController.postCreate(null);
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
        ResponseEntity<Post> response = postController.postCreate(post);
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
        ResponseEntity<Post> response = postController.postCreate(post);
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
        ResponseEntity<Post> response = postController.postCreate(post);
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
        ResponseEntity<Post> response = postController.postCreate(post);
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
        ResponseEntity<Post> response = postController.postCreate(post);
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
        ResponseEntity<Post> response = postController.postCreate(post);
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
        ResponseEntity<Post> response = postController.postCreate(post);
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
        ResponseEntity<Post> response = postController.postCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }

    @Test
    void testCreatePostWithEverythingAndCategoryNull(){
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
                .withCategory(null)
                .build();
        ResponseEntity response = postController.postCreate(post);
        Post createdPost = (Post)response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertNull(createdPost.getCategory())
        );
    }

    @Test
    void testCreatePostWithEverythingAndCategoryEmpty(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser = userRepository.save(user);

        Category category = new Category()
                .setName("");
        Category createdCategory = categoryRepository.save(category);

        Post post = new Post.PostBuilder()
                .withTitle("title")
                .withContent("content")
                .withUser(createdUser)
                .withCategory(createdCategory)
                .build();

        ResponseEntity response = postController.postCreate(post);
        Post createdPost = (Post)response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertNotNull(createdPost.getCategory())
        );
    }
    @Test
    void testCreatePostWithEverythingAndCategoryValid(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser = userRepository.save(user);

        Category category = new Category()
                .setName("myCategory");
        Category createdCategory = categoryRepository.save(category);

        Post post = new Post.PostBuilder()
                .withTitle("title")
                .withContent("content")
                .withUser(createdUser)
                .withCategory(createdCategory)
                .build();

        ResponseEntity response = postController.postCreate(post);
        Post createdPost = (Post)response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertNotNull(createdPost.getCategory())
        );
    }
    // endregion

    // region test show-posts

    @Test
    void testShowPostNoPostsAvailable(){
        ResponseEntity response = postController.getShowPosts();
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<Post> posts= (List<Post>) (response.getBody());
                    assertEquals(0,posts.size());
                }
        );
    }
    @Test
    void testShowPostOnePostsAvailable(){
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
        ResponseEntity response = postController.getShowPosts();
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<Post> posts= (List<Post>) (response.getBody());
                    assertEquals(1,posts.size());
                }
        );
    }
    @Test
    void testShowPostTwoPostsAvailableSameUser(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser = userRepository.save(user);
        Post post1 = new Post.PostBuilder()
                .withTitle("title")
                .withContent("content")
                .withUser(createdUser)
                .build();
        postRepository.save(post1);
        Post post2 = new Post.PostBuilder()
                .withTitle("newTitle")
                .withContent("newContent")
                .withUser(createdUser)
                .build();
        postRepository.save(post2);
        ResponseEntity response = postController.getShowPosts();
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<Post> posts= (List<Post>) (response.getBody());
                    assertEquals(2,posts.size());
                }
        );
    }
    @Test
    void testShowPostTwoPostsAvailableDifferentUser(){
        User user1 = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser1 = userRepository.save(user1);
        User user2 = new User.UserBuilder()
                .withUsername("NewUsername")
                .withPassword("NewPassword")
                .withEmail("Newemail@gmail.com")
                .build();
        User createdUser2 = userRepository.save(user2);
        Post post1 = new Post.PostBuilder()
                .withTitle("title")
                .withContent("content")
                .withUser(createdUser1)
                .build();
        postRepository.save(post1);
        Post post2 = new Post.PostBuilder()
                .withTitle("newTitle")
                .withContent("newContent")
                .withUser(createdUser2)
                .build();
        postRepository.save(post2);
        ResponseEntity response = postController.getShowPosts();
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<Post> posts= (List<Post>) (response.getBody());
                    assertEquals(2,posts.size());
                }
        );
    }

    // endregion
}
