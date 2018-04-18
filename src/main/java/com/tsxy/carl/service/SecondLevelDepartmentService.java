package com.tsxy.carl.service;

import com.tsxy.carl.domain.SecondLevelDepartment;
import com.tsxy.carl.repository.SecondLevelDepartmentRepository;
import com.tsxy.carl.service.dto.SecondLevelDepartmentDTO;
import com.tsxy.carl.service.mapper.SecondLevelDepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SecondLevelDepartment.
 */
@Service
@Transactional
public class SecondLevelDepartmentService {

    private final Logger log = LoggerFactory.getLogger(SecondLevelDepartmentService.class);

    private final SecondLevelDepartmentRepository secondLevelDepartmentRepository;

    private final SecondLevelDepartmentMapper secondLevelDepartmentMapper;

    public SecondLevelDepartmentService(SecondLevelDepartmentRepository secondLevelDepartmentRepository, SecondLevelDepartmentMapper secondLevelDepartmentMapper) {
        this.secondLevelDepartmentRepository = secondLevelDepartmentRepository;
        this.secondLevelDepartmentMapper = secondLevelDepartmentMapper;
    }

    /**
     * Save a secondLevelDepartment.
     *
     * @param secondLevelDepartmentDTO the entity to save
     * @return the persisted entity
     */
    public SecondLevelDepartmentDTO save(SecondLevelDepartmentDTO secondLevelDepartmentDTO) {
        log.debug("Request to save SecondLevelDepartment : {}", secondLevelDepartmentDTO);
        SecondLevelDepartment secondLevelDepartment = secondLevelDepartmentMapper.toEntity(secondLevelDepartmentDTO);
        secondLevelDepartment = secondLevelDepartmentRepository.save(secondLevelDepartment);
        return secondLevelDepartmentMapper.toDto(secondLevelDepartment);
    }

    /**
     *  Get all the secondLevelDepartments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SecondLevelDepartmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SecondLevelDepartments");
        return secondLevelDepartmentRepository.findAll(pageable)
            .map(secondLevelDepartmentMapper::toDto);
    }

    /**
     *  Get one secondLevelDepartment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SecondLevelDepartmentDTO findOne(Long id) {
        log.debug("Request to get SecondLevelDepartment : {}", id);
        SecondLevelDepartment secondLevelDepartment = secondLevelDepartmentRepository.findOne(id);
        return secondLevelDepartmentMapper.toDto(secondLevelDepartment);
    }

    /**
     *  Delete the  secondLevelDepartment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SecondLevelDepartment : {}", id);
        secondLevelDepartmentRepository.delete(id);
    }
}
