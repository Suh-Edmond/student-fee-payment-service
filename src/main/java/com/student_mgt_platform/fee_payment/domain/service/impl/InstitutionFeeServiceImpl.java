package com.student_mgt_platform.fee_payment.domain.service.impl;

import com.student_mgt_platform.fee_payment.domain.repository.InstitutionalFeeRepository;
import com.student_mgt_platform.fee_payment.domain.service.InstitutionFeeService;
import com.student_mgt_platform.fee_payment.dto.InstitutionFeeDto;
import com.student_mgt_platform.fee_payment.dto.mapper.InstitutionFeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionFeeServiceImpl implements InstitutionFeeService {
    private final InstitutionalFeeRepository institutionalFeeRepository;
    @Override
    public List<InstitutionFeeDto> getInstitutionFees() {
        return InstitutionFeeMapper.INSTANCE.mapToInstitutionFeeDtoList(institutionalFeeRepository.findAll());
    }
}
