package be.vdab.restcontrollers;

import be.vdab.domain.Category;
import be.vdab.domain.Post;
import be.vdab.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories/")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("add")
    public ResponseEntity<Category> postAddCategory(@RequestBody Category category) {
        boolean result = categoryService.addCategory(category);
        if(result){
            return new ResponseEntity<>(category,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @GetMapping("show")
    public ResponseEntity<Iterable<Category>> getShowCategories(){
        Iterable<Category> categories = categoryService.getCategories();
        return new ResponseEntity<>(categories,new HttpHeaders(), HttpStatus.OK);
    }
}
