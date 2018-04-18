package com.tsxy.carl.service;

import com.tsxy.carl.domain.ContentComment;
import com.tsxy.carl.repository.ContentCommentRepository;
import com.tsxy.carl.service.dto.ContentCommentDTO;
import com.tsxy.carl.service.mapper.ContentCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ContentComment.
 */
@Service
@Transactional
public class ContentCommentService {

    private final Logger log = LoggerFactory.getLogger(ContentCommentService.class);

    private final ContentCommentRepository contentCommentRepository;

    private final ContentCommentMapper contentCommentMapper;

    public ContentCommentService(ContentCommentRepository contentCommentRepository, ContentCommentMapper contentCommentMapper) {
        this.contentCommentRepository = contentCommentRepository;
        this.contentCommentMapper = contentCommentMapper;
    }

    /**
     * Save a contentComment.
     *
     * @param contentCommentDTO the entity to save
     * @return the persisted entity
     */
    public ContentCommentDTO save(ContentCommentDTO contentCommentDTO) {
        log.debug("Request to save ContentComment : {}", contentCommentDTO);
        ContentComment contentComment = contentCommentMapper.toEntity(contentCommentDTO);
        contentComment = contentCommentRepository.save(contentComment);
        return contentCommentMapper.toDto(contentComment);
    }

    /**
     *  Get all the contentComments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ContentCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContentComments");
        return contentCommentRepository.findAll(pageable)
            .map(contentCommentMapper::toDto);
    }

    /**
     *  Get one contentComment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ContentCommentDTO findOne(Long id) {
        log.debug("Request to get ContentComment : {}", id);
        ContentComment contentComment = contentCommentRepository.findOne(id);
        return contentCommentMapper.toDto(contentComment);
    }

    /**
     *  Delete the  contentComment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ContentComment : {}", id);
        contentCommentRepository.delete(id);
    }
}
