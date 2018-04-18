package com.tsxy.carl.repository;

import com.tsxy.carl.domain.ThirdLevelDepartment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the ThirdLevelDepartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThirdLevelDepartmentRepository extends JpaRepository<ThirdLevelDepartment, Long> {
    @Query("select distinct third_level_department from ThirdLevelDepartment third_level_department left join fetch third_level_department.doctors left join fetch third_level_department.secondLevelDepts")
    List<ThirdLevelDepartment> findAllWithEagerRelationships();

    @Query("select third_level_department from ThirdLevelDepartment third_level_department left join fetch third_level_department.doctors left join fetch third_level_department.secondLevelDepts where third_level_department.id =:id")
    ThirdLevelDepartment findOneWithEagerRelationships(@Param("id") Long id);

}
