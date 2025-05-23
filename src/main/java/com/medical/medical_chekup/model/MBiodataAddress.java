package com.medical.medical_chekup.model;

import org.attoparser.dom.Text;

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
@Table(name = "m_biodata_address")
public class MBiodataAddress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "biodata_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private MBiodata mBiodata;

    @Column(name = "label", nullable = true, length = 100)
    private String label;

    @Column(name = "recipient", nullable = true, length = 100)
    private String recipient;

    @Column(name = "recipient_phone_number", nullable = true, length = 15)
    private String recipientPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private MLocation location;

    @Column(name = "postal_code", nullable = true, length = 10)
    private String postalCode;

    @Column(name = "address", nullable = true)
    private Text address;
}
