package com.ssvm.school.controller;

import com.ssvm.school.dto.request.AcademicYearRequestDTO;
import com.ssvm.school.dto.response.AcademicYearResponseDTO;
import com.ssvm.school.entity.AcademicYear;
import com.ssvm.school.services.impl.AcademicYearServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${endpoint.academic-year:academicyear}")
@Validated
@Slf4j
public class AcademicYearController {

    private final AcademicYearServiceImpl academicYearService;

    public AcademicYearController(AcademicYearServiceImpl academicYearService) {
        this.academicYearService = academicYearService;
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AcademicYearResponseDTO>> getAcademicYears() {
        List<AcademicYear> academicYearEntities = academicYearService.findAll();

        List<AcademicYearResponseDTO> academicYearResponseDTOs = academicYearEntities.stream()
                .map(academicYear -> AcademicYearResponseDTO.builder()
                        .academicYearName(academicYear.getYearLabel())
                        .isActive(academicYear.isActive())
                        .build())
                .toList();

        return ResponseEntity.ok(academicYearResponseDTOs);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AcademicYearResponseDTO> createAcademicYear(@Valid @RequestBody AcademicYearRequestDTO academicYear) {
        AcademicYearResponseDTO academicYearResponse = academicYearService.createAcademicYear(academicYear);
        return new ResponseEntity<>(academicYearResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{year_label}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByAcademicYear(
            @PathVariable("year_label")
            @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "Invalid format. Expected YYYY-YY (e.g., 2025-26)")
            String yearLabel
    ) {
        academicYearService.deleteByYearLabel(yearLabel.trim());
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateAcademicYear(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "Invalid current year format") String currentYearLabel,
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "Invalid new year format") String newYearLabel) {

        AcademicYearRequestDTO currentDto = new AcademicYearRequestDTO();
        currentDto.setYearLabel(currentYearLabel);

        AcademicYearRequestDTO updatedDto = new AcademicYearRequestDTO();
        updatedDto.setYearLabel(newYearLabel);

        academicYearService.updateAcademicYear(currentDto, updatedDto);
        return ResponseEntity.ok("Updated to : " + newYearLabel);
    }


    @PatchMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> setActiveYear(@RequestBody AcademicYearRequestDTO academicYear) {
        academicYearService.updateStatusByYearLabel(academicYear);
        return ResponseEntity.ok("Active year has been updated to " + academicYear.getYearLabel());
    }
}
