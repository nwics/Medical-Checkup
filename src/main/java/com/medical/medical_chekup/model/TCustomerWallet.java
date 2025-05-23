package com.medical.medical_chekup.model;

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

@Table(name = "t_customer_wallet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TCustomerWallet extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MCustomer customer;

    @Column(name = "pin", length = 6)
    private String pin;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "barcode", length = 50)
    private String barcode;

    @Column(name = "points")
    private Double points;

    @Column(name = "pin_attempt")
    private Integer pinAttempt;

    @Column(name = "block_ends")
    private LocalDateTime blockEnds;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "boolean default false")
    private Boolean isBlocked = false;
}
