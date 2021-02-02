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
        post.setTitle("Title");
        ResponseEntity<PostDTO> response = postController.postCreate(post);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testCreatePostWithContent() {
        PostDTO post = new PostDTO();
        post.setContent("Content");

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
        post.setUser(user);


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
        post.setUser(user);

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
        post.setTitle("title");
        post.setContent("content");

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
        post.setTitle("title");
        post.setUser(user);

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
        post.setContent("content");
        post.setUser(user);
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

        PostDTO post = new PostDTO();
        post.setTitle("title");
        post.setContent("content");
        post.setUser(user);
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
        post.setTitle("title");
        post.setContent("content");
        post.setUser(createdUser);
        post.setCategory(null);

        ResponseEntity<PostDTO> response = postController.postCreate(post);
        PostDTO createdPost = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    assert createdPost != null;
                    assertNull(createdPost.getCategory());
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
        post.setTitle("title");
        post.setContent("content");
        post.setUser(createdUser);
        post.setCategory("");

        ResponseEntity<PostDTO> response = postController.postCreate(post);
        PostDTO createdPost = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    assert createdPost != null;
                    assertNotNull(createdPost.getCategory());
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
        post.setTitle("title");
        post.setContent("content");
        post.setUser(createdUser);
        post.setCategory("myCategory");


        ResponseEntity<PostDTO> response = postController.postCreate(post);
        PostDTO createdPost = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    assert createdPost != null;
                    assertNotNull(createdPost.getCategory());
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
        post1.setTitle("title1");
        post1.setContent("content1");
        post1.setUser(createdUser);
        post1.setCategory("Cat1");
        ResponseEntity<PostDTO> response1 = postController.postCreate(post1);

        PostDTO post2 = new PostDTO();
        post2.setTitle("title2");
        post2.setContent("content2");
        post2.setUser(createdUser);
        post2.setCategory("Cat1");
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

    // region test get-details
    @Test
    void testGetDetailsNull(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        userRepository.save(user);

        PostDTO post = new PostDTO();
        post.setTitle("title");
        post.setContent("content");
        post.setUser(user);
        assertNotNull(postController.postCreate(post)); // make sure there is a post
        ResponseEntity<Post> response = postController.getPostDetail(null);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }
    @Test
    void testGetDetailsInvalidID(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        userRepository.save(user);

        PostDTO post = new PostDTO();
        post.setTitle("title");
        post.setContent("content");
        post.setUser(user);
        assertNotNull(postController.postCreate(post)); // make sure there is a post
        ResponseEntity<Post> response = postController.getPostDetail(-10L);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }
    @Test
    void testGetDetailsValidID(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        userRepository.save(user);

        PostDTO post = new PostDTO();
        post.setTitle("title");
        post.setContent("content");
        post.setUser(user);
        assertNotNull(postController.postCreate(post)); // make sure there is a post

        Long idFound = postRepository.findAll().get(0).getId(); // get the id from the first existing post

        ResponseEntity<Post> response = postController.getPostDetail(idFound);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }



    // endregion
}
