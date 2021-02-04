package be.vdab.restcontrollers;

import be.vdab.domain.Comment;
import be.vdab.dtos.CommentDTO;
import be.vdab.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("add")
    public ResponseEntity<CommentDTO> postAddComment(@RequestBody Comment comment){
        boolean result = commentService.createComment(comment);
        if(result){
            return new ResponseEntity<>(new CommentDTO(comment), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,new HttpHeaders(),HttpStatus.CONFLICT);
    }

    @GetMapping("show/:{postid}")
    public ResponseEntity<Iterable<CommentDTO>> getComments(@PathVariable(value = "postid") Long postid){
        // get comments
        List<Comment> comments = (List<Comment>) commentService.getCommentsByPostID(postid);
        // cast comments to commentDTOs
        List<CommentDTO>commentDTOs = new ArrayList<>();
        comments.forEach(comment -> commentDTOs.add(new CommentDTO(comment)));

        return new ResponseEntity<>(commentDTOs,HttpStatus.OK);
    }

}
