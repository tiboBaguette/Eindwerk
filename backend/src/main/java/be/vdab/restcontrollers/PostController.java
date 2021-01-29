package be.vdab.restcontrollers;

import be.vdab.domain.Post;
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
    public ResponseEntity<Post> postCreate(@RequestBody Post post) {
        boolean result = postService.createPost(post);
        if(result){
            return new ResponseEntity<>(post,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @GetMapping("show-posts")
    public ResponseEntity<Iterable<Post>> getShowPosts(){
        return new ResponseEntity<>(postService.getPosts(),HttpStatus.OK);
    }
}
