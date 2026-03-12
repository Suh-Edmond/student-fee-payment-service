package com.student_mgt_platform.fee_payment.domain.service;

import com.student_mgt_platform.fee_payment.constant.IncentiveRate;
import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import com.student_mgt_platform.fee_payment.domain.exceptions.BusinessValidationException;
import com.student_mgt_platform.fee_payment.domain.model.FeePayment;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import com.student_mgt_platform.fee_payment.domain.repository.FeePaymentRepository;
import com.student_mgt_platform.fee_payment.domain.repository.InstitutionalFeeRepository;
import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import com.student_mgt_platform.fee_payment.dto.FeePaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FeePaymentServiceImplTest {
    @Mock
    private  FeePaymentRepository mockFeePaymentRepository;

    @Mock
    private  InstitutionalFeeRepository mockInstitutionalFeeRepository;

    @Mock
    private  StudentAccServiceImpl mockStudentAccService;

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
        studentAccount.setStudentNumber("studentNumber");
        studentAccount.setCurrentBalance(institutionalFee.getAmountPayable());

        feePaymentRequest = new FeePaymentRequest();
        feePaymentRequest.setInstitutionalFeeCategory(institutionalFee.getCategory());
        feePaymentRequest.setPaymentAmount(BigDecimal.valueOf(100000));
        feePaymentRequest.setStudentNumber(studentAccount.getStudentNumber());
    }

    @Test
    void makeStudentFeePayment_returns_institutional_fee_not_found(){
        when(mockInstitutionalFeeRepository.findInstitutionalFeeByCategory(InstitutionalFeeCategory.FRESH_MEN)).thenReturn(Optional.empty());

        BusinessValidationException exception = assertThrows(BusinessValidationException.class, () -> feePaymentService.makeStudentFeePayment(feePaymentRequest));
        assertEquals("Institutional fee not found", exception.getMessage());
        assertEquals(BusinessValidationException.class,exception.getClass());
    }

    @Test
    void makeStudentFeePayment_creates_student_account_and_complete_payment_when_account_not_found(){
        FeePayment saved = new FeePayment();
        saved.setPreviousBalance(institutionalFee.getAmountPayable());
        saved.setStudentAccount(studentAccount);
        saved.setPaymentAmount(feePaymentRequest.getPaymentAmount());
        saved.setIncentiveAmount(BigDecimal.valueOf(300000));
        saved.setIncentiveRate(IncentiveRate.MEDIUM_RATE);
        saved.setPaymentDate(LocalDate.now());
        saved.setNewBalance(BigDecimal.valueOf(400000));

        when(mockInstitutionalFeeRepository.findInstitutionalFeeByCategory(InstitutionalFeeCategory.FRESH_MEN)).thenReturn(Optional.of(institutionalFee));
        when(mockStudentAccService.getStudentAccount(studentAccount.getStudentNumber())).thenReturn(Optional.empty());
        when(mockStudentAccService.createStudentAccount(studentAccount.getStudentNumber(), institutionalFee)).thenReturn(studentAccount);
        when(mockFeePaymentRepository.findFirstByStudentAccount_IdOrderByPaymentDateDesc(studentAccount.getId())).thenReturn(Optional.empty());
        when(mockFeePaymentRepository.save(feePaymentArgumentCaptor.capture())).thenReturn(saved);

        FeePaymentDto feePaymentDto = feePaymentService.makeStudentFeePayment(feePaymentRequest);
        verify(mockFeePaymentRepository).save(feePaymentArgumentCaptor.capture());
        verify(mockStudentAccService).updateStudentAccount(studentAccountArgumentCaptor.capture());

        assertEquals(feePaymentDto.getPaymentAmount(), feePaymentArgumentCaptor.getValue().getPaymentAmount());
        assertEquals(feePaymentDto.getNewBalance(), feePaymentArgumentCaptor.getValue().getNewBalance());
        assertEquals(feePaymentDto.getPreviousBalance(), feePaymentArgumentCaptor.getValue().getPreviousBalance());
        assertEquals(feePaymentDto.getIncentiveAmount(), feePaymentArgumentCaptor.getValue().getIncentiveAmount());
        assertEquals(feePaymentDto.getIncentiveRate(), feePaymentArgumentCaptor.getValue().getIncentiveRate());
        assertEquals(feePaymentDto.getStudentNumber(), feePaymentArgumentCaptor.getValue().getStudentAccount().getStudentNumber());
        assertEquals(feePaymentDto.getNextDueDate(), studentAccountArgumentCaptor.getValue().getNextDueDate());

        assertEquals(studentAccount.getCurrentBalance(), studentAccountArgumentCaptor.getValue().getCurrentBalance());
        assertEquals(studentAccount.getNextDueDate(), studentAccountArgumentCaptor.getValue().getNextDueDate());
    }

    @Test
    void makeStudentFeePayment_makes_payment_when_account_found(){
        FeePayment saved = new FeePayment();
        saved.setPreviousBalance(institutionalFee.getAmountPayable());
        saved.setStudentAccount(studentAccount);
        saved.setPaymentAmount(feePaymentRequest.getPaymentAmount());
        saved.setIncentiveAmount(BigDecimal.valueOf(300000));
        saved.setIncentiveRate(IncentiveRate.MEDIUM_RATE);
        saved.setPaymentDate(LocalDate.now());
        saved.setNewBalance(BigDecimal.valueOf(400000));

        when(mockInstitutionalFeeRepository.findInstitutionalFeeByCategory(InstitutionalFeeCategory.FRESH_MEN)).thenReturn(Optional.of(institutionalFee));
        when(mockStudentAccService.getStudentAccount(studentAccount.getStudentNumber())).thenReturn(Optional.of(studentAccount));
        when(mockFeePaymentRepository.findFirstByStudentAccount_IdOrderByPaymentDateDesc(studentAccount.getId())).thenReturn(Optional.empty());
        when(mockFeePaymentRepository.save(feePaymentArgumentCaptor.capture())).thenReturn(saved);

        FeePaymentDto feePaymentDto = feePaymentService.makeStudentFeePayment(feePaymentRequest);
        verify(mockFeePaymentRepository).save(feePaymentArgumentCaptor.capture());
        verify(mockStudentAccService).updateStudentAccount(studentAccountArgumentCaptor.capture());

        assertEquals(feePaymentDto.getPaymentAmount(), feePaymentArgumentCaptor.getValue().getPaymentAmount());
        assertEquals(feePaymentDto.getNewBalance(), feePaymentArgumentCaptor.getValue().getNewBalance());
        assertEquals(feePaymentDto.getPreviousBalance(), feePaymentArgumentCaptor.getValue().getPreviousBalance());
        assertEquals(feePaymentDto.getIncentiveAmount(), feePaymentArgumentCaptor.getValue().getIncentiveAmount());
        assertEquals(feePaymentDto.getIncentiveRate(), feePaymentArgumentCaptor.getValue().getIncentiveRate());
        assertEquals(feePaymentDto.getStudentNumber(), feePaymentArgumentCaptor.getValue().getStudentAccount().getStudentNumber());
        assertEquals(feePaymentDto.getNextDueDate(), studentAccountArgumentCaptor.getValue().getNextDueDate());

        assertEquals(studentAccount.getCurrentBalance(), studentAccountArgumentCaptor.getValue().getCurrentBalance());
        assertEquals(studentAccount.getNextDueDate(), studentAccountArgumentCaptor.getValue().getNextDueDate());
    }

    @Test
    void makeStudentFeePayment_makes_payment_when_account_found_and_a_previous_payment(){
        FeePayment saved = new FeePayment();
        saved.setPreviousBalance(BigDecimal.valueOf(697000));
        saved.setStudentAccount(studentAccount);
        saved.setPaymentAmount(feePaymentRequest.getPaymentAmount());
        saved.setIncentiveAmount(BigDecimal.valueOf(300000));
        saved.setIncentiveRate(IncentiveRate.MEDIUM_RATE);
        saved.setPaymentDate(LocalDate.now());
        saved.setNewBalance(BigDecimal.valueOf(297000));

        FeePayment previousPayment = new FeePayment();
        previousPayment.setPreviousBalance(institutionalFee.getAmountPayable());
        previousPayment.setStudentAccount(studentAccount);
        previousPayment.setPaymentAmount(feePaymentRequest.getPaymentAmount());
        previousPayment.setIncentiveAmount(BigDecimal.valueOf(100000));
        previousPayment.setIncentiveRate(IncentiveRate.MEDIUM_RATE);
        previousPayment.setPaymentDate(LocalDate.now());
        previousPayment.setNewBalance(BigDecimal.valueOf(697000));

        when(mockInstitutionalFeeRepository.findInstitutionalFeeByCategory(InstitutionalFeeCategory.FRESH_MEN)).thenReturn(Optional.of(institutionalFee));
        when(mockStudentAccService.getStudentAccount(studentAccount.getStudentNumber())).thenReturn(Optional.of(studentAccount));
        when(mockFeePaymentRepository.findFirstByStudentAccount_IdOrderByPaymentDateDesc(studentAccount.getId())).thenReturn(Optional.of(previousPayment));
        when(mockFeePaymentRepository.save(feePaymentArgumentCaptor.capture())).thenReturn(saved);

        FeePaymentDto feePaymentDto = feePaymentService.makeStudentFeePayment(feePaymentRequest);
        verify(mockFeePaymentRepository).save(feePaymentArgumentCaptor.capture());
        verify(mockStudentAccService).updateStudentAccount(studentAccountArgumentCaptor.capture());

        assertEquals(feePaymentDto.getPaymentAmount(), feePaymentArgumentCaptor.getValue().getPaymentAmount());
        assertEquals(feePaymentDto.getNewBalance(), feePaymentArgumentCaptor.getValue().getNewBalance());
        assertEquals(feePaymentDto.getPreviousBalance(), feePaymentArgumentCaptor.getValue().getPreviousBalance());
        assertEquals(feePaymentDto.getIncentiveAmount(), feePaymentArgumentCaptor.getValue().getIncentiveAmount());
        assertEquals(feePaymentDto.getIncentiveRate(), feePaymentArgumentCaptor.getValue().getIncentiveRate());
        assertEquals(feePaymentDto.getStudentNumber(), feePaymentArgumentCaptor.getValue().getStudentAccount().getStudentNumber());
        assertEquals(feePaymentDto.getNextDueDate(), studentAccountArgumentCaptor.getValue().getNextDueDate());

        assertEquals(studentAccount.getCurrentBalance(), studentAccountArgumentCaptor.getValue().getCurrentBalance());
        assertEquals(studentAccount.getNextDueDate(), studentAccountArgumentCaptor.getValue().getNextDueDate());
    }


}