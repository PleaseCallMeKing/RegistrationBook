package com.tsxy.carl.service;

import com.tsxy.carl.domain.ContentCommentReply;
import com.tsxy.carl.repository.ContentCommentReplyRepository;
import com.tsxy.carl.service.dto.ContentCommentReplyDTO;
import com.tsxy.carl.service.mapper.ContentCommentReplyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ContentCommentReply.
 */
@Service
@Transactional
public class ContentCommentReplyService {

    private final Logger log = LoggerFactory.getLogger(ContentCommentReplyService.class);

    private final ContentCommentReplyRepository contentCommentReplyRepository;

    private final ContentCommentReplyMapper contentCommentReplyMapper;

    public ContentCommentReplyService(ContentCommentReplyRepository contentCommentReplyRepository, ContentCommentReplyMapper contentCommentReplyMapper) {
        this.contentCommentReplyRepository = contentCommentReplyRepository;
        this.contentCommentReplyMapper = contentCommentReplyMapper;
    }

    /**
     * Save a contentCommentReply.
     *
     * @param contentCommentReplyDTO the entity to save
     * @return the persisted entity
     */
    public ContentCommentReplyDTO save(ContentCommentReplyDTO contentCommentReplyDTO) {
        log.debug("Request to save ContentCommentReply : {}", contentCommentReplyDTO);
        ContentCommentReply contentCommentReply = contentCommentReplyMapper.toEntity(contentCommentReplyDTO);
        contentCommentReply = contentCommentReplyRepository.save(contentCommentReply);
        return contentCommentReplyMapper.toDto(contentCommentReply);
    }

    /**
     *  Get all the contentCommentReplies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ContentCommentReplyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContentCommentReplies");
        return contentCommentReplyRepository.findAll(pageable)
            .map(contentCommentReplyMapper::toDto);
    }

    /**
     *  Get one contentCommentReply by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ContentCommentReplyDTO findOne(Long id) {
        log.debug("Request to get ContentCommentReply : {}", id);
        ContentCommentReply contentCommentReply = contentCommentReplyRepository.findOne(id);
        return contentCommentReplyMapper.toDto(contentCommentReply);
    }

    /**
     *  Delete the  contentCommentReply by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ContentCommentReply : {}", id);
        contentCommentReplyRepository.delete(id);
    }
}
