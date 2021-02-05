package be.vdab.restcontrollers;

import be.vdab.domain.Like;
import be.vdab.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes/")
@CrossOrigin(origins = "http://localhost:4200")
public class LikeController {

    @Autowired
    LikeService likeService;

    @PostMapping("like/:{postID}")
    public ResponseEntity<String> postAddLike(@PathVariable Long postID, @RequestBody String username){
        if(postID == null || username == null){
            return new ResponseEntity<>("invalid data",HttpStatus.CONFLICT);
        }
        Like createdLike = likeService.addLike(postID,username);
        if(createdLike == null){
            return new ResponseEntity<>("invalid data",HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("like added",HttpStatus.CREATED);
    }

    @PostMapping("unlike/:{postID}")
    public ResponseEntity<String> postRemoveLike(@PathVariable Long postID, @RequestBody String username){
        if(postID == null || username == null){
            return new ResponseEntity<>("invalid data",HttpStatus.CONFLICT);
        }
        boolean result = likeService.removeLike(postID,username);
        if(result){
            return new ResponseEntity<>("like removed",HttpStatus.OK);
        }
        return new ResponseEntity<>("invalid data",HttpStatus.CONFLICT);

    }

    @GetMapping("getLikes/:{postID}")
    public ResponseEntity<Integer> getGetLikes(@PathVariable Long postID){
        if(postID == null){
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }
        Integer result = likeService.getLikes(postID);
        if(result == null){
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


}
