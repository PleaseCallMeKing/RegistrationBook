package com.tsxy.carl.repository;

import com.tsxy.carl.domain.ConsultRoom;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ConsultRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultRoomRepository extends JpaRepository<ConsultRoom, Long> {

}
