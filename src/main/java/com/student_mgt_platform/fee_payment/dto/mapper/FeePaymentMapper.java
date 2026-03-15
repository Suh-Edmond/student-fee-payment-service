package com.student_mgt_platform.fee_payment.dto.mapper;

import com.student_mgt_platform.fee_payment.domain.model.FeePayment;
import com.student_mgt_platform.fee_payment.dto.FeePaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.RoundingMode;
import java.time.LocalDate;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, imports = {RoundingMode.class}, config = BaseDtoMapper.class)
public interface FeePaymentMapper {
    FeePaymentMapper INSTANCE = Mappers.getMapper(FeePaymentMapper.class);

    @Mapping(target = "id", expression = "java(feePayment.getId().toString())")
    @Mapping(target = "nextDueDate", source = "nextDueDate")
    @Mapping(target = "studentNumber", source = "feePayment.studentAccount.studentNumber")
    @Mapping(target = "newBalance", expression = "java(feePayment.getNewBalance().setScale(2, RoundingMode.HALF_UP))")
    @Mapping(target = "incentiveAmount", expression = "java(feePayment.getIncentiveAmount().setScale(2, RoundingMode.HALF_UP))")
    @Mapping(target = "paymentAmount", expression = "java(feePayment.getPaymentAmount().setScale(2, RoundingMode.HALF_UP))")
    @Mapping(target = "previousBalance", expression = "java(feePayment.getPreviousBalance().setScale(2, RoundingMode.HALF_UP))")
    FeePaymentDto feePaymentDtoToFeePayment(FeePayment feePayment, LocalDate nextDueDate);
}
