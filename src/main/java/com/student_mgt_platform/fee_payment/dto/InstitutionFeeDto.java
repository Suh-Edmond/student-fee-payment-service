package com.student_mgt_platform.fee_payment.dto;

import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@Data
public class InstitutionFeeDto extends BaseDto {
    private String id;
    private String name;
    private BigDecimal amountPayable;
    private InstitutionalFeeCategory category;
}
