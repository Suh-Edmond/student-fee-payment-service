package com.student_mgt_platform.fee_payment.dto.mapper;


import com.student_mgt_platform.fee_payment.domain.model.BaseEntity;
import com.student_mgt_platform.fee_payment.dto.BaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BaseDtoMapper {
    BaseDtoMapper INSTANCE = Mappers.getMapper(BaseDtoMapper.class);

    BaseDto toBaseDto(BaseEntity baseEntity);
}
