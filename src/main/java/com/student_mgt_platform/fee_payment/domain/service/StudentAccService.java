package com.student_mgt_platform.fee_payment.domain.service;

import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;

import java.util.Optional;

public interface StudentAccService {
    StudentAccount createStudentAccount(String studentAccount);

    Optional<StudentAccount> getStudentAccount(String studentId);
}
