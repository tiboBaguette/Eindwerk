package be.vdab.services;

import be.vdab.domain.Post;
import be.vdab.repositories.CategoryRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public boolean createPost(Post post) {
        if(post == null){
            return false;   // no post given
        }
        if(post.getContent() == null || post.getTitle() == null || post.getUser() == null){
            return false;   // required field missing
        }
        if(post.getContent().equals("") || post.getTitle().equals("")){
            return false;   // don't accept empty title/content
        }
        if(userRepository.findUserByUsernameOrEmail(post.getUser().getUsername(),post.getUser().getEmail()).isEmpty()){
            return false;   // user could not be found in database -> invalid user
        }

        if(post.getCategory() != null){
            if(categoryRepository.findCategoriesByName(post.getCategory().getName()).isEmpty()){
                categoryRepository.save(post.getCategory());
            }
        }

        post.setCreationTime(LocalDateTime.now());
        postRepository.save(post);
        return true;
    }

    @Override
    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    }
}
