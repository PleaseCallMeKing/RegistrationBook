package com.tsxy.carl.repository;

import com.tsxy.carl.domain.ContentCommentReply;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContentCommentReply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentCommentReplyRepository extends JpaRepository<ContentCommentReply, Long> {

}
