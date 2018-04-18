package com.tsxy.carl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsxy.carl.service.ConsultRoomService;
import com.tsxy.carl.web.rest.errors.BadRequestAlertException;
import com.tsxy.carl.web.rest.util.HeaderUtil;
import com.tsxy.carl.web.rest.util.PaginationUtil;
import com.tsxy.carl.service.dto.ConsultRoomDTO;
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
 * REST controller for managing ConsultRoom.
 */
@RestController
@RequestMapping("/api")
public class ConsultRoomResource {

    private final Logger log = LoggerFactory.getLogger(ConsultRoomResource.class);

    private static final String ENTITY_NAME = "consultRoom";

    private final ConsultRoomService consultRoomService;

    public ConsultRoomResource(ConsultRoomService consultRoomService) {
        this.consultRoomService = consultRoomService;
    }

    /**
     * POST  /consult-rooms : Create a new consultRoom.
     *
     * @param consultRoomDTO the consultRoomDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultRoomDTO, or with status 400 (Bad Request) if the consultRoom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consult-rooms")
    @Timed
    public ResponseEntity<ConsultRoomDTO> createConsultRoom(@Valid @RequestBody ConsultRoomDTO consultRoomDTO) throws URISyntaxException {
        log.debug("REST request to save ConsultRoom : {}", consultRoomDTO);
        if (consultRoomDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultRoomDTO result = consultRoomService.save(consultRoomDTO);
        return ResponseEntity.created(new URI("/api/consult-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consult-rooms : Updates an existing consultRoom.
     *
     * @param consultRoomDTO the consultRoomDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultRoomDTO,
     * or with status 400 (Bad Request) if the consultRoomDTO is not valid,
     * or with status 500 (Internal Server Error) if the consultRoomDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consult-rooms")
    @Timed
    public ResponseEntity<ConsultRoomDTO> updateConsultRoom(@Valid @RequestBody ConsultRoomDTO consultRoomDTO) throws URISyntaxException {
        log.debug("REST request to update ConsultRoom : {}", consultRoomDTO);
        if (consultRoomDTO.getId() == null) {
            return createConsultRoom(consultRoomDTO);
        }
        ConsultRoomDTO result = consultRoomService.save(consultRoomDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultRoomDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consult-rooms : get all the consultRooms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultRooms in body
     */
    @GetMapping("/consult-rooms")
    @Timed
    public ResponseEntity<List<ConsultRoomDTO>> getAllConsultRooms(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ConsultRooms");
        Page<ConsultRoomDTO> page = consultRoomService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consult-rooms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /consult-rooms/:id : get the "id" consultRoom.
     *
     * @param id the id of the consultRoomDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultRoomDTO, or with status 404 (Not Found)
     */
    @GetMapping("/consult-rooms/{id}")
    @Timed
    public ResponseEntity<ConsultRoomDTO> getConsultRoom(@PathVariable Long id) {
        log.debug("REST request to get ConsultRoom : {}", id);
        ConsultRoomDTO consultRoomDTO = consultRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consultRoomDTO));
    }

    /**
     * DELETE  /consult-rooms/:id : delete the "id" consultRoom.
     *
     * @param id the id of the consultRoomDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consult-rooms/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultRoom(@PathVariable Long id) {
        log.debug("REST request to delete ConsultRoom : {}", id);
        consultRoomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
