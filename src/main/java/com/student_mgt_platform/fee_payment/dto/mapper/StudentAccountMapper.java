package com.student_mgt_platform.fee_payment.dto.mapper;

import com.student_mgt_platform.fee_payment.domain.model.StudentAccount;
import com.student_mgt_platform.fee_payment.dto.StudentAccountResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, config = BaseDtoMapper.class)
public interface StudentAccountMapper {
    StudentAccountMapper INSTANCE = Mappers.getMapper(StudentAccountMapper.class);

    @Mapping(target = "id", expression = "java(studentAccount.getId().toString())")
    StudentAccountResponseDto mapStudentAccountResponseDto(StudentAccount studentAccount);

}
