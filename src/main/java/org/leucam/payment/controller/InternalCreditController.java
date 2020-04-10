package org.leucam.payment.controller;

import org.leucam.payment.dto.UserDTO;
import org.leucam.payment.entity.Order;
import org.leucam.payment.entity.RechargeUserCreditLog;
import org.leucam.payment.entity.RechargeUserCreditType;
import org.leucam.payment.entity.UserCredit;
import org.leucam.payment.repository.OrderRepository;
import org.leucam.payment.repository.RechargeUserCreditLogRepository;
import org.leucam.payment.repository.UserCreditRepository;
import org.leucam.payment.service.InternalPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/internal-credit/")
public class InternalCreditController {
    @Autowired
    private UserCreditRepository userCreditRepository;

    @Autowired
    private RechargeUserCreditLogRepository rechargeUserCreditLogRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InternalPaymentService internalPaymentService;

    @Value("${message.userNotFound}")
    public String userNotFound;

    @Value("${message.insufficientCredit}")
    public String insufficientCredit;

    @Value("${message.alreadyPaid}")
    public String alreadyPaid;

    @Value("${message.paymentApproved}")
    public String paymentApproved;

    @Value("${message.orderNotExist}")
    public String orderNotExist;

    @Transactional
    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> removeCredit(@PathVariable("userId") Long userId) {
        Optional<UserCredit> userCredit = userCreditRepository.findById(userId);
        if(userCredit.isPresent()){
            rechargeUserCreditLogRepository.deleteAllByUserCredit(userCredit.get());
            userCreditRepository.deleteById(userId);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(userNotFound,userId), null);
        }
    }

    @PutMapping("/{credit}")
    public ResponseEntity<UserCredit> addCredit(@RequestBody UserDTO user, @PathVariable("credit") BigDecimal credit) {
        Optional<UserCredit> userCreditCurrent = userCreditRepository.findById(user.getId());
        if(userCreditCurrent.isPresent()){
            credit = credit.add(userCreditCurrent.get().getCredit());
        }
        UserCredit userCreditInstance = internalPaymentService.userCreditUpdateCredit(user,credit,RechargeUserCreditType.TELEGRAM);
        return new ResponseEntity<>(userCreditInstance, HttpStatus.OK);
    }

    @PostMapping("/{credit}")
    public ResponseEntity<UserCredit> newCredit(@RequestBody UserDTO user, @PathVariable("credit") BigDecimal credit) {
        UserCredit userCreditInstance = internalPaymentService.userCreditUpdateCredit(user,credit,RechargeUserCreditType.WEB_ADMIN);
        return new ResponseEntity<>(userCreditInstance, HttpStatus.OK);
    }

    @GetMapping("/{userId}/log")
    public ResponseEntity<List<RechargeUserCreditLog>> findRechargeUserCreditLogByUserId(@PathVariable("userId") Long userId) {
        Optional<UserCredit> userCredit = userCreditRepository.findById(userId);
        List<RechargeUserCreditLog> logs = new ArrayList<>();
        if (userCredit.isPresent()) {
            logs = rechargeUserCreditLogRepository.findAllByUserCreditOrderByRechargeDateTimeDesc(userCredit.get());
        }

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserCredit> findCreditByUser(@PathVariable("userId") Long userId) {
        Optional<UserCredit> userCredit = userCreditRepository.findById(userId);
        UserCredit userCreditInstance = new UserCredit(userId, null, null,null, null, BigDecimal.ZERO);
        if (userCredit.isPresent()) {
            userCreditInstance = userCredit.get();
        }
        return new ResponseEntity<>(userCreditInstance, HttpStatus.OK);
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<List<Order>> findOrdersByUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(orderRepository.findByUserCreditUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/totalUserCredit")
    public ResponseEntity<BigDecimal> totalUserCredit() {
        BigDecimal total = BigDecimal.ZERO;
        for(UserCredit userCredit : userCreditRepository.findAll()){
            total = total.add(userCredit.getCredit());
        }
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/order/{orderId}")
    public ResponseEntity<Boolean> findOrdersByUser(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isPresent()){
            orderRepository.deleteById(orderId);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserCredit>> findAllCredit() {
        List<UserCredit> userCredit = userCreditRepository.findAll();
        return new ResponseEntity<>(userCredit,HttpStatus.OK);
    }
}
