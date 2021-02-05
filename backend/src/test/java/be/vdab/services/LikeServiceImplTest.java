package src.test.java.be.vdab.services;

import be.vdab.BackendApplication;
import be.vdab.domain.Like;
import be.vdab.domain.Post;
import be.vdab.domain.User;
import be.vdab.repositories.LikeRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import be.vdab.services.LikeService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = BackendApplication.class)
@ActiveProfiles(value = "local")
class LikeServiceImplTest {

    @Autowired
    LikeRepository likeRepository;
    @Autowired
    LikeService likeService;
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

    // region test addLike
    @Test
    void testAddNull(){
        Like createdLike = likeService.addLike(null,null);
        assertNull(createdLike);
    }

    @Test
    void testAddOnlyPostID(){
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        Like createdLike = likeService.addLike(post.getId(),null);
        assertNull(createdLike);
    }

    @Test
    void testAddOnlyUserName(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        Like createdLike = likeService.addLike(null,user.getUsername());
        assertNull(createdLike);
    }

    @Test
    void testAddInvalidPostID(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        Like createdLike = likeService.addLike(-10L,user.getUsername());
        assertNull(createdLike);
    }

    @Test
    void testAddInvalidUserName(){
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        Like createdLike = likeService.addLike(post.getId(),"thisUsernameDoesNotExist");
        assertNull(createdLike);
    }

    @Test
    void testAddValid(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        Like createdLike = likeService.addLike(post.getId(),user.getUsername());
        assertAll(
                () -> assertNotNull(createdLike),
                () -> assertNotNull(createdLike.getPost()),
                () -> assertNotNull(createdLike.getUser())
        );
    }

    // endregion

    // region test removeLike
    @Test
    void testRemoveNull(){
        assertFalse(likeService.removeLike(null,null));

    }

    @Test
    void testRemoveOnlyPostID(){
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        assertFalse(likeService.removeLike(post.getId(),null));
    }

    @Test
    void testRemoveOnlyUserName(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        assertFalse(likeService.removeLike(null,user.getUsername()));
    }

    @Test
    void testRemoveInvalidPostID(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        assertFalse(likeService.removeLike(-10L,user.getUsername()));
    }

    @Test
    void testRemoveInvalidUserName(){
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        assertFalse(likeService.removeLike(post.getId(),"thisUsernameDoesNotExist"));
    }

    @Test
    void testRemoveValid(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        likeRepository.save(new Like(post,user));
        boolean result =likeService.removeLike(post.getId(),user.getUsername());
        assertTrue(result);
    }
    // endregion

    // region test getLikes
    @Test
    void testGetLikesNull(){
        Integer likeAmount = likeService.getLikes(null);
        assertNull(likeAmount);
    }
    @Test
    void testGetLikesInvalidID(){
        Integer likeAmount = likeService.getLikes(-10L);
        assertNull(likeAmount);

    }
    @Test
    void testGetLikesValidZero(){
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        Integer likeAmount = likeService.getLikes(post.getId());
        assertEquals(0,likeAmount);

    }
    @Test
    void testGetLikesValidOne(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        likeRepository.save(new Like(post,user));
        Integer likeAmount = likeService.getLikes(post.getId());
        assertEquals(1,likeAmount);

    }
    @Test
    void testGetLikesValidTwo(){
        User user = userRepository.findAll().get(0); // get createdUser from setup
        Post post = postRepository.findAll().get(0); // get createdPost from setup
        likeRepository.save(new Like(post,user));

        User user2 = new User.UserBuilder()
                .withUsername("User2")
                .withPassword("Pass2")
                .withEmail("Mail2@Gmail.com")
                .build();
        userRepository.save(user2);
        likeRepository.save(new Like(post,user2));

        Integer likeAmount = likeService.getLikes(post.getId());
        assertEquals(2,likeAmount);

    }
    // endregion


}
