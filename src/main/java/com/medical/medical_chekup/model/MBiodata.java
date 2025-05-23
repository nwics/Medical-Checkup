package com.medical.medical_chekup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "m_biodata")
public class MBiodata extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fullname", nullable = true, length = 255)
    private String fullName;

    @Column(name = "mobile_phone", nullable = true, length = 15)
    private String mobilePhone;

    @Lob
    @Column(name = "image", nullable = true)
    private byte[] image;

    @Column(name = "image_path", nullable = true, length = 255)
    private String imagePath;
}