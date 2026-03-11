package com.student_mgt_platform.fee_payment.domain.controller;

import com.student_mgt_platform.fee_payment.domain.service.FeePaymentService;
import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import com.student_mgt_platform.fee_payment.dto.FeePaymentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class FeePaymentController {
    private final FeePaymentService feePaymentService;

    @PostMapping("one-time-fee-payment")
    public ResponseEntity<FeePaymentDto> oneTimeFeePayment(@Valid @RequestBody final FeePaymentRequest request) {
        return ResponseEntity.ok(feePaymentService.makeStudentFeePayment(request));
    }
}
