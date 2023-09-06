package com.loyalty.dxvalley.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.loyalty.dxvalley.models.Packagess;
import com.loyalty.dxvalley.repositories.ImageMetadataRepository;
import com.loyalty.dxvalley.services.PackagesService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/packages")
@RestController
@RequiredArgsConstructor
public class PackagessController {

    @Autowired
    private final PackagesService packagessService;
    @Autowired
    private ImageMetadataRepository imageMetadataRepository; // Autowire ImageMetadata repository

    @Value("${file.upload-dir}")
    private String uploadDir;

   @GetMapping("/getPackages")
List<Packagess> getPackagesses() {
    List<Packagess> packagesses = this.packagessService.getPackage();
    packagesses.forEach(p -> {
        File imageFile = new File(uploadDir, p.getLogoMetadata().getFileName());
        if (imageFile.exists() && imageFile.isFile()) {
            String encodedFileName = UriUtils.encode(p.getLogoMetadata().getFileName(), StandardCharsets.UTF_8);
            String imageUrl = "http://10.1.177.123:9000/api/images/" + encodedFileName;
            p.setLogo(imageUrl);
        }
    });

    return packagesses;
}

    @GetMapping("/{packageId}")
    Packagess getPackages(@PathVariable Long packageId) {
        Packagess packagess=  packagessService.getPackagesById(packageId);
        File imageFile = new File(uploadDir, packagess.getLogoMetadata().getFileName());
         if (imageFile.exists() && imageFile.isFile()) {
            String encodedFileName = UriUtils.encode(packagess.getLogoMetadata().getFileName(), StandardCharsets.UTF_8);
            String imageUrl = "http://10.1.177.123:9000/api/images/" + encodedFileName;
            packagess.setLogo(imageUrl);
        }
        return  packagess;
    }

    // @PostMapping("/addPackages")
    // public ResponseEntity<CreateResponse> addPackagess (@RequestBody Packagess
    // packagess) {
    // packagessService.addPackages(packagess);
    // CreateResponse response = new CreateResponse("Success","Packages created
    // successfully");
    // return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    @Transactional
    @PostMapping("/addPackages")
    public ResponseEntity<CreateResponse> addPackages(
            @RequestParam("packageName") String packageName,
            @RequestParam("packageDescription") String packageDescription,
            @RequestParam("isEnabeled") Boolean isEnabeled,
            @RequestParam("logo") String logo,
            @RequestParam("logoFile") MultipartFile logoFile) {
        Packagess packagess = new Packagess();
        packagess.setPackageName(packageName);
        packagess.setPackageDescription(packageDescription);
        packagess.setIsEnabeled(isEnabeled);
        packagess.setLogo(logo);

        // Save the package information to the database or perform other operations
        packagessService.addPackages(packagess);

        // Handle the logo file separately
        if (!logoFile.isEmpty()) {
            String fileName = logoFile.getOriginalFilename();
            File file = new File(uploadDir + File.separator + fileName);
            try {
                logoFile.transferTo(file);

                // Create ImageMetadata entity and save it to associate with Packagess entity
                ImageMetadata logoMetadata = new ImageMetadata();
                logoMetadata.setFileName(fileName);
                imageMetadataRepository.save(logoMetadata);

                packagess.setLogoMetadata(logoMetadata); // Associate with main entity
            } catch (IOException e) {
                // Handle file upload exception
            }
        }

        CreateResponse response = new CreateResponse("Success", "Package created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Transactional
    @PutMapping("/edit/{packageId}")
    public ResponseEntity<CreateResponse> editPackages(
            @PathVariable Long packageId,
            @RequestParam(required = false) String packageName,
            @RequestParam(required = false) String packageDescription,
            @RequestParam(required = false) Boolean isEnabeled,
            @RequestParam(required = false) String logo,
            @RequestParam(required = false) MultipartFile logoFile) {
        Packagess packagess = packagessService.getPackagesById(packageId);
    
        if (packageName != null) {
            packagess.setPackageName(packageName);
        }
        if (packageDescription != null) {
            packagess.setPackageDescription(packageDescription);
        }
        if (isEnabeled != null) {
            packagess.setIsEnabeled(isEnabeled);
        }
        if (logo != null) {
            packagess.setLogo(logo);
        }
    
        // Save the package information to the database or perform other operations
        packagessService.editPackages(packagess);
    
        // Handle the logo file separately if provided
        if (logoFile != null && !logoFile.isEmpty()) {
            String fileName = logoFile.getOriginalFilename();
            File file = new File(uploadDir + File.separator + fileName);
            try {
                logoFile.transferTo(file);
    
                // Create ImageMetadata entity and save it to associate with Packagess entity
                ImageMetadata logoMetadata = new ImageMetadata();
                logoMetadata.setFileName(fileName);
                imageMetadataRepository.save(logoMetadata);
    
                packagess.setLogoMetadata(logoMetadata); // Associate with main entity
            } catch (IOException e) {
                // Handle file upload exception
            }
        }
    
        CreateResponse response = new CreateResponse("Success", "Package updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    // @PutMapping("/edit/{packageId}")
    // Packagess editPackagess(@RequestBody Packagess tempPackagess, @PathVariable Long packageId) {
    //     Packagess packagess = this.packagessService.getPackagesById(packageId);
    //     packagess.setPackageName(tempPackagess.getPackageName());
    //     packagess.setPackageDescription(tempPackagess.getPackageDescription());
    //     packagess.setLogo(tempPackagess.getLogo());
    //     packagess.setIsEnabeled(tempPackagess.getIsEnabeled());
    //     return packagessService.editPackages(packagess);

    // }

}
