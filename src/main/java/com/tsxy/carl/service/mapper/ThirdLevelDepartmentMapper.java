package com.tsxy.carl.service.mapper;

import com.tsxy.carl.domain.*;
import com.tsxy.carl.service.dto.ThirdLevelDepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ThirdLevelDepartment and its DTO ThirdLevelDepartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {DoctorMapper.class, SecondLevelDepartmentMapper.class})
public interface ThirdLevelDepartmentMapper extends EntityMapper<ThirdLevelDepartmentDTO, ThirdLevelDepartment> {

    

    @Mapping(target = "rooms", ignore = true)
    ThirdLevelDepartment toEntity(ThirdLevelDepartmentDTO thirdLevelDepartmentDTO);

    default ThirdLevelDepartment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ThirdLevelDepartment thirdLevelDepartment = new ThirdLevelDepartment();
        thirdLevelDepartment.setId(id);
        return thirdLevelDepartment;
    }
}
