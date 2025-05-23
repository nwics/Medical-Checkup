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
@Table(name = "m_medical_facility")
public class MMedicalFacility extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "medical_facility_category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MMedicalFacilityCategory mMedicalFacilityCategory;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MLocation mLocation;

    @Column(columnDefinition = "Text", name = "full_address", nullable = true)
    private String fullAddress;

    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @Column(name = "phone_code", nullable = true, length = 10)
    private String phoneCode;

    @Column(name = "phone", nullable = true, length = 15)
    private String phone;

    @Column(name = "fax", nullable = true, length = 15)
    private String fax;
}
