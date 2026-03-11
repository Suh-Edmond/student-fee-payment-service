package com.student_mgt_platform.fee_payment.domain.service;

import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;

import java.util.Optional;

public interface StudentAccService {
    StudentAccount createStudentAccount(String studentNumber, InstitutionalFee institutionalFee);

    Optional<StudentAccount> getStudentAccount(String studentId);

    void updateStudentAccount(StudentAccount studentAccount);
}
