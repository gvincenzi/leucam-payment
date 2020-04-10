package org.leucam.payment.client;

import org.leucam.payment.configuration.FeignClientConfiguration;
import org.leucam.payment.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "leucam-order-service/orders", configuration = FeignClientConfiguration.class)
public interface OrderResourceClient {
    @GetMapping("/{id}")
    OrderDTO findOrderById(@PathVariable Long id);
}
