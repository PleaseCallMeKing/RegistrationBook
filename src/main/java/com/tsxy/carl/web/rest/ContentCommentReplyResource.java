package com.tsxy.carl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsxy.carl.service.ContentCommentReplyService;
import com.tsxy.carl.web.rest.errors.BadRequestAlertException;
import com.tsxy.carl.web.rest.util.HeaderUtil;
import com.tsxy.carl.web.rest.util.PaginationUtil;
import com.tsxy.carl.service.dto.ContentCommentReplyDTO;
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
 * REST controller for managing ContentCommentReply.
 */
@RestController
@RequestMapping("/api")
public class ContentCommentReplyResource {

    private final Logger log = LoggerFactory.getLogger(ContentCommentReplyResource.class);

    private static final String ENTITY_NAME = "contentCommentReply";

    private final ContentCommentReplyService contentCommentReplyService;

    public ContentCommentReplyResource(ContentCommentReplyService contentCommentReplyService) {
        this.contentCommentReplyService = contentCommentReplyService;
    }

    /**
     * POST  /content-comment-replies : Create a new contentCommentReply.
     *
     * @param contentCommentReplyDTO the contentCommentReplyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentCommentReplyDTO, or with status 400 (Bad Request) if the contentCommentReply has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/content-comment-replies")
    @Timed
    public ResponseEntity<ContentCommentReplyDTO> createContentCommentReply(@Valid @RequestBody ContentCommentReplyDTO contentCommentReplyDTO) throws URISyntaxException {
        log.debug("REST request to save ContentCommentReply : {}", contentCommentReplyDTO);
        if (contentCommentReplyDTO.getId() != null) {
            throw new BadRequestAlertException("A new contentCommentReply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentCommentReplyDTO result = contentCommentReplyService.save(contentCommentReplyDTO);
        return ResponseEntity.created(new URI("/api/content-comment-replies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /content-comment-replies : Updates an existing contentCommentReply.
     *
     * @param contentCommentReplyDTO the contentCommentReplyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentCommentReplyDTO,
     * or with status 400 (Bad Request) if the contentCommentReplyDTO is not valid,
     * or with status 500 (Internal Server Error) if the contentCommentReplyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/content-comment-replies")
    @Timed
    public ResponseEntity<ContentCommentReplyDTO> updateContentCommentReply(@Valid @RequestBody ContentCommentReplyDTO contentCommentReplyDTO) throws URISyntaxException {
        log.debug("REST request to update ContentCommentReply : {}", contentCommentReplyDTO);
        if (contentCommentReplyDTO.getId() == null) {
            return createContentCommentReply(contentCommentReplyDTO);
        }
        ContentCommentReplyDTO result = contentCommentReplyService.save(contentCommentReplyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contentCommentReplyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /content-comment-replies : get all the contentCommentReplies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contentCommentReplies in body
     */
    @GetMapping("/content-comment-replies")
    @Timed
    public ResponseEntity<List<ContentCommentReplyDTO>> getAllContentCommentReplies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ContentCommentReplies");
        Page<ContentCommentReplyDTO> page = contentCommentReplyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/content-comment-replies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /content-comment-replies/:id : get the "id" contentCommentReply.
     *
     * @param id the id of the contentCommentReplyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentCommentReplyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/content-comment-replies/{id}")
    @Timed
    public ResponseEntity<ContentCommentReplyDTO> getContentCommentReply(@PathVariable Long id) {
        log.debug("REST request to get ContentCommentReply : {}", id);
        ContentCommentReplyDTO contentCommentReplyDTO = contentCommentReplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contentCommentReplyDTO));
    }

    /**
     * DELETE  /content-comment-replies/:id : delete the "id" contentCommentReply.
     *
     * @param id the id of the contentCommentReplyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/content-comment-replies/{id}")
    @Timed
    public ResponseEntity<Void> deleteContentCommentReply(@PathVariable Long id) {
        log.debug("REST request to delete ContentCommentReply : {}", id);
        contentCommentReplyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
