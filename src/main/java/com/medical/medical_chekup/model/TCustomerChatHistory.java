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

@Table(name = "t_customer_chat_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TCustomerChatHistory extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_chat_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TCustomerChat customerChat;

    @Column(name = "chat_content", columnDefinition = "TEXT")
    private String chatContent;
}
