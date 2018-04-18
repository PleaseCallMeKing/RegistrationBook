package com.tsxy.carl.service.mapper;

import com.tsxy.carl.domain.*;
import com.tsxy.carl.service.dto.ContentCommentReplyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContentCommentReply and its DTO ContentCommentReplyDTO.
 */
@Mapper(componentModel = "spring", uses = {ContentCommentMapper.class})
public interface ContentCommentReplyMapper extends EntityMapper<ContentCommentReplyDTO, ContentCommentReply> {

    @Mapping(source = "contentComment.id", target = "contentCommentId")
    ContentCommentReplyDTO toDto(ContentCommentReply contentCommentReply); 

    @Mapping(source = "contentCommentId", target = "contentComment")
    ContentCommentReply toEntity(ContentCommentReplyDTO contentCommentReplyDTO);

    default ContentCommentReply fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContentCommentReply contentCommentReply = new ContentCommentReply();
        contentCommentReply.setId(id);
        return contentCommentReply;
    }
}
