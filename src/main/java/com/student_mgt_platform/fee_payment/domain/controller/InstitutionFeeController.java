package com.student_mgt_platform.fee_payment.domain.controller;

import com.student_mgt_platform.fee_payment.domain.service.InstitutionFeeService;
import com.student_mgt_platform.fee_payment.dto.InstitutionFeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/public/institution-fees")
public class InstitutionFeeController {
    private final InstitutionFeeService institutionFeeService;

    @GetMapping()
    public ResponseEntity<List<InstitutionFeeDto>> getInstitutionFees() {
        return ResponseEntity.ok(institutionFeeService.getInstitutionFees());
    }
}
