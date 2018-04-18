package com.tsxy.carl.service.mapper;

import com.tsxy.carl.domain.*;
import com.tsxy.carl.service.dto.ConsultRoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConsultRoom and its DTO ConsultRoomDTO.
 */
@Mapper(componentModel = "spring", uses = {ThirdLevelDepartmentMapper.class})
public interface ConsultRoomMapper extends EntityMapper<ConsultRoomDTO, ConsultRoom> {

    @Mapping(source = "dept.id", target = "deptId")
    ConsultRoomDTO toDto(ConsultRoom consultRoom); 

    @Mapping(source = "deptId", target = "dept")
    ConsultRoom toEntity(ConsultRoomDTO consultRoomDTO);

    default ConsultRoom fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConsultRoom consultRoom = new ConsultRoom();
        consultRoom.setId(id);
        return consultRoom;
    }
}
