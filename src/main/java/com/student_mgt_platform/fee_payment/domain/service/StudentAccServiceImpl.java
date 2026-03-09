package com.student_mgt_platform.fee_payment.domain.service;

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
    public void createStudentAccount(String studentNumber) {
        if(getStudentAccount(studentNumber).isEmpty()) {
            StudentAccount studentAccount = new StudentAccount();
            studentAccount.setStudentNumber(studentNumber);
            studentAccountRepository.save(studentAccount);
        }
    }

    @Override
    public Optional<StudentAccount> getStudentAccount(String studentNumber) {
        return studentAccountRepository.findByStudentNumber(studentNumber);
    }
}
