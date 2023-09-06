package com.loyalty.dxvalley.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor

public class Packagess {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long packageId;
    private String packageName;
    private String packageDescription;
    private Boolean isEnabeled;
    private String logo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_metadata_id")
    private ImageMetadata logoMetadata;
    

}
