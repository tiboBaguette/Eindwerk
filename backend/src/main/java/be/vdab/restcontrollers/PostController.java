package be.vdab.restcontrollers;

import be.vdab.domain.Category;
import be.vdab.domain.Post;
import be.vdab.dtos.PostDTO;
import be.vdab.domain.User;
import be.vdab.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("create")
    public ResponseEntity<PostDTO> postCreate(@RequestBody PostDTO postdto) {

        boolean result = postService.createPost(postdto);

        if(result){
            return new ResponseEntity<>(postdto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @GetMapping("show")
    public ResponseEntity<Iterable<Post>> getShowPosts(){
        return new ResponseEntity<>(postService.getPosts(),HttpStatus.OK);
    }

    @GetMapping("detail/:{postid}")
    public ResponseEntity<Post> getPostDetail(@PathVariable(value = "postid") Long postid){
        Post foundPost = postService.getPostByID(postid);
        if(foundPost == null){
            return  new ResponseEntity<>(new HttpHeaders(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(foundPost,HttpStatus.CREATED);
    }

    @PostMapping("delete")
    public ResponseEntity<String> deletePost(@RequestBody Post post){
        if(post == null){
            return new ResponseEntity<>("Delete failed", new HttpHeaders(), HttpStatus.CONFLICT);
        }
        boolean result = postService.deletePostByID(post.getId());
        if(result){
            return new ResponseEntity<>("Delete success", new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Delete failed", new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @PutMapping("edit/:{postid}")
    public ResponseEntity<String> putEditPost(@RequestBody PostDTO postDTO, @PathVariable(value = "postid") Long postid){
        // test data validity and set proper id
        if(postDTO == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if(postDTO.getId() == null){
            if(postid == null){
                return new ResponseEntity<>(HttpStatus.CONFLICT); // no id present, invalid
            }
            postDTO.setId(postid);  // only postid available -> use postid
        } else if(postid != null){
            postDTO.setId(postid); // use postid if available
        }
        // -- else use id from postDTO --

        Post editedPost = postService.editPost(postDTO);
        if(editedPost == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("get-by-category/:{categoryName}")
    public ResponseEntity<Iterable<Post>> getPostByCategory(@PathVariable String categoryName){
        if(categoryName == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Category category = new Category()
                .setName(categoryName);
        List<Post> foundPosts = (List<Post>) postService.getPostsByCategory(category);
        if(foundPosts == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(foundPosts,HttpStatus.OK);
    }

}
