package com.student_mgt_platform.fee_payment.domain.controller;

import com.student_mgt_platform.fee_payment.domain.service.FeePaymentService;
import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import com.student_mgt_platform.fee_payment.dto.FeePaymentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class FeePaymentController {
    private final FeePaymentService feePaymentService;

    @PostMapping("one-time-fee-payment")
    public ResponseEntity<FeePaymentDto> oneTimeFeePayment(@Valid @RequestBody final FeePaymentRequest request) {
        return ResponseEntity.ok(feePaymentService.makeStudentFeePayment(request));
    }

    @GetMapping("student-payments")
    public ResponseEntity<List<FeePaymentDto>> getStudentPayments(@RequestParam() final String studentNumber) {
        return ResponseEntity.ok(feePaymentService.getStudentPayments(studentNumber));
    }
}
