package com.medical.medical_chekup.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "m_customer")
public class MCustomer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "biodata_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MBiodata mBiodata;

    @Column(name = "dob")
    private LocalDateTime dob;

    @Column(name = "gender")
    private String gender;

    @ManyToOne
    @JoinColumn(name = "blood_group_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MBloodGroup mBloodGroup;

    @Column(name = "rhesus_type")
    private String rhesusType;

    @Column(name = "height", nullable = true)
    private BigDecimal height;

    @Column(name = "weight", nullable = true)
    private BigDecimal weight;
}
