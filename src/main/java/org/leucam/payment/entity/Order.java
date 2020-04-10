package org.leucam.payment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "leucam_order")
public class Order {
    @Id
    private Long orderId;
    @Column
    private BigDecimal totalToPay;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private UserCredit userCredit;
}
