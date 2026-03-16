package com.student_mgt_platform.fee_payment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentAccountRequestDto {
    @NotBlank(message = "studentNumber is required")
    private String studentNumber;

    @NotBlank(message = "studentName is required")
    private String studentName;

    @NotBlank(message = "institutionFee is required")
    private String institutionFeeId;
}
