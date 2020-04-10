package org.leucam.payment;

import org.leucam.payment.binding.MQBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(MQBinding.class)
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class LeucamPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeucamPaymentApplication.class, args);
    }

}
