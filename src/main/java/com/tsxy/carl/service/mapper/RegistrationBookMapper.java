package com.tsxy.carl.service.mapper;

import com.tsxy.carl.domain.*;
import com.tsxy.carl.service.dto.RegistrationBookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RegistrationBook and its DTO RegistrationBookDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegistrationBookMapper extends EntityMapper<RegistrationBookDTO, RegistrationBook> {

    

    

    default RegistrationBook fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegistrationBook registrationBook = new RegistrationBook();
        registrationBook.setId(id);
        return registrationBook;
    }
}
