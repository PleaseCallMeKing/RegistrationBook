package com.tsxy.carl.service;

import com.tsxy.carl.domain.RegistrationBook;
import com.tsxy.carl.domain.User;
import com.tsxy.carl.repository.RegistrationBookRepository;
import com.tsxy.carl.repository.UserRepository;
import com.tsxy.carl.security.SecurityUtils;
import com.tsxy.carl.service.dto.RegistrationBookDTO;
import com.tsxy.carl.service.mapper.RegistrationBookMapper;
import com.tsxy.carl.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing RegistrationBook.
 */
@Service
@Transactional
public class RegistrationBookService {

    private final Logger log = LoggerFactory.getLogger(RegistrationBookService.class);

    private final RegistrationBookRepository registrationBookRepository;

    private final RegistrationBookMapper registrationBookMapper;

    private final UserRepository userRepository;

    public RegistrationBookService(RegistrationBookRepository registrationBookRepository, RegistrationBookMapper registrationBookMapper, UserRepository userRepository) {
        this.registrationBookRepository = registrationBookRepository;
        this.registrationBookMapper = registrationBookMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a registrationBook.
     *
     * @param registrationBookDTO the entity to save
     * @return the persisted entity
     */
    public RegistrationBookDTO save(RegistrationBookDTO registrationBookDTO) throws Exception {
        log.debug("Request to save RegistrationBook : {}", registrationBookDTO);
        Optional<User> currentUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if(!currentUser.isPresent()) throw new BadRequestAlertException("当前用户登录已过期或未登录，请重新登录","RegistrationBook", "Login is failure");
        registrationBookDTO.setUserId(currentUser.get().getId());
        registrationBookDTO.setUserName(currentUser.get().getLastName() + currentUser.get().getFirstName());
        registrationBookDTO.setMobilePhone(15230515282L);
        registrationBookDTO.setIdCard("130181199310166473");
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

    @Transactional(readOnly = true)
    public  Page<RegistrationBookDTO> findAllByUserId(Long userId, Pageable pageable) {
        log.debug("Request to get all RegistrationBooks by userId", userId);
        return registrationBookRepository.findAllByUserId(userId, pageable)
            .map(registrationBookMapper::toDto);
    }

    @Transactional(readOnly = true)
    public  Page<RegistrationBookDTO> findAllByDoctorId(Long doctorId, Pageable pageable) {
        log.debug("Request to get all RegistrationBooks by doctor", doctorId);
        return registrationBookRepository.findAllByDoctorId(doctorId, pageable)
            .map(registrationBookMapper::toDto);
    }

}
