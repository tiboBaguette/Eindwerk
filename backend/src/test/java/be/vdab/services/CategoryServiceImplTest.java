package be.vdab.services;

import be.vdab.BackendApplication;
import be.vdab.domain.Category;
import be.vdab.repositories.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;
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

    // region test add
    @Test
    void testAddCategoryNull() {
        assertFalse(categoryService.addCategory(null));
        assertEquals(0,categoryRepository.findAll().size());
    }

    @Test
    void testAddCategoryEmpty() {
        Category category = new Category();
        category.setName("");
        assertAll(
                () -> assertFalse(categoryService.addCategory(category)),
                () -> assertEquals(0,categoryRepository.findAll().size())
        );
    }

    @Test
    void testAddCategoryValid() {
        Category category = new Category();
        category.setName("testCategory");
        assertAll(
                () -> assertTrue(categoryService.addCategory(category)),
                () -> assertEquals(1,categoryRepository.findAll().size())
        );
    }

    @Test
    void testAddCategorySecondIdentical(){
        Category category1 = new Category();
        category1.setName("testCategory");
        assertTrue(categoryService.addCategory(category1));
        Category category2 = new Category();
        category2.setName("testCategory");
        assertFalse(categoryService.addCategory(category2));
        assertAll(
                () -> assertEquals(1,categoryRepository.findAll().size())
        );
    }
    @Test
    void testAddCategorySecondUnique(){
        Category category1 = new Category();
        category1.setName("Cat1");
        assertTrue(categoryService.addCategory(category1));
        Category category2 = new Category();
        category2.setName("Dog2");
        assertTrue(categoryService.addCategory(category2));
        assertAll(
                () -> assertEquals(2,categoryRepository.findAll().size())
        );
    }
    // endregion

    // region test get categories

    @Test
    void testGetNoCategoriesAvailable(){
        List<Category> categories = (List<Category>) categoryService.getCategories();
        assertAll(
                () -> assertNotNull(categories),
                () -> assertEquals(0,categories.size())
        );
    }

    @Test
    void testGetOneCategoryAvailable(){
        Category category = new Category();
        category.setName("newCategory");
        categoryRepository.save(category);

        List<Category> categories = (List<Category>) categoryService.getCategories();
        assertAll(
                () -> assertNotNull(categories),
                () -> assertEquals(1,categories.size()),
                () -> assertNotNull(categories.get(0)),
                () -> assertEquals("newCategory",categories.get(0).getName())
        );
    }

    @Test
    void testGetTwoCategoriesAvailable(){
        Category category1 = new Category();
        category1.setName("Cat1");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Dog2");
        categoryRepository.save(category2);

        List<Category> categories = (List<Category>) categoryService.getCategories();
        assertAll(
                () -> assertNotNull(categories),
                () -> assertEquals(2,categories.size()),
                () -> assertNotNull(categories.get(0)),
                () -> assertNotNull(categories.get(1))
        );
    }
    // endregion

}
