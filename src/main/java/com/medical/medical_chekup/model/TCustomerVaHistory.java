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

@Table(name = "t_customer_va_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TCustomerVaHistory extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_va_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TCustomerVa customerVa;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "expired_on")
    private LocalDateTime expiredOn;
}
