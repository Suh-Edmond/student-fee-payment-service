package com.student_mgt_platform.fee_payment.domain.service;

import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import com.student_mgt_platform.fee_payment.domain.repository.StudentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentAccServiceImpl implements StudentAccService {
    private final StudentAccountRepository studentAccountRepository;

    @Override
    public StudentAccount createStudentAccount(String studentNumber, InstitutionalFee fee) {
        StudentAccount studentAccount = new StudentAccount();
        studentAccount.setStudentNumber(studentNumber);
        studentAccount.setCurrentBalance(fee.getAmountPayable());
        return studentAccountRepository.save(studentAccount);
    }

    @Override
    public Optional<StudentAccount> getStudentAccount(String studentNumber) {
        return studentAccountRepository.findByStudentNumber(studentNumber);
    }

    @Override
    public void updateStudentAccount(StudentAccount studentAccount) {
        studentAccountRepository.save(studentAccount);
    }
}
