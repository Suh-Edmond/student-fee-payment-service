package com.student_mgt_platform.fee_payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.student_mgt_platform.fee_payment.domain.model")
@EnableJpaRepositories(basePackages = "com.student_mgt_platform.fee_payment.domain.repository")
@SpringBootApplication
public class FeePaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeePaymentApplication.class, args);
	}

}
