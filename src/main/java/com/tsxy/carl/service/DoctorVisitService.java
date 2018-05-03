package com.tsxy.carl.service;

import com.tsxy.carl.domain.DoctorVisit;
import com.tsxy.carl.repository.DoctorVisitRepository;
import com.tsxy.carl.service.dto.DoctorVisitDTO;
import com.tsxy.carl.service.mapper.DoctorVisitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DoctorVisit.
 */
@Service
@Transactional
public class DoctorVisitService {

    private final Logger log = LoggerFactory.getLogger(DoctorVisitService.class);

    private final DoctorVisitRepository doctorVisitRepository;

    private final DoctorVisitMapper doctorVisitMapper;

    public DoctorVisitService(DoctorVisitRepository doctorVisitRepository, DoctorVisitMapper doctorVisitMapper) {
        this.doctorVisitRepository = doctorVisitRepository;
        this.doctorVisitMapper = doctorVisitMapper;
    }

    /**
     * Save a doctorVisit.
     *
     * @param doctorVisitDTO the entity to save
     * @return the persisted entity
     */
    public DoctorVisitDTO save(DoctorVisitDTO doctorVisitDTO) {
        log.debug("Request to save DoctorVisit : {}", doctorVisitDTO);
        DoctorVisit doctorVisit = doctorVisitMapper.toEntity(doctorVisitDTO);
        doctorVisit = doctorVisitRepository.save(doctorVisit);
        return doctorVisitMapper.toDto(doctorVisit);
    }

    /**
     *  Get all the doctorVisits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DoctorVisitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DoctorVisits");
        return doctorVisitRepository.findAll(pageable)
            .map(doctorVisitMapper::toDto);
    }

    /**
     *  Get one doctorVisit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DoctorVisitDTO findOne(Long id) {
        log.debug("Request to get DoctorVisit : {}", id);
        DoctorVisit doctorVisit = doctorVisitRepository.findOne(id);
        return doctorVisitMapper.toDto(doctorVisit);
    }

    /**
     *  Delete the  doctorVisit by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DoctorVisit : {}", id);
        doctorVisitRepository.delete(id);
    }
}
