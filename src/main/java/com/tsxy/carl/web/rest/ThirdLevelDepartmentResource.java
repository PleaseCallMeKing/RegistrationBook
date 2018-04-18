package com.tsxy.carl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsxy.carl.service.ThirdLevelDepartmentService;
import com.tsxy.carl.web.rest.errors.BadRequestAlertException;
import com.tsxy.carl.web.rest.util.HeaderUtil;
import com.tsxy.carl.web.rest.util.PaginationUtil;
import com.tsxy.carl.service.dto.ThirdLevelDepartmentDTO;
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
 * REST controller for managing ThirdLevelDepartment.
 */
@RestController
@RequestMapping("/api")
public class ThirdLevelDepartmentResource {

    private final Logger log = LoggerFactory.getLogger(ThirdLevelDepartmentResource.class);

    private static final String ENTITY_NAME = "thirdLevelDepartment";

    private final ThirdLevelDepartmentService thirdLevelDepartmentService;

    public ThirdLevelDepartmentResource(ThirdLevelDepartmentService thirdLevelDepartmentService) {
        this.thirdLevelDepartmentService = thirdLevelDepartmentService;
    }

    /**
     * POST  /third-level-departments : Create a new thirdLevelDepartment.
     *
     * @param thirdLevelDepartmentDTO the thirdLevelDepartmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thirdLevelDepartmentDTO, or with status 400 (Bad Request) if the thirdLevelDepartment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/third-level-departments")
    @Timed
    public ResponseEntity<ThirdLevelDepartmentDTO> createThirdLevelDepartment(@Valid @RequestBody ThirdLevelDepartmentDTO thirdLevelDepartmentDTO) throws URISyntaxException {
        log.debug("REST request to save ThirdLevelDepartment : {}", thirdLevelDepartmentDTO);
        if (thirdLevelDepartmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new thirdLevelDepartment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThirdLevelDepartmentDTO result = thirdLevelDepartmentService.save(thirdLevelDepartmentDTO);
        return ResponseEntity.created(new URI("/api/third-level-departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /third-level-departments : Updates an existing thirdLevelDepartment.
     *
     * @param thirdLevelDepartmentDTO the thirdLevelDepartmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thirdLevelDepartmentDTO,
     * or with status 400 (Bad Request) if the thirdLevelDepartmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the thirdLevelDepartmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/third-level-departments")
    @Timed
    public ResponseEntity<ThirdLevelDepartmentDTO> updateThirdLevelDepartment(@Valid @RequestBody ThirdLevelDepartmentDTO thirdLevelDepartmentDTO) throws URISyntaxException {
        log.debug("REST request to update ThirdLevelDepartment : {}", thirdLevelDepartmentDTO);
        if (thirdLevelDepartmentDTO.getId() == null) {
            return createThirdLevelDepartment(thirdLevelDepartmentDTO);
        }
        ThirdLevelDepartmentDTO result = thirdLevelDepartmentService.save(thirdLevelDepartmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, thirdLevelDepartmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /third-level-departments : get all the thirdLevelDepartments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thirdLevelDepartments in body
     */
    @GetMapping("/third-level-departments")
    @Timed
    public ResponseEntity<List<ThirdLevelDepartmentDTO>> getAllThirdLevelDepartments(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ThirdLevelDepartments");
        Page<ThirdLevelDepartmentDTO> page = thirdLevelDepartmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/third-level-departments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /third-level-departments/:id : get the "id" thirdLevelDepartment.
     *
     * @param id the id of the thirdLevelDepartmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thirdLevelDepartmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/third-level-departments/{id}")
    @Timed
    public ResponseEntity<ThirdLevelDepartmentDTO> getThirdLevelDepartment(@PathVariable Long id) {
        log.debug("REST request to get ThirdLevelDepartment : {}", id);
        ThirdLevelDepartmentDTO thirdLevelDepartmentDTO = thirdLevelDepartmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(thirdLevelDepartmentDTO));
    }

    /**
     * DELETE  /third-level-departments/:id : delete the "id" thirdLevelDepartment.
     *
     * @param id the id of the thirdLevelDepartmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/third-level-departments/{id}")
    @Timed
    public ResponseEntity<Void> deleteThirdLevelDepartment(@PathVariable Long id) {
        log.debug("REST request to delete ThirdLevelDepartment : {}", id);
        thirdLevelDepartmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
