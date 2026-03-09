package com.student_mgt_platform.fee_payment.dto.mapper;

import com.student_mgt_platform.fee_payment.domain.model.FeePayment;
import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FeePaymentMapper {
    FeePaymentMapper INSTANCE = Mappers.getMapper(FeePaymentMapper.class);

    @Mapping(target = "studentNumber", source = "feePayment.getStudentAccount().getStudentNumber()")
    FeePaymentDto feePaymentDtoToFeePayment(FeePayment feePayment);
}
