package com.loyalty.dxvalley.services;

import java.util.List;
import com.loyalty.dxvalley.models.Packagess;

public interface PackagesService {
    Packagess addPackages(Packagess packagess);

    Packagess editPackages(Packagess packagess);

    List<Packagess> getPackage();
    Packagess getPackagesById(Long packagessId);
}
