package com.loyalty.dxvalley.services;

import java.util.List;
import com.loyalty.dxvalley.models.ProductCataloge;

public interface ProductCatalogService {
    List<ProductCataloge> getChallengeCataloges();
    ProductCataloge getProductCatalogeById( Long productCatalogeId);
    ProductCataloge addProductCataloge(ProductCataloge productCataloge);
    ProductCataloge editProductCataloge(ProductCataloge productCataloge);
}
