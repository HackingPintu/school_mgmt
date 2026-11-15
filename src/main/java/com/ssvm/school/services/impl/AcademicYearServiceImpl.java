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
    @Transactional
    public void updateAcademicYear(AcademicYearRequestDTO academicYear, AcademicYearRequestDTO updatedAcademicYear) {
        log.info("Updating academic year {}.", updatedAcademicYear.getYearLabel());
        log.info("Academic year {}.", academicYear.getYearLabel());
        Optional<AcademicYear> existingAcademicYear = this.findByYearLabel(academicYear.getYearLabel());
        if (existingAcademicYear.isEmpty()) {
            throw new EntityNotFoundException("Academic year not found");
        }
        if (findByYearLabel(updatedAcademicYear.getYearLabel()).isPresent()) {
            throw new IllegalArgumentException("Cannot update to already existing academic year");
        }
        existingAcademicYear.get().setYearLabel(updatedAcademicYear.getYearLabel());
        academicYearRepository.save(existingAcademicYear.get());

    }

    @Override
    @Transactional
    public void updateStatusByYearLabel(AcademicYearRequestDTO academicYear) {
        Optional<AcademicYear> existingAcademicYear = this.findByYearLabel(academicYear.getYearLabel());
        if (existingAcademicYear.isEmpty()) {
            throw new IllegalArgumentException("Academic year not found");
        }
        AcademicYear activeYear = findActiveYear();
        if (activeYear == null) {
            existingAcademicYear.get().setActive(true);
            return;
        }
        if (activeYear.getYearLabel().equals(academicYear.getYearLabel())) {
            throw new IllegalArgumentException("Academic year already active");
        }
        existingAcademicYear.get().setActive(true);
        activeYear.setActive(false);
        academicYearRepository.save(activeYear);
    }


    @Override
    public AcademicYear findActiveYear() {
        return academicYearRepository.findByIsActiveTrue();
    }

    @Override
    public AcademicYear matchYearWithActiveYear(String yearLabel) {
        AcademicYear academicYear = findActiveYear();
        if (!academicYear.getYearLabel().equals(yearLabel)) {
            throw new IllegalStateException("Academic year is not active. \n Current academic year: " + academicYear.getYearLabel());
        }
        return academicYear;
    }
}
