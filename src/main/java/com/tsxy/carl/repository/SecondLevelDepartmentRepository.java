package com.tsxy.carl.repository;

import com.tsxy.carl.domain.SecondLevelDepartment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SecondLevelDepartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecondLevelDepartmentRepository extends JpaRepository<SecondLevelDepartment, Long> {

}
