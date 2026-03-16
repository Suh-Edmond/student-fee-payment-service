package com.student_mgt_platform.fee_payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentAccountRequestDto {
    @NotBlank(message = "studentNumber is required")
    @Size(min = 5, max = 10, message = "studentNumber must have at least 5 characters and should not be more than 10 characters")
    private String studentNumber;

    @NotBlank(message = "studentName is required")
    @Size(min = 3, max = 255, message = "studentName must have at least 3 characters and should not be more than 255 characters")
    private String studentName;

    @NotBlank(message = "institutionFee is required")
    private String institutionFeeId;
}
