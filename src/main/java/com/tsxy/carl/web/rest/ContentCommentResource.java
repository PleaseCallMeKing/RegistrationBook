package com.tsxy.carl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsxy.carl.service.ContentCommentService;
import com.tsxy.carl.web.rest.errors.BadRequestAlertException;
import com.tsxy.carl.web.rest.util.HeaderUtil;
import com.tsxy.carl.web.rest.util.PaginationUtil;
import com.tsxy.carl.service.dto.ContentCommentDTO;
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
 * REST controller for managing ContentComment.
 */
@RestController
@RequestMapping("/api")
public class ContentCommentResource {

    private final Logger log = LoggerFactory.getLogger(ContentCommentResource.class);

    private static final String ENTITY_NAME = "contentComment";

    private final ContentCommentService contentCommentService;

    public ContentCommentResource(ContentCommentService contentCommentService) {
        this.contentCommentService = contentCommentService;
    }

    /**
     * POST  /content-comments : Create a new contentComment.
     *
     * @param contentCommentDTO the contentCommentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentCommentDTO, or with status 400 (Bad Request) if the contentComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/content-comments")
    @Timed
    public ResponseEntity<ContentCommentDTO> createContentComment(@Valid @RequestBody ContentCommentDTO contentCommentDTO) throws URISyntaxException {
        log.debug("REST request to save ContentComment : {}", contentCommentDTO);
        if (contentCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new contentComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentCommentDTO result = contentCommentService.save(contentCommentDTO);
        return ResponseEntity.created(new URI("/api/content-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /content-comments : Updates an existing contentComment.
     *
     * @param contentCommentDTO the contentCommentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentCommentDTO,
     * or with status 400 (Bad Request) if the contentCommentDTO is not valid,
     * or with status 500 (Internal Server Error) if the contentCommentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/content-comments")
    @Timed
    public ResponseEntity<ContentCommentDTO> updateContentComment(@Valid @RequestBody ContentCommentDTO contentCommentDTO) throws URISyntaxException {
        log.debug("REST request to update ContentComment : {}", contentCommentDTO);
        if (contentCommentDTO.getId() == null) {
            return createContentComment(contentCommentDTO);
        }
        ContentCommentDTO result = contentCommentService.save(contentCommentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contentCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /content-comments : get all the contentComments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contentComments in body
     */
    @GetMapping("/content-comments")
    @Timed
    public ResponseEntity<List<ContentCommentDTO>> getAllContentComments(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ContentComments");
        Page<ContentCommentDTO> page = contentCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/content-comments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /content-comments/:id : get the "id" contentComment.
     *
     * @param id the id of the contentCommentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentCommentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/content-comments/{id}")
    @Timed
    public ResponseEntity<ContentCommentDTO> getContentComment(@PathVariable Long id) {
        log.debug("REST request to get ContentComment : {}", id);
        ContentCommentDTO contentCommentDTO = contentCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contentCommentDTO));
    }

    /**
     * DELETE  /content-comments/:id : delete the "id" contentComment.
     *
     * @param id the id of the contentCommentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/content-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteContentComment(@PathVariable Long id) {
        log.debug("REST request to delete ContentComment : {}", id);
        contentCommentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
