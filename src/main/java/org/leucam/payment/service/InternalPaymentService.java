package org.leucam.payment.service;

import org.leucam.payment.dto.OrderDTO;
import org.leucam.payment.dto.UserDTO;
import org.leucam.payment.entity.Order;
import org.leucam.payment.entity.RechargeUserCreditType;
import org.leucam.payment.entity.UserCredit;

import java.math.BigDecimal;

public interface InternalPaymentService {
    UserCredit userCreditUpdateCredit(UserDTO user, BigDecimal credit, RechargeUserCreditType type);
    void processUserRegistration(UserDTO msg);
    Order processUserOrder(OrderDTO msg);
    void processUserCancellation(UserDTO msg);
    void processOrderCancellation(OrderDTO msg);
}
