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

@Table(name = "t_customer_wallet_withdraw")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TCustomerWalletWithdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private MCustomer customer;

    @ManyToOne
    @JoinColumn(name = "wallet_default_id", referencedColumnName = "id")
    private MWalletDefaultNominal walletDefault;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "bank_name", length = 50)
    private String bankName;

    @Column(name = "account_number", length = 50)
    private String accountNumber;

    @Column(name = "account_name", length = 200)
    private String accountName;

    @Column(name = "otp")
    private Integer otp;
}
