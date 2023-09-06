package com.loyalty.dxvalley.serviceImpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyalty.dxvalley.models.Category;
import com.loyalty.dxvalley.repositories.CategoryRepository;
import com.loyalty.dxvalley.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
    @Override
    public Category addCategory(Category category) {
       return categoryRepository.save(category);
    }
    @Override
    public Category editCategory(Category category) {
       return categoryRepository.save(category);
    }
    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findCategoryByCategoryId(categoryId);
    }
}
