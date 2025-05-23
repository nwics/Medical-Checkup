package com.medical.medical_chekup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_medical_item")
public class MMedicalItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "medical_item_category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MMedicalItemCategory mMedicalItemCategory;

    @Column(columnDefinition = "TEXT", name = "composition", nullable = true)
    private String composition;

    @Column(name = "indication", columnDefinition = "TEXT")
    private String indication;

    @Column(name = "dosage", columnDefinition = "TEXT")
    private String dosage;

    @Column(name = "directions", columnDefinition = "TEXT")
    private String directions;

    @Column(name = "contraindication", columnDefinition = "TEXT")
    private String contraIndication;

    @Column(name = "caution", columnDefinition = "TEXT")
    private String caution;

    @ManyToOne
    @JoinColumn(name = "medical_item_segmentation_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MMedicalItemSegmentation mMedicalItemSegmentation;

    @Column(name = "manufacturer", length = 100, nullable = true)
    private String manufacturer;

    @Column(name = "packaging", length = 50)
    private String packaging;

    @Column(name = "price_max")
    private Long priceMax;

    @Column(name = "price_min")
    private Long priceMin;

    @Column(name = "image", columnDefinition = "BYTEA")
    private byte[] image;

    @Column(name = "image_path", length = 100)
    private String image_path;
}
