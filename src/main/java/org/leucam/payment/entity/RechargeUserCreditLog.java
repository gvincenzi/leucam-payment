package org.leucam.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "leucam_recharge_user_credit_log")
public class RechargeUserCreditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private BigDecimal oldCredit;
    @Column
    private BigDecimal newCredit;
    @Column
    private RechargeUserCreditType rechargeUserCreditType;
    @Column
    private LocalDateTime rechargeDateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private UserCredit userCredit;
}
