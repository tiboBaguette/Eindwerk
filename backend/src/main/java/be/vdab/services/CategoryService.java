package be.vdab.services;

import be.vdab.domain.Category;

public interface CategoryService {
    boolean addCategory(Category category);
    Iterable<Category> getCategories();
}
