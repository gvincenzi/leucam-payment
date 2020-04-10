package org.leucam.payment.repository;

import org.leucam.payment.entity.UserCredit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCreditRepository extends JpaRepository<UserCredit, Long> {
}
