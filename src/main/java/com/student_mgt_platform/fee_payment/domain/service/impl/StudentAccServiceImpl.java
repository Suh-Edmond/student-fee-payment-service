package com.student_mgt_platform.fee_payment.domain.service.impl;

import com.student_mgt_platform.fee_payment.domain.exceptions.ResourceAlreadyExistException;
import com.student_mgt_platform.fee_payment.domain.exceptions.ResourceNotFoundException;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import com.student_mgt_platform.fee_payment.domain.repository.InstitutionalFeeRepository;
import com.student_mgt_platform.fee_payment.domain.repository.StudentAccountRepository;
import com.student_mgt_platform.fee_payment.domain.service.StudentAccService;
import com.student_mgt_platform.fee_payment.dto.InstitutionFeeDto;
import com.student_mgt_platform.fee_payment.dto.StudentAccountRequestDto;
import com.student_mgt_platform.fee_payment.dto.StudentAccountResponseDto;
import com.student_mgt_platform.fee_payment.dto.mapper.InstitutionFeeMapper;
import com.student_mgt_platform.fee_payment.dto.mapper.StudentAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.student_mgt_platform.fee_payment.constant.ErrorCodes.*;

@Service
@RequiredArgsConstructor
public class StudentAccServiceImpl implements StudentAccService {
    private final StudentAccountRepository studentAccountRepository;
    private final InstitutionalFeeRepository institutionalFeeRepository;

    @Override
    public StudentAccountResponseDto createStudentAccount(StudentAccountRequestDto requestDto) {

        InstitutionalFee institutionalFee = getInstitutionalFee(requestDto.getInstitutionFeeId());
        Optional<StudentAccount> account = studentAccountRepository.findByStudentNumber(requestDto.getStudentNumber());
        if (account.isPresent()) {
            throw new ResourceAlreadyExistException(STUDENT_ACCOUNT_FOUND);
        }
        StudentAccount studentAccount = new StudentAccount();
        studentAccount.setStudentNumber(requestDto.getStudentNumber());
        studentAccount.setStudentName(requestDto.getStudentName());
        studentAccount.setInstitutionalFee(institutionalFee);

        StudentAccount saved = studentAccountRepository.save(studentAccount);

        StudentAccountResponseDto responseDto = StudentAccountMapper.INSTANCE.mapStudentAccountResponseDto(saved);
        responseDto.setInstitutionFeeDto(InstitutionFeeMapper.INSTANCE.mapToInstitutionFeeDto(saved.getInstitutionalFee()));
        return responseDto;
    }

    @Override
    public StudentAccount getStudentAccount(String studentNumber) {
        Optional<StudentAccount> studentAccount = studentAccountRepository.findByStudentNumber(studentNumber);
        if (studentAccount.isEmpty()) {
            throw new ResourceNotFoundException(STUDENT_ACCOUNT_NOT_FOUND);
        }
        return studentAccount.get();
    }

    @Override
    public void updateStudentAccount(StudentAccount studentAccount) {
        studentAccountRepository.save(studentAccount);
    }

    @Override
    public InstitutionFeeDto getInstitutionFee(String studentNumber) {
        StudentAccount studentAccount = getStudentAccount(studentNumber);
        return  InstitutionFeeMapper.INSTANCE.mapToInstitutionFeeDto(studentAccount.getInstitutionalFee());
    }

    private InstitutionalFee getInstitutionalFee(String institutionFeeId) {
        Optional<InstitutionalFee> institutionalFee = institutionalFeeRepository.findById(UUID.fromString(institutionFeeId));
        if (institutionalFee.isEmpty()) {
            throw new ResourceNotFoundException(INSTITUTION_FEE_NOT_FOUND);
        }
        return institutionalFee.get();
    }
}
