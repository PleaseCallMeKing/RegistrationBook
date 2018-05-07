package com.tsxy.carl.repository;

import com.tsxy.carl.domain.Doctor;
import com.tsxy.carl.domain.DoctorVisit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the DoctorVisit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorVisitRepository extends JpaRepository<DoctorVisit, Long> {

    List<DoctorVisit> findAllByDoctor(Doctor doctor);

}
