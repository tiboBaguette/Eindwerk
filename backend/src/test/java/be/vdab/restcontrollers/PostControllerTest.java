
package src.test.java.be.vdab.restcontrollers;

import be.vdab.BackendApplication;
import be.vdab.domain.Category;
import be.vdab.domain.Post;
import be.vdab.dtos.PostDTO;
import be.vdab.domain.User;
import be.vdab.repositories.CategoryRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import be.vdab.restcontrollers.PostController;
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
        post.setUser(user.getUsername());


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
        post.setUser(user.getUsername());

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
        post.setUser(user.getUsername());

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
        post.setUser(user.getUsername());
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
        post.setUser(user.getUsername());
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
        post.setUser(createdUser.getUsername());
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
        post.setUser(createdUser.getUsername());
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
        post.setUser(createdUser.getUsername());
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
        post1.setUser(createdUser.getUsername());
        post1.setCategory("Cat1");
        ResponseEntity<PostDTO> response1 = postController.postCreate(post1);

        PostDTO post2 = new PostDTO();
        post2.setTitle("title2");
        post2.setContent("content2");
        post2.setUser(createdUser.getUsername());
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
        ResponseEntity<Iterable<PostDTO>> response = postController.getShowPosts();
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<PostDTO> posts = (List<PostDTO>) (response.getBody());
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
        ResponseEntity<Iterable<PostDTO>> response = postController.getShowPosts();
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<PostDTO> posts= (List<PostDTO>) (response.getBody());
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
        ResponseEntity<Iterable<PostDTO>> response = postController.getShowPosts();
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<PostDTO> posts= (List<PostDTO>) (response.getBody());
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
        ResponseEntity<Iterable<PostDTO>> response = postController.getShowPosts();
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<PostDTO> posts= (List<PostDTO>) (response.getBody());
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
        post.setUser(user.getUsername());
        assertNotNull(postController.postCreate(post)); // make sure there is a post
        ResponseEntity<PostDTO> response = postController.getPostDetail(null);
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
        post.setUser(user.getUsername());
        assertNotNull(postController.postCreate(post)); // make sure there is a post
        ResponseEntity<PostDTO> response = postController.getPostDetail(-10L);
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
        post.setUser(user.getUsername());
        assertNotNull(postController.postCreate(post)); // make sure there is a post

        Long idFound = postRepository.findAll().get(0).getId(); // get the id from the first existing post

        ResponseEntity<PostDTO> response = postController.getPostDetail(idFound);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
    }
    // endregion

    // region test deletePost
    @Test
    void testDeleteNull(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser = userRepository.save(user);

        Post post = new Post.PostBuilder()
                .withTitle("postTitle")
                .withContent("postContent")
                .withUser(createdUser)
                .build();
        Post createdPost = postRepository.save(post);

        ResponseEntity<String> response = postController.deletePost(null);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertEquals("Delete failed",response.getBody())
        );
    }
    @Test
    void testDeleteInvalid(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser = userRepository.save(user);

        Post post = new Post.PostBuilder()
                .withTitle("postTitle")
                .withContent("postContent")
                .withUser(createdUser)
                .build();
        Post createdPost = postRepository.save(post); // createdPost is valid

        Post invalidPost = new Post.PostBuilder() // invalidPost is invalid
                .withTitle("postTitle")
                .withContent("postContent")
                .withUser(createdUser)
                .build();


        ResponseEntity<String> response = postController.deletePost(invalidPost);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertEquals("Delete failed",response.getBody())
        );
    }
    @Test
    void testDeleteValid(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser = userRepository.save(user);

        Post post = new Post.PostBuilder()
                .withTitle("postTitle")
                .withContent("postContent")
                .withUser(createdUser)
                .build();
        Post createdPost = postRepository.save(post); // createdPost is valid
        Post post2 = new Post.PostBuilder()
                .withTitle("postTitle")
                .withContent("postContent")
                .withUser(createdUser)
                .build();
        Post dummyPost = postRepository.save(post2); // dummyPost is valid and should exist after delete


        ResponseEntity<String> response = postController.deletePost(createdPost);
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertEquals("Delete success",response.getBody()),
                () -> assertEquals(1,postRepository.findAll().size())
        );
    }

    // endregion

    // region test editPost
    @Test
    void testEditPostNull(){
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
        Post createdPost = postRepository.save(post);

        ResponseEntity<String> response = postController.putEditPost(null,null);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());

    }
    @Test
    void testEditPostWithUserNull(){
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
        Post createdPost = postRepository.save(post);

        PostDTO changedPost = new PostDTO()
                .setTitle("editedTitle")
                .setContent("editedContent")
                .setUser(null)
                .setId(createdPost.getId());

        ResponseEntity<String> response = postController.putEditPost(changedPost, changedPost.getId());
        // even though user is invalid, it is not updated and thus not checked
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    void testEditPostWithUserInvalid(){
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
        Post createdPost = postRepository.save(post);

        User invalidUser = new User.UserBuilder()
                .withUsername("invalid")
                .withPassword("invalid")
                .withEmail("invalid@gmail.com")
                .build();
        // not saved -> does not exist on database -> invalid user
        PostDTO changedPost = new PostDTO()
                .setTitle("editedTitle")
                .setContent("editedContent")
                .setUser(invalidUser.getUsername())
                .setId(createdPost.getId());


        ResponseEntity<String> response = postController.putEditPost(changedPost, changedPost.getId());
        // even though user is invalid, it is not updated and thus not checked
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    void testEditPostValid(){
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
        Post createdPost = postRepository.save(post);


        PostDTO changedPost = new PostDTO()
                .setTitle("editedTitle")
                .setContent("editedContent")
                .setUser(createdUser.getUsername())
                .setId(createdPost.getId());


        ResponseEntity<String> response = postController.putEditPost(changedPost, changedPost.getId());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    //  TODO: add tests for when postid != post.getId()
    // endregion

    // region test getByCategory
    @Test
    void testGetPostsByCategoryNull(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser = userRepository.save(user);
        // make post with category1
        Category category1 = new Category()
                .setName("Cat1");
        Category createdCategory1 = categoryRepository.save(category1);

        Post post1 = new Post.PostBuilder()
                .withTitle("title1")
                .withContent("content1")
                .withUser(createdUser)
                .withCategory(createdCategory1)
                .build();
        postRepository.save(post1);
        // make post with category2
        Category category2 = new Category()
                .setName("Dog2");
        Category createdCategory2 = categoryRepository.save(category2);

        Post post2 = new Post.PostBuilder()
                .withTitle("title2")
                .withContent("content2")
                .withUser(createdUser)
                .withCategory(createdCategory2)
                .build();
        postRepository.save(post2);
        // get posts from 'null'
        ResponseEntity<Iterable<PostDTO>> response = postController.getPostByCategory(null);
        assertAll(
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );

    }
    @Test
    void testGetPostsByCategoryNonExist(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser = userRepository.save(user);
        // make post with category1
        Category category1 = new Category()
                .setName("Cat1");
        Category createdCategory1 = categoryRepository.save(category1);

        Post post1 = new Post.PostBuilder()
                .withTitle("title1")
                .withContent("content1")
                .withUser(createdUser)
                .withCategory(createdCategory1)
                .build();
        postRepository.save(post1);
        // make post with category2
        Category category2 = new Category()
                .setName("Dog2");
        Category createdCategory2 = categoryRepository.save(category2);

        Post post2 = new Post.PostBuilder()
                .withTitle("title2")
                .withContent("content2")
                .withUser(createdUser)
                .withCategory(createdCategory2)
                .build();
        postRepository.save(post2);
        // get posts from empty string
        ResponseEntity<Iterable<PostDTO>> response = postController.getPostByCategory("");
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<PostDTO> posts = (List<PostDTO>) response.getBody();
                    assertEquals(0,posts.size());
                }
        );

    }
    @Test
    void testGetPostsByCategoryExists(){
        User user = new User.UserBuilder()
                .withUsername("Username")
                .withPassword("Password")
                .withEmail("email@gmail.com")
                .build();
        User createdUser = userRepository.save(user);
        // make post with category1
        Category category1 = new Category()
                .setName("Cat1");
        Category createdCategory1 = categoryRepository.save(category1);

        Post post1 = new Post.PostBuilder()
                .withTitle("title1")
                .withContent("content1")
                .withUser(createdUser)
                .withCategory(createdCategory1)
                .build();
        postRepository.save(post1);
        // make post with category2
        Category category2 = new Category()
                .setName("Dog2");
        Category createdCategory2 = categoryRepository.save(category2);

        Post post2 = new Post.PostBuilder()
                .withTitle("title2")
                .withContent("content2")
                .withUser(createdUser)
                .withCategory(createdCategory2)
                .build();
        postRepository.save(post2);
        // get posts from category1
        ResponseEntity<Iterable<PostDTO>> response = postController.getPostByCategory("Cat1");
        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<PostDTO> posts = (List<PostDTO>) response.getBody();
                    assertEquals(1,posts.size());
                }
        );

    }
    // endregion
}

