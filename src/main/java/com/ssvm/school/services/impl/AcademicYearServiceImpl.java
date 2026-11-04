package com.ssvm.school.services.impl;

import com.ssvm.school.dto.request.AcademicYearRequestDTO;
import com.ssvm.school.dto.response.AcademicYearResponseDTO;
import com.ssvm.school.entity.AcademicYear;
import com.ssvm.school.repository.AcademicYearRepository;
import com.ssvm.school.services.AcademicYearService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    @Override
    public List<AcademicYear> findAll() {
        List<AcademicYear> academicYears = academicYearRepository.findAll();
        if (academicYears.isEmpty()) {
            throw new NoSuchElementException("No academic years found");
        }
        return academicYears;
    }

    @Override
    public Optional<AcademicYear> findByYearLabel(String yearLabel) {
        return academicYearRepository.findByYearLabel(yearLabel);

    }

    @Transactional
    @Override
    public void deleteByYearLabel(String yearLabel) {
        boolean exists = academicYearRepository.findByYearLabel(yearLabel).isPresent();
        if (!exists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Academic year '" + yearLabel + "' not found."
            );
        }
        academicYearRepository.deleteByYearLabel(yearLabel);
        log.info("Deleted academic year {}.", yearLabel);
    }

    @Transactional
    @Override
    public AcademicYearResponseDTO createAcademicYear(AcademicYearRequestDTO academicYear) {
        Optional<AcademicYear> existingAcademicYear = findByYearLabel(academicYear.getYearLabel());
        if (existingAcademicYear.isPresent()) {
            throw new EntityExistsException("Academic year already exists");
        }
        AcademicYear academicYearEntity = AcademicYear.builder()
                .yearLabel(academicYear.getYearLabel())
                .isActive(false)
                .build();
        String yearLabel = academicYearRepository.save(academicYearEntity).getYearLabel();
        return AcademicYearResponseDTO.builder().academicYearName(yearLabel).build();
    }

    @Override
    public void updateAcademicYear(AcademicYearRequestDTO academicYear) {
        Optional<AcademicYear> existingAcademicYear = this.findByYearLabel(academicYear.getYearLabel());
        existingAcademicYear.ifPresent(year -> year.setYearLabel(academicYear.getYearLabel()));
    }

    @Override
    public void updateStatusByYearLabel(AcademicYearRequestDTO academicYear, boolean status) {
        Optional<AcademicYear> existingAcademicYear = this.findByYearLabel(academicYear.getYearLabel());
        existingAcademicYear.ifPresent(year -> year.setActive(status));
    }

    @Override
    public AcademicYear findByYearLabelAndStatusTrue(String yearLabel) {
        return academicYearRepository.findByYearLabelAndIsActiveTrue(yearLabel).orElseThrow(() -> new EntityNotFoundException("No academic year found"));
    }

    @Override
    public List<AcademicYear> findByIsActiveTrue() {
        return academicYearRepository.findByIsActiveTrue();
    }
}
