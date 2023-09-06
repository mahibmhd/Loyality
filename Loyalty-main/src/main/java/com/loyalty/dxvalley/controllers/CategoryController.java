package com.loyalty.dxvalley.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.loyalty.dxvalley.models.Category;
import com.loyalty.dxvalley.models.CreateResponse;
import com.loyalty.dxvalley.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {
     @Autowired
    private final CategoryService categoryService;
    @PostMapping("/addCategory")
    public ResponseEntity<CreateResponse> addCategory (@RequestBody Category category) {
        categoryService.addCategory(category);
        CreateResponse response = new CreateResponse("Success","Category created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    } 
      @PutMapping("/edit/{categoryId}")
      Category editCategory(@RequestBody Category tempCategory, @PathVariable Long categoryId) {
        Category category = this.categoryService.getCategoryById(categoryId);
        category.setCategoryName(tempCategory.getCategoryName());
        category.setDescription(tempCategory.getDescription());
        return categoryService.editCategory(category);
 }

  @GetMapping("/getAll")
  List<Category> getAll() {
   return categoryService.getCategories();
  }
}
