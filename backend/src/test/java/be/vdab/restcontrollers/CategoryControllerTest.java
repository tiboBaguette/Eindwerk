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

import java.util.ArrayList;
import java.util.List;

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
        ResponseEntity<Category> response = categoryController.postAddCategory(null);
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

        ResponseEntity<Category> response = categoryController.postAddCategory(category);
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

        ResponseEntity<Category> response = categoryController.postAddCategory(category);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    Category categoryFound = response.getBody();
                    assertEquals("newCategory", categoryFound.getName());
                }
        );
    }

    @Test
    void testAddCategorySecondDuplicate(){
        Category category1 = new Category();
        category1.setName("Cat1");
        categoryRepository.save(category1);
        Category category2 = new Category();
        category2.setName("Cat1");

        ResponseEntity<Category> response = categoryController.postAddCategory(category2);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CONFLICT,response.getStatusCode()),
                () -> assertNull(response.getBody())
        );
    }

    @Test
    void testAddCategorySecondUnique(){
        Category category1 = new Category();
        category1.setName("Cat1");
        categoryRepository.save(category1);
        Category category2 = new Category();
        category2.setName("Dog2");

        ResponseEntity<Category> response = categoryController.postAddCategory(category2);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    Category categoryFound =  response.getBody();
                    assertEquals("Dog2", categoryFound.getName());
                }
        );
    }
    // endregion

    // region Get Categories
    // TODO: test get categories
    @Test
    void testShowCategoriesNone(){
    ResponseEntity<Iterable<Category>> response = categoryController.getShowCategories();

    assertAll(
            () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
            () -> assertNotNull(response.getBody()),
            () -> {
                List<Category> categories = (ArrayList<Category>) response.getBody();
                assertTrue(categories.isEmpty());
            }
    );
    }
    @Test
    void testShowCategoriesOne(){
        Category category = new Category();
        category.setName("Cat1");
        categoryRepository.save(category);
        ResponseEntity<Iterable<Category>> response = categoryController.getShowCategories();

        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<Category> categories = (ArrayList<Category>) response.getBody();
                    assertEquals(1,categories.size());
                    assertEquals( "Cat1",categories.get(0).getName());
                }
        );
    }
    @Test
    void testShowCategoriesTwo(){
        Category category1 = new Category();
        category1.setName("Cat1");
        categoryRepository.save(category1);
        Category category2 = new Category();
        category2.setName("Dog2");
        categoryRepository.save(category2);


        ResponseEntity<Iterable<Category>> response = categoryController.getShowCategories();

        assertAll(
                () -> assertEquals(HttpStatus.OK,response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> {
                    List<Category> categories = (ArrayList<Category>) response.getBody();
                    assertEquals(2,categories.size());
                }
        );
    }

    // endregion


}
