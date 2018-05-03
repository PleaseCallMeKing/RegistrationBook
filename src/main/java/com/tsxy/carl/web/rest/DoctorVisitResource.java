package com.tsxy.carl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsxy.carl.service.DoctorVisitService;
import com.tsxy.carl.web.rest.errors.BadRequestAlertException;
import com.tsxy.carl.web.rest.util.HeaderUtil;
import com.tsxy.carl.web.rest.util.PaginationUtil;
import com.tsxy.carl.service.dto.DoctorVisitDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DoctorVisit.
 */
@RestController
@RequestMapping("/api")
public class DoctorVisitResource {

    private final Logger log = LoggerFactory.getLogger(DoctorVisitResource.class);

    private static final String ENTITY_NAME = "doctorVisit";

    private final DoctorVisitService doctorVisitService;

    public DoctorVisitResource(DoctorVisitService doctorVisitService) {
        this.doctorVisitService = doctorVisitService;
    }

    /**
     * POST  /doctor-visits : Create a new doctorVisit.
     *
     * @param doctorVisitDTO the doctorVisitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doctorVisitDTO, or with status 400 (Bad Request) if the doctorVisit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doctor-visits")
    @Timed
    public ResponseEntity<DoctorVisitDTO> createDoctorVisit(@Valid @RequestBody DoctorVisitDTO doctorVisitDTO) throws URISyntaxException {
        log.debug("REST request to save DoctorVisit : {}", doctorVisitDTO);
        if (doctorVisitDTO.getId() != null) {
            throw new BadRequestAlertException("A new doctorVisit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoctorVisitDTO result = doctorVisitService.save(doctorVisitDTO);
        return ResponseEntity.created(new URI("/api/doctor-visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doctor-visits : Updates an existing doctorVisit.
     *
     * @param doctorVisitDTO the doctorVisitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doctorVisitDTO,
     * or with status 400 (Bad Request) if the doctorVisitDTO is not valid,
     * or with status 500 (Internal Server Error) if the doctorVisitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doctor-visits")
    @Timed
    public ResponseEntity<DoctorVisitDTO> updateDoctorVisit(@Valid @RequestBody DoctorVisitDTO doctorVisitDTO) throws URISyntaxException {
        log.debug("REST request to update DoctorVisit : {}", doctorVisitDTO);
        if (doctorVisitDTO.getId() == null) {
            return createDoctorVisit(doctorVisitDTO);
        }
        DoctorVisitDTO result = doctorVisitService.save(doctorVisitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, doctorVisitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doctor-visits : get all the doctorVisits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of doctorVisits in body
     */
    @GetMapping("/doctor-visits")
    @Timed
    public ResponseEntity<List<DoctorVisitDTO>> getAllDoctorVisits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DoctorVisits");
        Page<DoctorVisitDTO> page = doctorVisitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doctor-visits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /doctor-visits/:id : get the "id" doctorVisit.
     *
     * @param id the id of the doctorVisitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doctorVisitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/doctor-visits/{id}")
    @Timed
    public ResponseEntity<DoctorVisitDTO> getDoctorVisit(@PathVariable Long id) {
        log.debug("REST request to get DoctorVisit : {}", id);
        DoctorVisitDTO doctorVisitDTO = doctorVisitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(doctorVisitDTO));
    }

    /**
     * DELETE  /doctor-visits/:id : delete the "id" doctorVisit.
     *
     * @param id the id of the doctorVisitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doctor-visits/{id}")
    @Timed
    public ResponseEntity<Void> deleteDoctorVisit(@PathVariable Long id) {
        log.debug("REST request to delete DoctorVisit : {}", id);
        doctorVisitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
