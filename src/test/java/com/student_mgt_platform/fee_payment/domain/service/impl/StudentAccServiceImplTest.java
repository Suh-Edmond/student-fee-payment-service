package com.student_mgt_platform.fee_payment.domain.service.impl;

import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import com.student_mgt_platform.fee_payment.domain.exceptions.ResourceNotFoundException;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import com.student_mgt_platform.fee_payment.domain.repository.InstitutionalFeeRepository;
import com.student_mgt_platform.fee_payment.domain.repository.StudentAccountRepository;
import com.student_mgt_platform.fee_payment.dto.InstitutionFeeDto;
import com.student_mgt_platform.fee_payment.dto.StudentAccountRequestDto;
import com.student_mgt_platform.fee_payment.dto.StudentAccountResponseDto;
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

import static com.student_mgt_platform.fee_payment.constant.ErrorCodes.INSTITUTION_FEE_NOT_FOUND;
import static com.student_mgt_platform.fee_payment.constant.ErrorCodes.STUDENT_ACCOUNT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentAccServiceImplTest {
    @Mock
    private StudentAccountRepository mockStudentAccountRepository;

    @Mock
    private InstitutionalFeeRepository mockInstitutionalFeeRepository;

    @InjectMocks
    private StudentAccServiceImpl studentAccService;

    @Captor
    private ArgumentCaptor<StudentAccount> studentAccountArgumentCaptor;
    private InstitutionalFee institutionalFee;
    private StudentAccountRequestDto requestDto;

    @BeforeEach
    void setUp() {
        institutionalFee = new InstitutionalFee();
        institutionalFee.setCategory(InstitutionalFeeCategory.FRESH_MEN);
        institutionalFee.setName("Fresh Men");
        institutionalFee.setAmountPayable(BigDecimal.valueOf(800000));
        institutionalFee.setId(UUID.randomUUID());

        requestDto = new StudentAccountRequestDto();
        requestDto.setInstitutionFeeId(institutionalFee.getId().toString());
        requestDto.setStudentName("studentName");
        requestDto.setStudentNumber("studentNumber");
    }

    @Test
    void createStudentAccount_createsStudentAccount_when_not_found() {
        StudentAccount savedAcc = new StudentAccount();
        savedAcc.setStudentNumber(requestDto.getStudentNumber());
        savedAcc.setStudentName(requestDto.getStudentName());
        savedAcc.setInstitutionalFee(institutionalFee);
        savedAcc.setId(UUID.randomUUID());

        when(mockInstitutionalFeeRepository.findById(UUID.fromString(requestDto.getInstitutionFeeId()))).thenReturn(Optional.of(institutionalFee));
        when(mockStudentAccountRepository.findByStudentNumber(requestDto.getStudentNumber())).thenReturn(Optional.empty());
        when(mockStudentAccountRepository.save(studentAccountArgumentCaptor.capture())).thenReturn(savedAcc);
        StudentAccountResponseDto studentAccountResponseDto = studentAccService.createStudentAccount(requestDto);
        verify(mockStudentAccountRepository).save(studentAccountArgumentCaptor.capture());

        StudentAccount studentAccount = studentAccountArgumentCaptor.getValue();

        assertEquals("studentNumber", studentAccount.getStudentNumber());
        assertEquals(institutionalFee.getId(), studentAccount.getInstitutionalFee().getId());
        assertEquals("studentName", studentAccountArgumentCaptor.getValue().getStudentName());
        assertEquals("studentNumber", studentAccountArgumentCaptor.getValue().getStudentNumber());
        assertNotNull(studentAccountArgumentCaptor.getValue().getInstitutionalFee().getId());
        assertNotNull(studentAccountResponseDto);
        assertEquals(institutionalFee.getAmountPayable(), studentAccountResponseDto.getInstitutionFeeDto().getAmountPayable());

    }

    @Test
    void createStudentAccount_returns_not_found_when_institutional_fee_not_found() {
        when(mockInstitutionalFeeRepository.findById(UUID.fromString(requestDto.getInstitutionFeeId()))).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentAccService.createStudentAccount(requestDto));

        assertEquals(INSTITUTION_FEE_NOT_FOUND, exception.getMessage());
        assertEquals(ResourceNotFoundException.class, exception.getClass());
    }

    @Test
    void getStudentAccount_returnsStudentAccount() {
        StudentAccount account = new StudentAccount();
        account.setStudentNumber("studentNumber");
        account.setStudentName("studentName");
        account.setInstitutionalFee(institutionalFee);

        when(mockStudentAccountRepository.findByStudentNumber("studentNumber")).thenReturn(Optional.of(account));
        StudentAccount studentAccount = studentAccService.getStudentAccount("studentNumber");

        assertEquals(account.getStudentName(), studentAccount.getStudentName());
        assertEquals(account.getStudentNumber(), studentAccount.getStudentNumber());
        assertEquals(account.getInstitutionalFee().getId(), studentAccount.getInstitutionalFee().getId());
    }

    @Test
    void getStudentAccount_returnsEmptyOptional() {
        when(mockStudentAccountRepository.findByStudentNumber("studentNumber")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> studentAccService.getStudentAccount("studentNumber"));

        assertEquals(STUDENT_ACCOUNT_NOT_FOUND, exception.getMessage());
        assertEquals(ResourceNotFoundException.class, exception.getClass());
    }

    @Test
    void updateStudentAccount() {
        StudentAccount account = new StudentAccount();
        account.setStudentNumber("studentNumber");
        account.setStudentName("studentName");
        account.setInstitutionalFee(institutionalFee);
        account.setNextDueDate(LocalDate.now());

        studentAccService.updateStudentAccount(account);
        verify(mockStudentAccountRepository).save(studentAccountArgumentCaptor.capture());

        assertEquals(account.getNextDueDate(), studentAccountArgumentCaptor.getValue().getNextDueDate());
    }

    @Test
    void getInstitutionalFee_returnsInstitutionalFee() {
        StudentAccount savedAcc = new StudentAccount();
        savedAcc.setStudentNumber(requestDto.getStudentNumber());
        savedAcc.setStudentName(requestDto.getStudentName());
        savedAcc.setInstitutionalFee(institutionalFee);
        savedAcc.setId(UUID.randomUUID());

        when(mockStudentAccountRepository.findByStudentNumber("studentNumber")).thenReturn(Optional.of(savedAcc));
        InstitutionFeeDto institutionFeeDto = studentAccService.getInstitutionFee("studentNumber");

        assertEquals(0, institutionFeeDto.getAmountPayable().compareTo(institutionalFee.getAmountPayable()));
        assertEquals(institutionalFee.getId().toString(), institutionFeeDto.getId());
        assertEquals(institutionalFee.getCategory(), institutionFeeDto.getCategory());
        assertEquals(institutionalFee.getName(), institutionFeeDto.getName());
    }
}