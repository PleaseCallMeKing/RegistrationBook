package com.tsxy.carl.service;

import com.tsxy.carl.domain.ThirdLevelDepartment;
import com.tsxy.carl.repository.ThirdLevelDepartmentRepository;
import com.tsxy.carl.service.dto.ThirdLevelDepartmentDTO;
import com.tsxy.carl.service.mapper.ThirdLevelDepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ThirdLevelDepartment.
 */
@Service
@Transactional
public class ThirdLevelDepartmentService {

    private final Logger log = LoggerFactory.getLogger(ThirdLevelDepartmentService.class);

    private final ThirdLevelDepartmentRepository thirdLevelDepartmentRepository;

    private final ThirdLevelDepartmentMapper thirdLevelDepartmentMapper;

    public ThirdLevelDepartmentService(ThirdLevelDepartmentRepository thirdLevelDepartmentRepository, ThirdLevelDepartmentMapper thirdLevelDepartmentMapper) {
        this.thirdLevelDepartmentRepository = thirdLevelDepartmentRepository;
        this.thirdLevelDepartmentMapper = thirdLevelDepartmentMapper;
    }

    /**
     * Save a thirdLevelDepartment.
     *
     * @param thirdLevelDepartmentDTO the entity to save
     * @return the persisted entity
     */
    public ThirdLevelDepartmentDTO save(ThirdLevelDepartmentDTO thirdLevelDepartmentDTO) {
        log.debug("Request to save ThirdLevelDepartment : {}", thirdLevelDepartmentDTO);
        ThirdLevelDepartment thirdLevelDepartment = thirdLevelDepartmentMapper.toEntity(thirdLevelDepartmentDTO);
        thirdLevelDepartment = thirdLevelDepartmentRepository.save(thirdLevelDepartment);
        return thirdLevelDepartmentMapper.toDto(thirdLevelDepartment);
    }

    /**
     *  Get all the thirdLevelDepartments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ThirdLevelDepartmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThirdLevelDepartments");
        return thirdLevelDepartmentRepository.findAll(pageable)
            .map(thirdLevelDepartmentMapper::toDto);
    }

    /**
     *  Get one thirdLevelDepartment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ThirdLevelDepartmentDTO findOne(Long id) {
        log.debug("Request to get ThirdLevelDepartment : {}", id);
        ThirdLevelDepartment thirdLevelDepartment = thirdLevelDepartmentRepository.findOneWithEagerRelationships(id);
        return thirdLevelDepartmentMapper.toDto(thirdLevelDepartment);
    }

    /**
     *  Delete the  thirdLevelDepartment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ThirdLevelDepartment : {}", id);
        thirdLevelDepartmentRepository.delete(id);
    }
}
