package com.loyalty.dxvalley.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyalty.dxvalley.models.Packagess;
import com.loyalty.dxvalley.repositories.PackagessRepository;
import com.loyalty.dxvalley.services.PackagesService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service

public class PackagessServiceImpl implements PackagesService  {
@Autowired
private final PackagessRepository packagesRepository;
    @Override
    public List<Packagess> getPackage() {
        List<Packagess> packages = packagesRepository.findAll();

        for (Packagess packagess : packages) {
            if (packagess.getLogoMetadata() != null) {
                String imageName = packagess.getLogoMetadata().getFileName();
                String imageUrl = "http://10.1.177.123:9000/" + imageName; // Adjust URL accordingly
                packagess.setLogo(imageUrl);
            }
        }

        return packages;
      
    }
    @Override
    public Packagess addPackages(Packagess packagess) {
        return packagesRepository.save(packagess);
    }
    @Override
    public Packagess editPackages(Packagess packagess) {
       return packagesRepository.save(packagess);
    }
    @Override
    public Packagess getPackagesById(Long packagessId) {
        return packagesRepository.findByPackageId(packagessId);
    }
   
    
}
