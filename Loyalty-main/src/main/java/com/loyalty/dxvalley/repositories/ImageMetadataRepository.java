package com.loyalty.dxvalley.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loyalty.dxvalley.models.ImageMetadata;

public interface ImageMetadataRepository  extends JpaRepository<ImageMetadata,Long>{
    
}
