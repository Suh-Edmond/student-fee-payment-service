package com.student_mgt_platform.fee_payment.dto;

import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeePaymentRequest {
    @NotNull(message = "The paymentAmount field is required")
    @Min(value = 1, message = "paymentAmount must be greater than 0")
    private BigDecimal paymentAmount;

    @NotNull(message = "The studentNumber field is required")
    private String studentNumber;

    @NotNull(message = "The institutionalFeeCategory field is required")
    private InstitutionalFeeCategory institutionalFeeCategory;
}
