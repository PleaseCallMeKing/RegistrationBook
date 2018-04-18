package com.tsxy.carl.repository;

import com.tsxy.carl.domain.ContentComment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContentComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentCommentRepository extends JpaRepository<ContentComment, Long> {

}
