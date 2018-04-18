package com.tsxy.carl.service.mapper;

import com.tsxy.carl.domain.*;
import com.tsxy.carl.service.dto.SecondLevelDepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SecondLevelDepartment and its DTO SecondLevelDepartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SecondLevelDepartmentMapper extends EntityMapper<SecondLevelDepartmentDTO, SecondLevelDepartment> {

    

    @Mapping(target = "depts", ignore = true)
    SecondLevelDepartment toEntity(SecondLevelDepartmentDTO secondLevelDepartmentDTO);

    default SecondLevelDepartment fromId(Long id) {
        if (id == null) {
            return null;
        }
        SecondLevelDepartment secondLevelDepartment = new SecondLevelDepartment();
        secondLevelDepartment.setId(id);
        return secondLevelDepartment;
    }
}
