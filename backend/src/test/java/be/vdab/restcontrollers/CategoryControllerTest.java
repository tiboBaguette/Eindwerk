package be.vdab.restcontrollers;

import be.vdab.BackendApplication;
import be.vdab.domain.Category;
import be.vdab.repositories.CategoryRepository;
import be.vdab.services.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class CategoryControllerTest {

    @Autowired
    CategoryController categoryController;
    @Autowired
    CategoryRepository categoryRepository;


    // region setup
    @BeforeEach
    void setup(){

    }
    @AfterEach
    void breakDown(){
        categoryRepository.deleteAll();
    }
    // endregion

    // region test Add category

    @Test
    void testAddCategoryNull(){
        ResponseEntity response = categoryController.postAddCategory(null);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }
    @Test
    void testAddCategoryEmpty(){
        Category category = new Category();
        category.setName("");

        ResponseEntity response = categoryController.postAddCategory(category);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }
    @Test
    void testAddCategoryValid(){

        Category category = new Category();
        category.setName("newCategory");

        ResponseEntity response = categoryController.postAddCategory(category);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    Category categoryFound = (Category) response.getBody();
                    assertEquals("newCategory", categoryFound.getName());
                }
        );
    }
    // endregion


}
