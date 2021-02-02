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
    public ResponseEntity<Post> getPostDetail(@PathVariable Long postid){
        Post foundPost = postService.getPostByID(postid);
        if(foundPost == null){
            return  new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(foundPost,HttpStatus.CREATED);
    }

}
