package com.student_mgt_platform.fee_payment.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class FeePaymentDto extends BaseDto {
    private UUID id;
    private BigDecimal previousBalance;
    private BigDecimal paymentAmount;
    private int incentiveRate;
    private BigDecimal incentiveAmount;
    private BigDecimal newBalance;
    private LocalDate nextDueDate;
    private String studentNumber;
}
