package com.student_mgt_platform.fee_payment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDto {
    private LocalDateTime created;
    private LocalDateTime updated;
}
