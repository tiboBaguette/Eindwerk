package be.vdab.restcontrollers;

import be.vdab.domain.Comment;
import be.vdab.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments/")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("add")
    public ResponseEntity<Comment> postAddComment(@RequestBody Comment comment){
        boolean result = commentService.createComment(comment);
        if(result){
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,new HttpHeaders(),HttpStatus.CONFLICT);
    }

    @GetMapping("show/{postid}")
    public ResponseEntity<Iterable<Comment>> getComments(@PathVariable long postid){
        return new ResponseEntity<>(null,new HttpHeaders(),HttpStatus.NOT_IMPLEMENTED);
    }

}
