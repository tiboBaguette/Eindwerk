package src.test.java.be.vdab.services;


import be.vdab.BackendApplication;
import be.vdab.domain.Category;
import be.vdab.domain.Post;
import be.vdab.dtos.PostDTO;
import be.vdab.domain.User;
import be.vdab.repositories.CategoryRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import be.vdab.services.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
@ActiveProfiles(value = "local")

class PostServiceImplTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;
    @Autowired
    UserRepository userRepository;
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
        assertFalse(postService.createPost((PostDTO) null));
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
        assertTrue(postService.createPost(post));

        assertAll(
                () -> assertFalse(postRepository.findPostsByTitle("title").isEmpty()),
                () -> assertNull(postRepository.findPostsByTitle("title").get(0).getCategory())
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
        assertTrue(postService.createPost(post));

        assertAll(
                () -> assertFalse(postRepository.findPostsByTitle("title").isEmpty()),
                () -> assertNotNull(postRepository.findPostsByTitle("title").get(0).getCategory())
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
        assertTrue(postService.createPost(post));

        assertAll(
                () -> assertFalse(postRepository.findPostsByTitle("title").isEmpty()),
                () -> assertNotNull(postRepository.findPostsByTitle("title").get(0).getCategory())
        );
    }


    // endregion

    // region test getAll

    @Test
    void testShowNoPostsAvailable(){
        List<Post> posts = (List<Post>) postService.getPosts();
        assertAll(
                () -> assertNotNull(posts),
                () -> assertTrue(posts.isEmpty())
        );
    }
    @Test
    void testShowOnePostAvailable(){
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
        List<Post> posts = (List<Post>) postService.getPosts();
        assertAll(
                () -> assertNotNull(posts),
                () -> assertEquals(1,posts.size())
        );

    }
    @Test
    void testShowMultiplePostsAvailableSameUser() {
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
        List<Post> posts = (List<Post>) postService.getPosts();
        assertAll(
                () -> assertNotNull(posts),
                () -> assertEquals(2,posts.size())
        );
    }

    @Test
    void testShowMultiplePostsAvailableDifferentUser(){
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
        List<Post> posts = (List<Post>) postService.getPosts();
        assertAll(
                () -> assertNotNull(posts),
                () -> assertEquals(2,posts.size())
        );
    }


    // endregion

    // region test getByID
    @Test
    void testGetByIDNull(){
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
        assertNull(postService.getPostByID(null));
    }
    @Test
    void testGetByIDInvalid(){
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
        assertNull(postService.getPostByID(-10L));  // -10L should NEVER be a valid index
    }
    @Test
    void testGetByIDValid(){
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

        Long idFound = postRepository.findAll().get(0).getId(); // get the id from the first existing post

        Post foundPost = postService.getPostByID(idFound);
        assertAll(
                () -> assertNotNull(foundPost),
                () -> assertEquals("title",foundPost.getTitle())
        );
    }
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
        assertTrue(postService.createPost(post1));
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
        assertTrue(postService.createPost(post2));
        // get posts from 'null'
        assertNull( postService.getPostsByCategory(null));

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
        assertTrue(postService.createPost(post1));
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
        assertTrue(postService.createPost(post2));
        // get posts from nonexistent category -> should return empty list?
        Category searchCategory = new Category()
                .setName("ThisCategoryNameDoesNotExist");
        List<Post> posts = (List<Post>) postService.getPostsByCategory(searchCategory);
        assertEquals(0,posts.size());
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
        assertTrue(postService.createPost(post1));
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
        assertTrue(postService.createPost(post2));
        // get posts from category1
        Category searchCategory = new Category()
                .setName("Cat1");
        List<Post> posts = (List<Post>) postService.getPostsByCategory(searchCategory);
        assertEquals(1,posts.size());
    }
    // endregion

    // region test deletePost
    @Test
    void testDeleteNull(){
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
        // delete post
       assertFalse(postService.deletePostByID(null));
       assertTrue(postRepository.findById(createdPost.getId()).isPresent());
    }
    @Test
    void testDeleteInvalidID(){
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
        // delete post
        assertFalse(postService.deletePostByID(-10L));
        assertTrue(postRepository.findById(createdPost.getId()).isPresent());
    }
    @Test
    void testDeleteValidID(){
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

        Post post2 = new Post.PostBuilder()
                .withTitle("title")
                .withContent("content")
                .withUser(createdUser)
                .build();
        Post dummyPost = postRepository.save(post2);
        // delete post
        assertTrue(postService.deletePostByID(createdPost.getId()));
        assertTrue(postRepository.findById(createdPost.getId()).isEmpty());
        assertEquals(1, postRepository.findAll().size());
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
        Post editedPost = postService.editPost((PostDTO)null);
        assertNull(editedPost);
    }
    @Test
    void testEditPostEmptyTitle(){
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
                .setTitle("")
                .setContent("editedContent")
                .setUser(createdUser) // valid user
                .setId(createdPost.getId());

        Post editedPost = postService.editPost(changedPost);
        assertNull(editedPost);
    }
    @Test
    void testEditPostEmptyContent(){
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
                .setContent("")
                .setUser(createdUser) // valid user
                .setId(createdPost.getId());

        Post editedPost = postService.editPost(changedPost);
        assertNull(editedPost);
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
        // make changed post
        Post changedPost = new Post.PostBuilder()
                .withTitle("editedTitle")
                .withContent("editedContent")
                .withUser(createdUser) // valid user
                .withId(createdPost.getId())
                .build();
        Post editedPost = postService.editPost(changedPost);

        assertAll(
                () -> assertNotNull(editedPost),
                () -> assertEquals("editedTitle",editedPost.getTitle()),
                () -> assertEquals("editedContent",editedPost.getContent())
        );
    }
    // endregion
}
