package com.ssvm.school.controller;

import com.ssvm.school.constants.FeeType;
import com.ssvm.school.constants.Standards;
import com.ssvm.school.dto.request.FeeStructureRequestDTO;
import com.ssvm.school.dto.response.FeeStructureResponseDTO;
import com.ssvm.school.services.FeeStructureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${endpoint.feestructure:feestructure}")
public class FeeStructureController {

    private final FeeStructureService feeStructureService;

    public FeeStructureController(FeeStructureService feeStructureService) {
        this.feeStructureService = feeStructureService;
    }

    @PostMapping("/create")
    public ResponseEntity<FeeStructureResponseDTO> createFeeStructure(@RequestBody FeeStructureRequestDTO feeStructureRequestDTO) {
        return ResponseEntity.ok(feeStructureService.createFeeStructure(feeStructureRequestDTO));
    }

    @GetMapping("/get/byClass")
    public ResponseEntity<List<FeeStructureResponseDTO>> getFeeStructureByClass(@RequestParam Standards standard) {
        return ResponseEntity.ok(feeStructureService.getFeeByClass(standard));
    }

    @GetMapping("/get/byType")
    public ResponseEntity<List<FeeStructureResponseDTO>> getFeeStructureByClass(@RequestParam FeeType feeType) {
        return ResponseEntity.ok(feeStructureService.getFeeByType(feeType));
    }

    @GetMapping("/get/byYear")
    public ResponseEntity<List<FeeStructureResponseDTO>> getFeeStructureByAcademicYear(@RequestParam String academicYear) {
        return ResponseEntity.ok(feeStructureService.getFeeByAcademicYear(academicYear));
    }


}
