package com.loyalty.dxvalley.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.loyalty.dxvalley.models.CreateResponse;
import com.loyalty.dxvalley.models.ImageMetadata;
import com.loyalty.dxvalley.models.ProductCataloge;
import com.loyalty.dxvalley.repositories.ImageMetadataRepository;
import com.loyalty.dxvalley.services.ProductCatalogService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/productCatalog")
@RestController
@RequiredArgsConstructor
public class ProductCatalogeController {
  @Value("${file.upload-dir}")
  private String uploadDir;
  @Autowired
  private final ProductCatalogService productCatalogService;
  @Autowired
  private ImageMetadataRepository imageMetadataRepository;

  // @PostMapping("/addProductCatalog")
  // public ResponseEntity<CreateResponse> addProductCatalog (@RequestBody
  // ProductCataloge productCataloge) {
  // productCatalogService.addProductCataloge(productCataloge);
  // CreateResponse response = new CreateResponse("Success","ProductCatelog
  // created successfully");
  // return new ResponseEntity<>(response, HttpStatus.OK);
  // }
  @GetMapping("/{productCatalogId}")
  ProductCataloge getProductCatalogs(@PathVariable Long productCatalogId) {
    ProductCataloge productCataloge = productCatalogService.getProductCatalogeById(productCatalogId);
    File imageFile = new File(uploadDir, productCataloge.getLogoMetadata().getFileName());
    if (imageFile.exists() && imageFile.isFile()) {
      String encodedFileName = UriUtils.encode(productCataloge.getLogoMetadata().getFileName(), StandardCharsets.UTF_8);
      String imageUrl = "http://10.1.177.123:9000/api/images/" + encodedFileName;
      productCataloge.setProductLogo(imageUrl);
    }
    return productCataloge;
  }

  @Transactional
  @PostMapping("/addProductCataloge")
  public ResponseEntity<CreateResponse> addProductCataloge(
      @RequestParam("productName") String productName,
      @RequestParam("productLogo") String productLogo,
      @RequestParam("description") String description,
      @RequestParam("createdAt") String createdAt,
      @RequestParam("playstoreLink") String playstoreLink,
      @RequestParam("logoFile") MultipartFile logoFile) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate = new Date();
    ProductCataloge productCataloge = new ProductCataloge();
    productCataloge.setProductName(productName);
    productCataloge.setProductLogo(productLogo);
    productCataloge.setDescription(description);
    productCataloge.setPlaystoreLink(playstoreLink);
    productCataloge.setCreatedAt(dateFormat.format(currentDate));

    // Save the product catalog information to the database
    productCatalogService.addProductCataloge(productCataloge);

    // Handle the logo file separately
    if (!logoFile.isEmpty()) {
      String fileName = logoFile.getOriginalFilename();
      File file = new File(uploadDir + File.separator + fileName);
      try {
        logoFile.transferTo(file);

        // Create ImageMetadata entity and save it to associate with ProductCataloge's
        // logoMetadata
        ImageMetadata logoMetadata = new ImageMetadata();
        logoMetadata.setFileName(fileName);
        imageMetadataRepository.save(logoMetadata);

        productCataloge.setLogoMetadata(logoMetadata); // Associate with main entity
      } catch (IOException e) {
        // Handle file upload exception
      }
    }

    CreateResponse response = new CreateResponse("Success", "Product catalog added successfully");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  // @PutMapping("/edit/{productCatalogeId}")
  // ProductCataloge editProductCatalog(@RequestBody ProductCataloge
  // tempProductCatalog,
  // @PathVariable Long productCatalogeId) {
  // ProductCataloge productCatalog =
  // this.productCatalogService.getProductCatalogeById(productCatalogeId);
  // productCatalog.setProductName(tempProductCatalog.getProductName());
  // productCatalog.setCreatedAt(tempProductCatalog.getCreatedAt());
  // productCatalog.setProductLogo(tempProductCatalog.getProductLogo());
  // productCatalog.setDescription(tempProductCatalog.getDescription());
  // return productCatalogService.editProductCataloge(productCatalog);
  // }
  @Transactional
  @PutMapping("/edit/{productCatalogeId}")
  public ResponseEntity<CreateResponse> editProductCataloge(
      @PathVariable Long productCatalogeId,
      @RequestParam(required = false) String productName,
      @RequestParam(required = false) String productLogo,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) String createdAt,
      @RequestParam(required = false) String playstoreLink,
      @RequestParam(required = false) MultipartFile logoFile) {
    ProductCataloge productCataloge = productCatalogService.getProductCatalogeById(productCatalogeId);

    if (productName != null) {
      productCataloge.setProductName(productName);
    }
    if (productLogo != null) {
      productCataloge.setProductLogo(productLogo);
    }
    if (description != null) {
      productCataloge.setDescription(description);
    }
    if (createdAt != null) {
      productCataloge.setCreatedAt(createdAt);
    }
    if (playstoreLink != null) {
      productCataloge.setPlaystoreLink(playstoreLink);
    }
    // Save the product catalog information to the database or perform other
    // operations
    productCatalogService.editProductCataloge(productCataloge);

    // Handle the logo file separately if provided
    if (logoFile != null && !logoFile.isEmpty()) {
      String fileName = logoFile.getOriginalFilename();
      File file = new File(uploadDir + File.separator + fileName);
      try {
        logoFile.transferTo(file);

        // Create ImageMetadata entity and save it to associate with ProductCataloge
        // entity
        ImageMetadata logoMetadata = new ImageMetadata();
        logoMetadata.setFileName(fileName);
        imageMetadataRepository.save(logoMetadata);

        productCataloge.setLogoMetadata(logoMetadata); // Associate with main entity
      } catch (IOException e) {
        // Handle file upload exception
      }
    }

    CreateResponse response = new CreateResponse("Success", "Product catalog updated successfully");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  // @GetMapping("/getAll")
  // List<ProductCataloge> getAll() {
  // return productCatalogService.getChallengeCataloges();
  // }
  @GetMapping("/getProductCatalogs")
  List<ProductCataloge> getProductCatalogs() {
    List<ProductCataloge> productCataloges = this.productCatalogService.getChallengeCataloges();
    productCataloges.forEach(p -> {
      File imageFile = new File(uploadDir, p.getLogoMetadata().getFileName());
      if (imageFile.exists() && imageFile.isFile()) {
        String encodedFileName = UriUtils.encode(p.getLogoMetadata().getFileName(), StandardCharsets.UTF_8);
        String imageUrl = "http://10.1.177.123:9000/api/images/" + encodedFileName;
        p.setProductLogo(imageUrl);
      }
    });

    return productCataloges;
  }
}
