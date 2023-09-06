package com.loyalty.dxvalley.models;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@RequiredArgsConstructor
@Entity
public class ProductCataloge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String productName;
    private String productLogo;
    private String description;
    private String playstoreLink;
     @JsonFormat(pattern="yyyy-MM-dd",shape = Shape.STRING)
    @Column(name="createdAt")
    private String  createdAt;
     @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_metadata_id")
    private ImageMetadata logoMetadata;
}
