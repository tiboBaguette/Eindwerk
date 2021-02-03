package be.vdab.services;

import be.vdab.domain.Category;
import be.vdab.domain.Comment;
import be.vdab.domain.Post;
import be.vdab.dtos.PostDTO;
import be.vdab.repositories.CategoryRepository;
import be.vdab.repositories.CommentRepository;
import be.vdab.repositories.PostRepository;
import be.vdab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public boolean createPost(Post post) {
        //  TODO: refactor this code
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

        if(post.getCategory() != null){ // check if the category needs saving
            if(categoryRepository.findCategoryByName(post.getCategory().getName()) == null){
                categoryRepository.save(post.getCategory());
            }
            else{
                // link the found category to this post
                // TODO: find a better way to do this
                post = new Post.PostBuilder()
                        .withCategory(categoryRepository.findCategoryByName(post.getCategory().getName()))
                        .withContent(post.getContent())
                        .withTitle(post.getTitle())
                        .withUser(post.getUser())
                        .build();
            }
        }

        post.setCreationTime(LocalDateTime.now());
        postRepository.save(post);
        return true;
    }

    @Override
    public boolean createPost(PostDTO postDTO) {
        if(postDTO == null){
            return false;
        }
        Category category = new Category().setName(postDTO.getCategory());

        Post post = new Post.PostBuilder()
                .withUser(postDTO.getUser())
                .withTitle(postDTO.getTitle())
                .withContent(postDTO.getContent())
                .withCategory(category)
                .build();

        return createPost(post);
    }

    @Override
    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostByID(Long postID) {
        if(postID == null){
            return null;
        }
        return postRepository.findById(postID).orElse(null); // return post if found, or else null
    }

    @Override
    public boolean deletePostByID(Long postID) {
        if(postID == null){
            return false;
        }
        if(postRepository.findById(postID).isEmpty()){ // make sure postID is valid
            return false;
        }
        List<Comment> comments = (List<Comment>) commentRepository.findCommentsByPost_Id(postID);
        commentRepository.deleteAll(comments);
        postRepository.deleteById(postID);
        return true;
    }
}
