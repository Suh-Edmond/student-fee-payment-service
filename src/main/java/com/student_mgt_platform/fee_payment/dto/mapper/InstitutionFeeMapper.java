package com.student_mgt_platform.fee_payment.dto.mapper;

import com.student_mgt_platform.fee_payment.domain.model.InstitutionalFee;
import com.student_mgt_platform.fee_payment.dto.InstitutionFeeDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, config = BaseDtoMapper.class)
public interface InstitutionFeeMapper {
    InstitutionFeeMapper INSTANCE = Mappers.getMapper(InstitutionFeeMapper.class);

    @Mapping(target = "id", expression = "java(institutionalFee.getId().toString())")
    InstitutionFeeDto mapToInstitutionFeeDto(InstitutionalFee institutionalFee);

    @InheritConfiguration
    List<InstitutionFeeDto> mapToInstitutionFeeDtoList(List<InstitutionalFee> institutionalFees);
}
