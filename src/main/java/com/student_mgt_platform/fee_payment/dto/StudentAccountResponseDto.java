package com.student_mgt_platform.fee_payment.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentAccountResponseDto extends BaseDto {
    private String id;
    private String studentName;
    private String studentNumber;
    private InstitutionFeeDto institutionFeeDto;
}
