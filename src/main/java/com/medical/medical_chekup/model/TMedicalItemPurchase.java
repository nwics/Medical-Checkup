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

@Table(name = "t_medical_item_purchase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TMedicalItemPurchase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MCustomer customer;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MPaymentMethod paymentMethod;
}
