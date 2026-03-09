package com.student_mgt_platform.fee_payment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeePaymentRequest {
    @NotNull
    @Min(1)
    private BigDecimal paymentAmount;

    @NotNull
    private String studentNumber;
}
