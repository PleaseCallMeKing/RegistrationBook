package com.tsxy.carl.service.mapper;

import com.tsxy.carl.domain.*;
import com.tsxy.carl.service.dto.DoctorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Doctor and its DTO DoctorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DoctorMapper extends EntityMapper<DoctorDTO, Doctor> {

    

    @Mapping(target = "depts", ignore = true)
    Doctor toEntity(DoctorDTO doctorDTO);

    default Doctor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Doctor doctor = new Doctor();
        doctor.setId(id);
        return doctor;
    }
}
