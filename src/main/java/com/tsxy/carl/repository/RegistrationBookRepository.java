package com.tsxy.carl.repository;

import com.tsxy.carl.domain.RegistrationBook;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RegistrationBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistrationBookRepository extends JpaRepository<RegistrationBook, Long> {

}
