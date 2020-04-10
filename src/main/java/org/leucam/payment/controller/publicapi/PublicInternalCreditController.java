package org.leucam.payment.controller.publicapi;

import org.leucam.payment.client.OrderResourceClient;
import org.leucam.payment.dto.OrderDTO;
import org.leucam.payment.entity.*;
import org.leucam.payment.repository.OrderRepository;
import org.leucam.payment.repository.PaymentRepository;
import org.leucam.payment.repository.UserCreditRepository;
import org.leucam.payment.service.InternalPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(value = "/public/internal-credit/")
public class PublicInternalCreditController {
    @Autowired
    private UserCreditRepository userCreditRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MessageChannel orderPaymentChannel;

    @Autowired
    private InternalPaymentService internalPaymentService;

    @Autowired
    private OrderResourceClient orderResourceClient;

    @Value("${message.insufficientCredit}")
    public String insufficientCredit;

    @Value("${message.alreadyPaid}")
    public String alreadyPaid;

    @Value("${message.paymentApproved}")
    public String paymentApproved;

    @Value("${message.orderNotExist}")
    public String orderNotExist;

    @GetMapping(value = "/make/payment/{orderId}")
    public ResponseEntity<String> makePayment(@PathVariable("orderId") Long orderId) {
        Optional<Order> orderToPay = orderRepository.findById(orderId);
        Order order = null;
        if (!orderToPay.isPresent()) {
            OrderDTO orderToPayRemote = orderResourceClient.findOrderById(orderId);
            if(orderToPayRemote != null){
                order = internalPaymentService.processUserOrder(orderToPayRemote);
            } else {
                return new ResponseEntity<>(String.format(orderNotExist, orderId), HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            order = orderToPay.get();
        }

        UserCredit userCredit = order.getUserCredit();
        if (userCredit.getCredit().compareTo(order.getTotalToPay()) < 0) {
            return new ResponseEntity<>(String.format(insufficientCredit, order.getTotalToPay(), userCredit.getUserId(), userCredit.getCredit()), HttpStatus.NOT_ACCEPTABLE);
        } else {
            Optional<Payment> paymentPeristed = paymentRepository.findByOrderId(order.getOrderId());
            if(paymentPeristed.isPresent()){

                return new ResponseEntity<>(String.format(alreadyPaid,order.getOrderId()), HttpStatus.NOT_ACCEPTABLE);
            } else {
                Payment payment = new Payment();
                payment.setPaymentId("INTERNAL_PAYID_" + System.currentTimeMillis());
                payment.setPaymentDateTime(LocalDateTime.now());
                payment.setOrderId(order.getOrderId());
                payment.setPaymentType(PaymentType.INTERNAL_CREDIT);
                paymentRepository.save(payment);
                BigDecimal newCredit = userCredit.getCredit().subtract(order.getTotalToPay());
                userCredit.setCredit(newCredit);
                userCreditRepository.save(userCredit);

                Message<Payment> msg = MessageBuilder.withPayload(payment).build();
                orderPaymentChannel.send(msg);
                return new ResponseEntity<>(paymentApproved, HttpStatus.OK);
            }
        }
    }
}
