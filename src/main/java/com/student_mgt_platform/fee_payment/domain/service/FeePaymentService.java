package com.student_mgt_platform.fee_payment.domain.service;

import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import com.student_mgt_platform.fee_payment.dto.FeePaymentRequest;

import java.util.List;

public interface FeePaymentService {
    FeePaymentDto makeStudentFeePayment(FeePaymentRequest request);
    List<FeePaymentDto> getStudentPayments(String studentNumber);
}
