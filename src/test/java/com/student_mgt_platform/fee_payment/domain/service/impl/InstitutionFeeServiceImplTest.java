package com.student_mgt_platform.fee_payment.domain.service.impl;

import com.student_mgt_platform.fee_payment.constant.InstitutionalFeeCategory;
import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.domain.repository.InstitutionalFeeRepository;
import com.student_mgt_platform.fee_payment.dto.InstitutionFeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstitutionFeeServiceImplTest {
    @Mock
    private InstitutionalFeeRepository mockInstitutionalFeeRepository;

    @InjectMocks
    private InstitutionFeeServiceImpl institutionFeeService;

    private InstitutionalFee institutionalFee;

    @BeforeEach
    void setUp() {
        institutionalFee = new InstitutionalFee();
        institutionalFee.setCategory(InstitutionalFeeCategory.FRESH_MEN);
        institutionalFee.setName("Fresh Men");
        institutionalFee.setAmountPayable(BigDecimal.valueOf(800000));
        institutionalFee.setId(UUID.randomUUID());

    }

    @Test
    void getInstitutionFees() {
        when(mockInstitutionalFeeRepository.findAll()).thenReturn(Collections.singletonList(institutionalFee));
        List<InstitutionFeeDto> institutionFees = institutionFeeService.getInstitutionFees();
        assertEquals(1, institutionFees.size());
        assertEquals(institutionalFee.getAmountPayable(), institutionFees.get(0).getAmountPayable());
        assertEquals(institutionalFee.getCategory(), institutionFees.get(0).getCategory());
        assertEquals(institutionalFee.getName(), institutionFees.get(0).getName());
    }
}