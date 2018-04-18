package com.tsxy.carl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsxy.carl.service.RegistrationBookService;
import com.tsxy.carl.web.rest.errors.BadRequestAlertException;
import com.tsxy.carl.web.rest.util.HeaderUtil;
import com.tsxy.carl.web.rest.util.PaginationUtil;
import com.tsxy.carl.service.dto.RegistrationBookDTO;
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
 * REST controller for managing RegistrationBook.
 */
@RestController
@RequestMapping("/api")
public class RegistrationBookResource {

    private final Logger log = LoggerFactory.getLogger(RegistrationBookResource.class);

    private static final String ENTITY_NAME = "registrationBook";

    private final RegistrationBookService registrationBookService;

    public RegistrationBookResource(RegistrationBookService registrationBookService) {
        this.registrationBookService = registrationBookService;
    }

    /**
     * POST  /registration-books : Create a new registrationBook.
     *
     * @param registrationBookDTO the registrationBookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new registrationBookDTO, or with status 400 (Bad Request) if the registrationBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/registration-books")
    @Timed
    public ResponseEntity<RegistrationBookDTO> createRegistrationBook(@Valid @RequestBody RegistrationBookDTO registrationBookDTO) throws URISyntaxException {
        log.debug("REST request to save RegistrationBook : {}", registrationBookDTO);
        if (registrationBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new registrationBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistrationBookDTO result = registrationBookService.save(registrationBookDTO);
        return ResponseEntity.created(new URI("/api/registration-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /registration-books : Updates an existing registrationBook.
     *
     * @param registrationBookDTO the registrationBookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated registrationBookDTO,
     * or with status 400 (Bad Request) if the registrationBookDTO is not valid,
     * or with status 500 (Internal Server Error) if the registrationBookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/registration-books")
    @Timed
    public ResponseEntity<RegistrationBookDTO> updateRegistrationBook(@Valid @RequestBody RegistrationBookDTO registrationBookDTO) throws URISyntaxException {
        log.debug("REST request to update RegistrationBook : {}", registrationBookDTO);
        if (registrationBookDTO.getId() == null) {
            return createRegistrationBook(registrationBookDTO);
        }
        RegistrationBookDTO result = registrationBookService.save(registrationBookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, registrationBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /registration-books : get all the registrationBooks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of registrationBooks in body
     */
    @GetMapping("/registration-books")
    @Timed
    public ResponseEntity<List<RegistrationBookDTO>> getAllRegistrationBooks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RegistrationBooks");
        Page<RegistrationBookDTO> page = registrationBookService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/registration-books");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /registration-books/:id : get the "id" registrationBook.
     *
     * @param id the id of the registrationBookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the registrationBookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/registration-books/{id}")
    @Timed
    public ResponseEntity<RegistrationBookDTO> getRegistrationBook(@PathVariable Long id) {
        log.debug("REST request to get RegistrationBook : {}", id);
        RegistrationBookDTO registrationBookDTO = registrationBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(registrationBookDTO));
    }

    /**
     * DELETE  /registration-books/:id : delete the "id" registrationBook.
     *
     * @param id the id of the registrationBookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/registration-books/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegistrationBook(@PathVariable Long id) {
        log.debug("REST request to delete RegistrationBook : {}", id);
        registrationBookService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
