package com.tsxy.carl.service.mapper;

import com.tsxy.carl.domain.*;
import com.tsxy.carl.service.dto.ContentCommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContentComment and its DTO ContentCommentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContentCommentMapper extends EntityMapper<ContentCommentDTO, ContentComment> {

    

    @Mapping(target = "replys", ignore = true)
    ContentComment toEntity(ContentCommentDTO contentCommentDTO);

    default ContentComment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContentComment contentComment = new ContentComment();
        contentComment.setId(id);
        return contentComment;
    }
}
