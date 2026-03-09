package com.student_mgt_platform.fee_payment.domain.service;

import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import com.student_mgt_platform.fee_payment.dto.FeePaymentRequest;

public interface FeePaymentService {
    FeePaymentDto makeStudentFeePayment(FeePaymentRequest request);
}
