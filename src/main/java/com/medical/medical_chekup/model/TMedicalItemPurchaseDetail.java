package com.medical.medical_chekup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "t_medical_item_purchase_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TMedicalItemPurchaseDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medical_item_purchase_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TMedicalItemPurchase medicalItemPurchaseId;

    @ManyToOne
    @JoinColumn(name = "medical_item_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MMedicalItem medicalItem;

    @Column(name = "qty")
    private Integer qty;

    @ManyToOne
    @JoinColumn(name = "medical_facility_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MMedicalFacility medicalFacility;

    @ManyToOne
    @JoinColumn(name = "courier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MCourier courier;

    @Column(name = "sub_total")
    private Double sub_total;
}
