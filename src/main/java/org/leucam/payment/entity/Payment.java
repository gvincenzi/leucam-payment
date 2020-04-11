package org.leucam.payment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "leucam_payment")
public class Payment {
    @Id
    private String paymentId;
    @Column
    private LocalDateTime paymentDateTime;
    @Column
    private Long orderId;
    @Column
    private PaymentType paymentType;
    @Column
    private BigDecimal amount;
}
