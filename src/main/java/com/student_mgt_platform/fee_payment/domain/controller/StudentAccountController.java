package com.student_mgt_platform.fee_payment.domain.controller;

import com.student_mgt_platform.fee_payment.domain.service.StudentAccService;
import com.student_mgt_platform.fee_payment.dto.StudentAccountRequestDto;
import com.student_mgt_platform.fee_payment.dto.StudentAccountResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/student-account")
public class StudentAccountController {
    private final StudentAccService studentAccService;

    @PostMapping("create")
    public ResponseEntity<StudentAccountResponseDto> createStudentAccount(@Valid  @RequestBody StudentAccountRequestDto studentAccountDto) {
        return ResponseEntity.ok(studentAccService.createStudentAccount(studentAccountDto));
    }
}
