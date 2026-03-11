package com.student_mgt_platform.fee_payment.dto.mapper;


import com.student_mgt_platform.fee_payment.domain.model.BaseEntity;
import com.student_mgt_platform.fee_payment.dto.BaseDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BaseDtoMapper {
    @Mapping(target = "created", source = "entity.created")
    @Mapping(target = "updated", source = "entity.updated")
    BaseDto mapBaseFields(BaseEntity entity, @MappingTarget BaseDto dto);

}
