package com.loyalty.dxvalley.services;

import java.util.List;

import com.loyalty.dxvalley.models.Category;


public interface CategoryService {
    List<Category> getCategories ();
    Category getCategoryById( Long categoryId);
    Category addCategory(Category category);
    Category editCategory(Category category);
}
