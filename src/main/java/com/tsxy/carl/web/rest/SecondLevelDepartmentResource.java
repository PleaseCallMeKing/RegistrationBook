package com.tsxy.carl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsxy.carl.service.SecondLevelDepartmentService;
import com.tsxy.carl.web.rest.errors.BadRequestAlertException;
import com.tsxy.carl.web.rest.util.HeaderUtil;
import com.tsxy.carl.web.rest.util.PaginationUtil;
import com.tsxy.carl.service.dto.SecondLevelDepartmentDTO;
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
 * REST controller for managing SecondLevelDepartment.
 */
@RestController
@RequestMapping("/api")
public class SecondLevelDepartmentResource {

    private final Logger log = LoggerFactory.getLogger(SecondLevelDepartmentResource.class);

    private static final String ENTITY_NAME = "secondLevelDepartment";

    private final SecondLevelDepartmentService secondLevelDepartmentService;

    public SecondLevelDepartmentResource(SecondLevelDepartmentService secondLevelDepartmentService) {
        this.secondLevelDepartmentService = secondLevelDepartmentService;
    }

    /**
     * POST  /second-level-departments : Create a new secondLevelDepartment.
     *
     * @param secondLevelDepartmentDTO the secondLevelDepartmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new secondLevelDepartmentDTO, or with status 400 (Bad Request) if the secondLevelDepartment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/second-level-departments")
    @Timed
    public ResponseEntity<SecondLevelDepartmentDTO> createSecondLevelDepartment(@Valid @RequestBody SecondLevelDepartmentDTO secondLevelDepartmentDTO) throws URISyntaxException {
        log.debug("REST request to save SecondLevelDepartment : {}", secondLevelDepartmentDTO);
        if (secondLevelDepartmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new secondLevelDepartment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecondLevelDepartmentDTO result = secondLevelDepartmentService.save(secondLevelDepartmentDTO);
        return ResponseEntity.created(new URI("/api/second-level-departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /second-level-departments : Updates an existing secondLevelDepartment.
     *
     * @param secondLevelDepartmentDTO the secondLevelDepartmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated secondLevelDepartmentDTO,
     * or with status 400 (Bad Request) if the secondLevelDepartmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the secondLevelDepartmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/second-level-departments")
    @Timed
    public ResponseEntity<SecondLevelDepartmentDTO> updateSecondLevelDepartment(@Valid @RequestBody SecondLevelDepartmentDTO secondLevelDepartmentDTO) throws URISyntaxException {
        log.debug("REST request to update SecondLevelDepartment : {}", secondLevelDepartmentDTO);
        if (secondLevelDepartmentDTO.getId() == null) {
            return createSecondLevelDepartment(secondLevelDepartmentDTO);
        }
        SecondLevelDepartmentDTO result = secondLevelDepartmentService.save(secondLevelDepartmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, secondLevelDepartmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /second-level-departments : get all the secondLevelDepartments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of secondLevelDepartments in body
     */
    @GetMapping("/second-level-departments")
    @Timed
    public ResponseEntity<List<SecondLevelDepartmentDTO>> getAllSecondLevelDepartments(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SecondLevelDepartments");
        Page<SecondLevelDepartmentDTO> page = secondLevelDepartmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/second-level-departments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /second-level-departments/:id : get the "id" secondLevelDepartment.
     *
     * @param id the id of the secondLevelDepartmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the secondLevelDepartmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/second-level-departments/{id}")
    @Timed
    public ResponseEntity<SecondLevelDepartmentDTO> getSecondLevelDepartment(@PathVariable Long id) {
        log.debug("REST request to get SecondLevelDepartment : {}", id);
        SecondLevelDepartmentDTO secondLevelDepartmentDTO = secondLevelDepartmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(secondLevelDepartmentDTO));
    }

    /**
     * DELETE  /second-level-departments/:id : delete the "id" secondLevelDepartment.
     *
     * @param id the id of the secondLevelDepartmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/second-level-departments/{id}")
    @Timed
    public ResponseEntity<Void> deleteSecondLevelDepartment(@PathVariable Long id) {
        log.debug("REST request to delete SecondLevelDepartment : {}", id);
        secondLevelDepartmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
