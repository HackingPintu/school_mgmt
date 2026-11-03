package com.ssvm.school.repository;

import com.ssvm.school.entity.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, String> {

    Optional<AcademicYear> findByYearLabel(String yearLabel);

    void deleteByYearLabel(String yearLabel);

    Optional<AcademicYear> findByYearLabelAndIsActiveTrue(String yearLabel);

    List<AcademicYear> findByIsActiveTrue();
}
