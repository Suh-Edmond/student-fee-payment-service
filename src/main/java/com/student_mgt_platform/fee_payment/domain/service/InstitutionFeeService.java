package com.student_mgt_platform.fee_payment.domain.service;

import com.student_mgt_platform.fee_payment.dto.InstitutionFeeDto;

import java.util.List;

public interface InstitutionFeeService {
    List<InstitutionFeeDto> getInstitutionFees();
}
