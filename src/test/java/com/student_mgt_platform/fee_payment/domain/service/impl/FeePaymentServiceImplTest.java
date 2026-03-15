package com.student_mgt_platform.fee_payment.domain.service.impl;

import com.student_mgt_platform.fee_payment.constant.IncentiveRate;
import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import com.student_mgt_platform.fee_payment.domain.exceptions.BusinessValidationException;
import com.student_mgt_platform.fee_payment.domain.model.FeePayment;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import com.student_mgt_platform.fee_payment.domain.repository.FeePaymentRepository;
import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import com.student_mgt_platform.fee_payment.dto.FeePaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.student_mgt_platform.fee_payment.constant.ErrorCodes.STUDENT_ACCOUNT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FeePaymentServiceImplTest {
    @Mock
    private FeePaymentRepository mockFeePaymentRepository;

    @Mock
    private StudentAccServiceImpl mockStudentAccService;

    @Captor
    private ArgumentCaptor<StudentAccount> studentAccountArgumentCaptor;

    @Captor
    private ArgumentCaptor<FeePayment> feePaymentArgumentCaptor;

    @InjectMocks
    private FeePaymentServiceImpl feePaymentService;

    private StudentAccount studentAccount;
    private InstitutionalFee institutionalFee;
    private FeePaymentRequest feePaymentRequest;

    @BeforeEach
    void setUp() {
        institutionalFee = new InstitutionalFee();
        institutionalFee.setCategory(InstitutionalFeeCategory.FRESH_MEN);
        institutionalFee.setAmountPayable(BigDecimal.valueOf(800000));
        institutionalFee.setName("Fresh Men");

        studentAccount = new StudentAccount();
        studentAccount.setId(UUID.randomUUID());
        studentAccount.setStudentName("studentName");
        studentAccount.setStudentNumber("studentNumber");
        studentAccount.setInstitutionalFee(institutionalFee);

        feePaymentRequest = new FeePaymentRequest();
        feePaymentRequest.setPaymentAmount(BigDecimal.valueOf(100000));
        feePaymentRequest.setStudentNumber(studentAccount.getStudentNumber());
    }

    @Test
    void makeStudentFeePayment_returns_not_found_student_account_fee_not_found() {
        when(mockStudentAccService.getStudentAccount(feePaymentRequest.getStudentNumber())).thenThrow(new BusinessValidationException(STUDENT_ACCOUNT_NOT_FOUND));

        BusinessValidationException exception = assertThrows(BusinessValidationException.class, () -> feePaymentService.makeStudentFeePayment(feePaymentRequest));
        assertEquals(STUDENT_ACCOUNT_NOT_FOUND, exception.getMessage());
        assertEquals(BusinessValidationException.class, exception.getClass());
    }

    @Test
    void makeStudentFeePayment_complete_payment_when_student_account_found_and_no_previous_payment() {
        FeePayment saved = new FeePayment();
        saved.setPreviousBalance(institutionalFee.getAmountPayable());
        saved.setStudentAccount(studentAccount);
        saved.setPaymentAmount(feePaymentRequest.getPaymentAmount());
        saved.setIncentiveAmount(BigDecimal.valueOf(3000.00));
        saved.setIncentiveRate(IncentiveRate.MEDIUM_RATE);
        saved.setPaymentDate(LocalDate.now());
        saved.setId(UUID.randomUUID());
        saved.setNewBalance(BigDecimal.valueOf(697000.00));

        when(mockStudentAccService.getStudentAccount(feePaymentRequest.getStudentNumber())).thenReturn(studentAccount);
        when(mockFeePaymentRepository.findFirstByStudentAccount_IdOrderByPaymentDateDesc(studentAccount.getId())).thenReturn(Optional.empty());
        when(mockFeePaymentRepository.save(feePaymentArgumentCaptor.capture())).thenReturn(saved);

        FeePaymentDto feePaymentDto = feePaymentService.makeStudentFeePayment(feePaymentRequest);
        verify(mockFeePaymentRepository).save(feePaymentArgumentCaptor.capture());
        verify(mockStudentAccService).updateStudentAccount(studentAccountArgumentCaptor.capture());

        assertEquals(feePaymentDto.getPaymentAmount(), feePaymentArgumentCaptor.getValue().getPaymentAmount().setScale(2, RoundingMode.HALF_UP));
        assertEquals(feePaymentDto.getNewBalance(), feePaymentArgumentCaptor.getValue().getNewBalance().setScale(2, RoundingMode.HALF_UP));
        assertEquals(feePaymentDto.getPreviousBalance(), feePaymentArgumentCaptor.getValue().getPreviousBalance().setScale(2, RoundingMode.HALF_UP));
        assertEquals(feePaymentDto.getIncentiveAmount(), feePaymentArgumentCaptor.getValue().getIncentiveAmount().setScale(2, RoundingMode.HALF_UP));
        assertEquals(feePaymentDto.getIncentiveRate(), feePaymentArgumentCaptor.getValue().getIncentiveRate());
        assertEquals(feePaymentDto.getStudentNumber(), feePaymentArgumentCaptor.getValue().getStudentAccount().getStudentNumber());
        assertEquals(feePaymentDto.getNextDueDate(), studentAccountArgumentCaptor.getValue().getNextDueDate());

        assertEquals(studentAccount.getNextDueDate(), studentAccountArgumentCaptor.getValue().getNextDueDate());
    }


    @Test
    void makeStudentFeePayment_makes_payment_when_account_found_and_a_previous_payment() {
        FeePayment saved = new FeePayment();
        saved.setPreviousBalance(BigDecimal.valueOf(697000));
        saved.setStudentAccount(studentAccount);
        saved.setPaymentAmount(feePaymentRequest.getPaymentAmount());
        saved.setIncentiveAmount(BigDecimal.valueOf(3000));
        saved.setIncentiveRate(IncentiveRate.MEDIUM_RATE);
        saved.setPaymentDate(LocalDate.now());
        saved.setId(UUID.randomUUID());
        saved.setNewBalance(BigDecimal.valueOf(594000));

        FeePayment previousPayment = new FeePayment();
        previousPayment.setPreviousBalance(institutionalFee.getAmountPayable());
        previousPayment.setStudentAccount(studentAccount);
        previousPayment.setPaymentAmount(feePaymentRequest.getPaymentAmount());
        previousPayment.setIncentiveAmount(BigDecimal.valueOf(100000));
        previousPayment.setIncentiveRate(IncentiveRate.MEDIUM_RATE);
        previousPayment.setPaymentDate(LocalDate.now());
        previousPayment.setNewBalance(BigDecimal.valueOf(697000));

        when(mockStudentAccService.getStudentAccount(feePaymentRequest.getStudentNumber())).thenReturn(studentAccount);
        when(mockFeePaymentRepository.findFirstByStudentAccount_IdOrderByPaymentDateDesc(studentAccount.getId())).thenReturn(Optional.of(previousPayment));
        when(mockFeePaymentRepository.save(feePaymentArgumentCaptor.capture())).thenReturn(saved);

        FeePaymentDto feePaymentDto = feePaymentService.makeStudentFeePayment(feePaymentRequest);
        verify(mockFeePaymentRepository).save(feePaymentArgumentCaptor.capture());
        verify(mockStudentAccService).updateStudentAccount(studentAccountArgumentCaptor.capture());

        assertEquals(feePaymentDto.getPaymentAmount(), feePaymentArgumentCaptor.getValue().getPaymentAmount().setScale(2, RoundingMode.HALF_UP));
        assertEquals(feePaymentDto.getNewBalance(), feePaymentArgumentCaptor.getValue().getNewBalance().setScale(2, RoundingMode.HALF_UP));
        assertEquals(feePaymentDto.getPreviousBalance(), feePaymentArgumentCaptor.getValue().getPreviousBalance().setScale(2, RoundingMode.HALF_UP));
        assertEquals(feePaymentDto.getIncentiveAmount(), feePaymentArgumentCaptor.getValue().getIncentiveAmount().setScale(2, RoundingMode.HALF_UP));
        assertEquals(feePaymentDto.getIncentiveRate(), feePaymentArgumentCaptor.getValue().getIncentiveRate());
        assertEquals(feePaymentDto.getStudentNumber(), feePaymentArgumentCaptor.getValue().getStudentAccount().getStudentNumber());
        assertEquals(feePaymentDto.getNextDueDate(), studentAccountArgumentCaptor.getValue().getNextDueDate());

        assertEquals(studentAccount.getNextDueDate(), studentAccountArgumentCaptor.getValue().getNextDueDate());
    }


}