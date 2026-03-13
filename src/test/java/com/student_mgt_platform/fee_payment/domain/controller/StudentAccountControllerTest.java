package com.student_mgt_platform.fee_payment.domain.controller;

import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.service.impl.StudentAccServiceImpl;
import com.student_mgt_platform.fee_payment.dto.StudentAccountRequestDto;
import com.student_mgt_platform.fee_payment.dto.StudentAccountResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentAccountControllerTest {

    @Mock
    private StudentAccServiceImpl studentAccService;

    @InjectMocks
    private StudentAccountController studentAccountController;

    @Test
    void createStudentAccount() {
        InstitutionalFee institutionalFee = new InstitutionalFee();
        institutionalFee.setCategory(InstitutionalFeeCategory.FRESH_MEN);
        institutionalFee.setName("Fresh Men");
        institutionalFee.setAmountPayable(BigDecimal.valueOf(800000));
        institutionalFee.setId(UUID.randomUUID());

        StudentAccountRequestDto studentAccountRequestDto = new StudentAccountRequestDto();
        studentAccountRequestDto.setStudentNumber("studentNumber");
        studentAccountRequestDto.setStudentName("studentName");
        studentAccountRequestDto.setInstitutionFeeId(institutionalFee.getId().toString());

        ResponseEntity<StudentAccountResponseDto> responseEntity = studentAccountController.createStudentAccount(studentAccountRequestDto);
        verify(studentAccService).createStudentAccount(studentAccountRequestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}