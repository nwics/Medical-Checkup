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

@Table(name = "t_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MCustomer customer;

    @Column(name = "token", length = 50)
    private String token;

    @Column(name = "exired_on")
    private LocalDateTime expiredOn;

    @Column(name = "is_expired")
    private boolean isExpired;

    @Column(name = "used_for", length = 20)
    private String usedFor;

}
