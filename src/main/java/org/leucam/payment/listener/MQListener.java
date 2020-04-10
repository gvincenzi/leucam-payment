package org.leucam.payment.listener;

import org.leucam.payment.binding.MQBinding;
import org.leucam.payment.dto.OrderDTO;
import org.leucam.payment.dto.UserDTO;
import org.leucam.payment.service.InternalPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.transaction.annotation.Transactional;

@EnableBinding(MQBinding.class)
public class MQListener {
    @Autowired
    private InternalPaymentService internalPaymentService;

    @StreamListener(target = MQBinding.USER_REGISTRATION)
    public void processUserRegistration(UserDTO msg) {
        internalPaymentService.processUserRegistration(msg);
    }

    @StreamListener(target = MQBinding.USER_ORDER)
    public void processUserOrder(OrderDTO msg) {
        internalPaymentService.processUserOrder(msg);
    }

    @Transactional
    @StreamListener(target = MQBinding.USER_CANCELLATION)
    public void processUserCancellation(UserDTO msg) {
        internalPaymentService.processUserCancellation(msg);
    }

    @StreamListener(target = MQBinding.ORDER_CANCELLATION)
    public void processOrderCancellation(OrderDTO msg) {
        internalPaymentService.processOrderCancellation(msg);
    }
}
