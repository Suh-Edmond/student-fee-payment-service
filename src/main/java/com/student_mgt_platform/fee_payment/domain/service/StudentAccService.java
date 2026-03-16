package com.student_mgt_platform.fee_payment.domain.service;

import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import com.student_mgt_platform.fee_payment.dto.InstitutionFeeDto;
import com.student_mgt_platform.fee_payment.dto.StudentAccountRequestDto;
import com.student_mgt_platform.fee_payment.dto.StudentAccountResponseDto;

public interface StudentAccService {
    StudentAccountResponseDto createStudentAccount(StudentAccountRequestDto studentAccountDto);

    StudentAccount getStudentAccount(String studentId);

    void updateStudentAccount(StudentAccount studentAccount);

    InstitutionFeeDto getInstitutionFee(String studentNumber);
}
