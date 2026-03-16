package com.student_mgt_platform.fee_payment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeePaymentRequest {
    @NotNull(message = "The paymentAmount field is required")
    @Min(value = 1, message = "paymentAmount must be greater than 0")
    private BigDecimal paymentAmount;

    @NotBlank(message = "The studentNumber field is required")
    private String studentNumber;
}
