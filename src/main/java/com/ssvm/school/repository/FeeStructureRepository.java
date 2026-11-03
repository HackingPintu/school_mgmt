package com.ssvm.school.repository;

import com.ssvm.school.constants.FeeType;
import com.ssvm.school.entity.FeeStructure;
import com.ssvm.school.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeeStructureRepository extends JpaRepository<FeeStructure, String> {

    List<FeeStructure> findByStatusIsTrue();

    List<FeeStructure> findDistinctSchoolClassByStatusIsTrue();

    List<FeeStructure> findBySchoolClassAndStatusIsTrue(SchoolClass schoolClass);

    List<FeeStructure> findByFeeTypeAndStatusIsTrue(FeeType feeType);

    @Query("SELECT f FROM FeeStructure f WHERE f.academicYear.yearLabel = :academicYear")
    List<FeeStructure> findByAcademicYearAndStatusIsTrue(@Param("academicYear") String academicYear);


}
