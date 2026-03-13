package com.student_mgt_platform.fee_payment.domain.controller;

import com.student_mgt_platform.fee_payment.domain.service.impl.FeePaymentServiceImpl;
import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import com.student_mgt_platform.fee_payment.dto.FeePaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FeePaymentControllerTest {
    @Mock
    private FeePaymentServiceImpl feePaymentService;

    @InjectMocks
    private FeePaymentController feePaymentController;

    private FeePaymentRequest feePaymentRequest;

    @BeforeEach
    void setUp() {
        feePaymentRequest = new FeePaymentRequest();
        feePaymentRequest.setPaymentAmount(BigDecimal.valueOf(100000));
        feePaymentRequest.setStudentNumber("studentNumber");
    }

    @Test
    void testInitiateStudentFeePaymentIsCalled() {
        ResponseEntity<FeePaymentDto> feePaymentDtoResponseEntity = feePaymentController.oneTimeFeePayment(feePaymentRequest);
        verify(feePaymentService).makeStudentFeePayment(feePaymentRequest);

        assertEquals(HttpStatus.OK, feePaymentDtoResponseEntity.getStatusCode());
    }

}