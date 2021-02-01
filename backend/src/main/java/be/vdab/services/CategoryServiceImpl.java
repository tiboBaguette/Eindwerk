package be.vdab.services;

import be.vdab.domain.Category;
import be.vdab.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public boolean addCategory(Category category) {
        if(category == null){
            return false;
        }
        if(category.getName().equals("")){
            return false;
        }
        if(!categoryRepository.findCategoriesByName(category.getName()).isEmpty()){
            return false;
        }
            categoryRepository.save(category);
        return true;
    }

    @Override
    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
