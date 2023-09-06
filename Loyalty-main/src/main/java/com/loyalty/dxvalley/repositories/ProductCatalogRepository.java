package com.loyalty.dxvalley.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.loyalty.dxvalley.models.ProductCataloge;

public interface ProductCatalogRepository extends JpaRepository<ProductCataloge,Long>{
    ProductCataloge findProductCatalogeByProductId(Long productCatalogeId); 
}
