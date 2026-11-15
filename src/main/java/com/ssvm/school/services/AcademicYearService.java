package com.ssvm.school.services;

import com.ssvm.school.dto.request.AcademicYearRequestDTO;
import com.ssvm.school.dto.response.AcademicYearResponseDTO;
import com.ssvm.school.entity.AcademicYear;

import java.util.List;
import java.util.Optional;

public interface AcademicYearService {

    List<AcademicYear> findAll();

    Optional<AcademicYear> findByYearLabel(String yearLabel);

    void deleteByYearLabel(String yearLabel);

    AcademicYearResponseDTO createAcademicYear(AcademicYearRequestDTO academicYear);

    void updateAcademicYear(AcademicYearRequestDTO academicYear, AcademicYearRequestDTO updatedAcademicYear);

    void updateStatusByYearLabel(AcademicYearRequestDTO academicYear);

    AcademicYear findActiveYear();

    AcademicYear matchYearWithActiveYear(String yearLabel);

}
