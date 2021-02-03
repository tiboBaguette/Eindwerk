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

    @GetMapping("detail/{postid}")
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

    @PutMapping("edit/{postid}")
    public ResponseEntity<String> putEditPost(@RequestBody Post post, @PathVariable(value = "postid") Long postid){
        if(post == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if(!post.getId().equals(postid) && postid != null){ // postid gets priority over post.getId().
            System.out.println("[WARN] postid does not match post.getId()! using postid as id");
            post = new Post.PostBuilder()
                    .withId(postid)
                    .withUser(post.getUser())
                    .withTitle(post.getTitle())
                    .withContent(post.getContent())
                    .withCategory(post.getCategory())
                    .withCreationTime(post.getCreationTime())
                    .build();
        }
        Post editedPost = postService.editPost(post);
        if(editedPost == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        // no need to send edited post back?
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
