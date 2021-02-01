package be.vdab.restcontrollers;

import be.vdab.BackendApplication;
import be.vdab.domain.Category;
import be.vdab.domain.Post;
import be.vdab.dtos.PostDTO;
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
        // make & delete post to ensure eindwerk.post table exists
        Post post = new Post();
        postRepository.save(post);
        //postRepository.deleteAll();
        // test begins here
        ResponseEntity<PostDTO> response = postController.postCreate(null);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testCreatePostWithTitle() {
        PostDTO post = new PostDTO();
        post.title = "Title";
        ResponseEntity<PostDTO> response = postController.postCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testCreatePostWithContent() {
        PostDTO post = new PostDTO();
        post.content = "Content";

        ResponseEntity<PostDTO> response = postController.postCreate(post);
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
//        Post post = new Post.PostBuilder()
//                .withUser(user)
//                .build();
//
        PostDTO post = new PostDTO();
        post.user = user;


        ResponseEntity<PostDTO> response = postController.postCreate(post);
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
//        Post post = new Post.PostBuilder()
//                .withUser(user)
//                .build();
        PostDTO post = new PostDTO();
        post.user = user;

        ResponseEntity<PostDTO> response = postController.postCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );

    }

    @Test
    void testCreatePostWithTitleAndContent() {
//        Post post = new Post.PostBuilder()
//                .withTitle("title")
//                .withContent("content")
//                .build();
        PostDTO post = new PostDTO();
        post.title = "title";
        post.content = "content";

        ResponseEntity<PostDTO> response = postController.postCreate(post);
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
//        Post post = new Post.PostBuilder()
//                .withTitle("title")
//                .withUser(user)
//                .build();
        PostDTO post = new PostDTO();
        post.title = "title";
        post.user = user;

        ResponseEntity<PostDTO> response = postController.postCreate(post);
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
//        Post post = new Post.PostBuilder()
//                .withContent("content")
//                .withUser(user)
//                .build();
        PostDTO post = new PostDTO();
        post.content = "content";
        post.user = user;
        ResponseEntity<PostDTO> response = postController.postCreate(post);
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
//        Post post = new Post.PostBuilder()
//                .withTitle("title")
//                .withContent("content")
//                .withUser(user)
//                .build();
        PostDTO post = new PostDTO();
        post.title = "title";
        post.content = "content";
        post.user = user;
        ResponseEntity<PostDTO> response = postController.postCreate(post);
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
//        Post post = new Post.PostBuilder()
//                .withTitle("title")
//                .withContent("content")
//                .withUser(createdUser)
//                .withCategory(null)
//                .build();
        PostDTO post = new PostDTO();
        post.title = "title";
        post.content = "content";
        post.user = createdUser;
        post.category = null;

        ResponseEntity<PostDTO> response = postController.postCreate(post);
        PostDTO createdPost = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    assert createdPost != null;
                    assertNull(createdPost.category);
                }
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
//
//        Category category = new Category()
//                .setName("");
//        Category createdCategory = categoryRepository.save(category);
//
//        Post post = new Post.PostBuilder()
//                .withTitle("title")
//                .withContent("content")
//                .withUser(createdUser)
//                .withCategory(createdCategory)
//                .build();
//
        PostDTO post = new PostDTO();
        post.title = "title";
        post.content = "content";
        post.user = createdUser;
        post.category = "";

        ResponseEntity<PostDTO> response = postController.postCreate(post);
        PostDTO createdPost = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    assert createdPost != null;
                    assertNotNull(createdPost.category);
                }
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

//        Category category = new Category()
//                .setName("myCategory");
//        Category createdCategory = categoryRepository.save(category);
//
//        Post post = new Post.PostBuilder()
//                .withTitle("title")
//                .withContent("content")
//                .withUser(createdUser)
//                .withCategory(createdCategory)
//                .build();
        PostDTO post = new PostDTO();
        post.title = "title";
        post.content = "content";
        post.user = createdUser;
        post.category = "myCategory";


        ResponseEntity<PostDTO> response = postController.postCreate(post);
        PostDTO createdPost = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    assert createdPost != null;
                    assertNotNull(createdPost.category);
                }
        );
    }

    @Test
    void testCreatePostTwoWithSameCategory(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser = userRepository.save(user);

        PostDTO post1 = new PostDTO();
        post1.title = "title1";
        post1.content = "content1";
        post1.user = createdUser;
        post1.category = "Cat1";
        ResponseEntity<PostDTO> response1 = postController.postCreate(post1);

        PostDTO post2 = new PostDTO();
        post2.title = "title2";
        post2.content = "content2";
        post2.user = createdUser;
        post2.category = "Cat1";
        ResponseEntity<PostDTO> response2 = postController.postCreate(post2);

        Category searchCategory = new Category();
        searchCategory.setName("Cat1");

        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response1.getStatusCode()),
                () -> assertEquals(HttpStatus.CREATED,response2.getStatusCode())
                //() -> assertEquals(2,postRepository.findPostsByCategoryLike(searchCategory).size())
        );
    }


    // endregion

    // region test show-posts

    @Test
    void testShowPostNoPostsAvailable(){
        ResponseEntity<Iterable<Post>> response = postController.getShowPosts();
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<Post> posts = (List<Post>) (response.getBody());
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
        ResponseEntity<Iterable<Post>> response = postController.getShowPosts();
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<Post> posts= (List<Post>) (response.getBody());
                    assertEquals(1,posts.size());
                }
        );
    }
/*    @Test
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
        ResponseEntity<Iterable<Post>> response = postController.getShowPosts();
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<Post> posts= (List<Post>) (response.getBody());
                    assertEquals(2,posts.size());
                }
        );
    }*/
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
        ResponseEntity<Iterable<Post>> response = postController.getShowPosts();
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
