package com.student_mgt_platform.fee_payment.domain.controller;

import com.student_mgt_platform.fee_payment.domain.service.InstitutionFeeService;
import com.student_mgt_platform.fee_payment.domain.service.impl.InstitutionFeeServiceImpl;
import com.student_mgt_platform.fee_payment.dto.InstitutionFeeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InstitutionFeeControllerTest {
    @Mock
    private InstitutionFeeServiceImpl institutionFeeService;

    @InjectMocks
    private InstitutionFeeController institutionFeeController;

    @Test
    void getInstitutionFees() {
        ResponseEntity<List<InstitutionFeeDto>> responseEntity = institutionFeeController.getInstitutionFees();
        verify(institutionFeeService).getInstitutionFees();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}