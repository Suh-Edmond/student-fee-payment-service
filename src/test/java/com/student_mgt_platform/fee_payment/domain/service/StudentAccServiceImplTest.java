package com.student_mgt_platform.fee_payment.domain.service;

import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import com.student_mgt_platform.fee_payment.domain.repository.StudentAccountRepository;
import com.student_mgt_platform.fee_payment.domain.service.impl.StudentAccServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentAccServiceImplTest {
    @Mock
    private StudentAccountRepository studentAccountRepository;

    @InjectMocks
    private StudentAccServiceImpl studentAccService;

    @Captor
    private ArgumentCaptor<StudentAccount> studentAccountArgumentCaptor;

    @Test
    void createStudentAccount() {
        InstitutionalFee fee = new InstitutionalFee();
        fee.setAmountPayable(BigDecimal.valueOf(800000));
        fee.setCategory(InstitutionalFeeCategory.FRESH_MEN);

        studentAccService.createStudentAccount("studentNumber", fee);
        verify(studentAccountRepository).save(studentAccountArgumentCaptor.capture());

        StudentAccount studentAccount = studentAccountArgumentCaptor.getValue();

        assertEquals("studentNumber", studentAccount.getStudentNumber());
        assertEquals(fee.getAmountPayable(), studentAccount.getCurrentBalance());
        assertEquals(fee.getAmountPayable(), studentAccountArgumentCaptor.getValue().getCurrentBalance());
        assertEquals("studentNumber", studentAccountArgumentCaptor.getValue().getStudentNumber());
    }

    @Test
    void getStudentAccount() {
        StudentAccount account = new StudentAccount();
        account.setStudentNumber("studentNumber");
        when(studentAccountRepository.findByStudentNumber("studentNumber")).thenReturn(Optional.of(account));
        Optional<StudentAccount> optional = studentAccService.getStudentAccount("studentNumber");

        assertTrue(optional.isPresent());
    }
}