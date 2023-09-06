package com.loyalty.dxvalley.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyalty.dxvalley.models.ProductCataloge;
import com.loyalty.dxvalley.repositories.ProductCatalogRepository;
import com.loyalty.dxvalley.services.ProductCatalogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCatelogeServiceImpl implements ProductCatalogService{
     @Autowired
    private final ProductCatalogRepository productCatalogRepository;
    @Override
    public List<ProductCataloge> getChallengeCataloges() {
                 return  productCatalogRepository.findAll();    }

    @Override
    public ProductCataloge addProductCataloge(ProductCataloge productCataloge) {
               return productCatalogRepository.save(productCataloge); }

    @Override
    public ProductCataloge editProductCataloge(ProductCataloge productCataloge) {
              return productCatalogRepository.save(productCataloge);  }

    @Override
    public ProductCataloge getProductCatalogeById(Long productCatalogeId) {
        return productCatalogRepository.findProductCatalogeByProductId(productCatalogeId);
    }
    
}
