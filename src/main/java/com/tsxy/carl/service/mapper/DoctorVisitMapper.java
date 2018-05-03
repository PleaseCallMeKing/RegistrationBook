package com.tsxy.carl.service.mapper;

import com.tsxy.carl.domain.*;
import com.tsxy.carl.service.dto.DoctorVisitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DoctorVisit and its DTO DoctorVisitDTO.
 */
@Mapper(componentModel = "spring", uses = {DoctorMapper.class, ConsultRoomMapper.class})
public interface DoctorVisitMapper extends EntityMapper<DoctorVisitDTO, DoctorVisit> {

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "doctor.fullName", target = "doctorName")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.consultRoomName", target = "roomName")
    DoctorVisitDTO toDto(DoctorVisit doctorVisit);

    @Mapping(source = "doctorId", target = "doctor")
    @Mapping(source = "roomId", target = "room")
    DoctorVisit toEntity(DoctorVisitDTO doctorVisitDTO);

    default DoctorVisit fromId(Long id) {
        if (id == null) {
            return null;
        }
        DoctorVisit doctorVisit = new DoctorVisit();
        doctorVisit.setId(id);
        return doctorVisit;
    }
}
