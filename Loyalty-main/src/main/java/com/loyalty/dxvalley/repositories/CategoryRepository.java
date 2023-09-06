package com.loyalty.dxvalley.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.loyalty.dxvalley.models.Category;


public interface CategoryRepository extends JpaRepository<Category,Long>{
   Category findCategoryByCategoryId(Long categoryId); 
}
