package com.medical.medical_chekup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@Table(name = "m_biodata_attachment")
public class MBiodataAttachment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "biodata_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MBiodata biodata;

    @Column(name = "file_name", length = 50)
    private String fileName;

    @Column(name = "file_path", length = 100)
    private String filePath;

    @Column(name = "file_size")
    private Integer fileSize;

    @Lob
    @Column(name = "file")
    private byte[] file;
}
