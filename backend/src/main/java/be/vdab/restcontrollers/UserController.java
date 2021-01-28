package be.vdab.restcontrollers;

import be.vdab.domain.User;
import be.vdab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("register")
    public ResponseEntity<User> postRegister(@RequestBody User user){
        boolean result = userService.registerUser(user);
        if(result){
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,new HttpHeaders() ,HttpStatus.CONFLICT);
    }

    @PostMapping("login")
    public ResponseEntity<User> postLogin(@RequestBody User user){
        User loggedUser = userService.login(user);
        if(loggedUser != null){
            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
}
