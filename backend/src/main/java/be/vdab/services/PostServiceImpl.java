package be.vdab.services;

import be.vdab.domain.*;
import be.vdab.dtos.PostDTO;
import be.vdab.repositories.*;
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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LikeRepository likeRepository;


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
            // TODO: check for invalid users with same name/mail as valid user
        }

        if(post.getCategory() != null){ // check if the category needs saving
            if(categoryRepository.findCategoryByName(post.getCategory().getName()) == null){
                categoryRepository.save(post.getCategory());
            }
            else{
                // link the found category to this post
                // TODO: find a better way to do this?
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

        User user = userRepository.findUserByUsername(postDTO.getUser());

        Post post = new Post.PostBuilder()
                .withUser(user)
                .withTitle(postDTO.getTitle())
                .withContent(postDTO.getContent())
                .withCategory(category)
                .build();

        return createPost(post);
    }

    @Override
    public List<Post> getPosts() {
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
    public Iterable<Post> getPostsByCategory(Category category) {
        if(category == null){
            return null;
        }
        String categoryName = category.getName();
        if(categoryName == null){
            return null;
        }
        return postRepository.findPostsByCategory_Name(categoryName);
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
        List<Like> likes = (List<Like>) likeRepository.findAllByPost_Id(postID);
        likeRepository.deleteAll(likes);
        postRepository.deleteById(postID);
        return true;
    }

    @Override
    public Post editPost(Post post) {
        if(post == null){
            return null;    // data validity check
        }
        if(postRepository.findById(post.getId()).isEmpty()){
            return null;    // only edit existing posts
        }
        if(post.getTitle() == null || post.getContent() == null){
            return null;    // data validity check
        }
        if(post.getTitle().equals("") || post.getContent().equals("")){
            return null;    // data validity check
        }
        postRepository.updatePost(post.getId(), post.getTitle(),post.getContent(),post.getCategory());
        return postRepository.findById(post.getId()).orElse(null); //should always return a post

    }

    @Override
    public Post editPost(PostDTO postDTO){
        if(postDTO == null){
            return null;
        }
        // cast postDTO to post and call editPost(Post)
        Category category = categoryRepository.findCategoryByName(postDTO.getCategory());
        if(category == null && postDTO.getCategory() != null){
            category = new Category().setName(postDTO.getCategory());
            categoryService.addCategory(category);
        }

            User user = userRepository.findUserByUsername(postDTO.getUser());


        Post post = new Post.PostBuilder()
                .withId(postDTO.getId())
                .withTitle(postDTO.getTitle())
                .withContent(postDTO.getContent())
                .withUser(user)
                .withCategory(category)
                .withCreationTime(postDTO.getCreationTime())
                .build();
        return editPost(post);
    }
}
