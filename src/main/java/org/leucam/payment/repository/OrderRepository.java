package org.leucam.payment.repository;

import org.leucam.payment.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserCreditUserId(Long userId);
}
