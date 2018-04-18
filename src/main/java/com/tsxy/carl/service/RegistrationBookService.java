package com.tsxy.carl.service;

import com.tsxy.carl.domain.RegistrationBook;
import com.tsxy.carl.repository.RegistrationBookRepository;
import com.tsxy.carl.service.dto.RegistrationBookDTO;
import com.tsxy.carl.service.mapper.RegistrationBookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RegistrationBook.
 */
@Service
@Transactional
public class RegistrationBookService {

    private final Logger log = LoggerFactory.getLogger(RegistrationBookService.class);

    private final RegistrationBookRepository registrationBookRepository;

    private final RegistrationBookMapper registrationBookMapper;

    public RegistrationBookService(RegistrationBookRepository registrationBookRepository, RegistrationBookMapper registrationBookMapper) {
        this.registrationBookRepository = registrationBookRepository;
        this.registrationBookMapper = registrationBookMapper;
    }

    /**
     * Save a registrationBook.
     *
     * @param registrationBookDTO the entity to save
     * @return the persisted entity
     */
    public RegistrationBookDTO save(RegistrationBookDTO registrationBookDTO) {
        log.debug("Request to save RegistrationBook : {}", registrationBookDTO);
        RegistrationBook registrationBook = registrationBookMapper.toEntity(registrationBookDTO);
        registrationBook = registrationBookRepository.save(registrationBook);
        return registrationBookMapper.toDto(registrationBook);
    }

    /**
     *  Get all the registrationBooks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RegistrationBookDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegistrationBooks");
        return registrationBookRepository.findAll(pageable)
            .map(registrationBookMapper::toDto);
    }

    /**
     *  Get one registrationBook by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RegistrationBookDTO findOne(Long id) {
        log.debug("Request to get RegistrationBook : {}", id);
        RegistrationBook registrationBook = registrationBookRepository.findOne(id);
        return registrationBookMapper.toDto(registrationBook);
    }

    /**
     *  Delete the  registrationBook by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RegistrationBook : {}", id);
        registrationBookRepository.delete(id);
    }
}
