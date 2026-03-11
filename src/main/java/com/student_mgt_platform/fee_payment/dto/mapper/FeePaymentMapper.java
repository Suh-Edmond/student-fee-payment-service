package com.student_mgt_platform.fee_payment.dto.mapper;

import com.student_mgt_platform.fee_payment.domain.model.FeePayment;
import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, imports = {UUID.class}, config = BaseDtoMapper.class)
public interface FeePaymentMapper {
    FeePaymentMapper INSTANCE =  Mappers.getMapper(FeePaymentMapper.class);

    @Mapping(target = "nextDueDate", source = "nextDueDate")
    @Mapping(target = "studentNumber", source = "feePayment.studentAccount.studentNumber")
    FeePaymentDto feePaymentDtoToFeePayment(FeePayment feePayment, LocalDate nextDueDate);
}
