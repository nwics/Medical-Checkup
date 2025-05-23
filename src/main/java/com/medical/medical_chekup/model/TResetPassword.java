package com.medical.medical_chekup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "t_reset_password")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TResetPassword extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "old_password", length = 255)
    private String oldPassword;

    @Column(name = "new_password", length = 255)
    private String newPassword;

    @Column(name = "reset_for", length = 20)
    private String resetFor;
}
