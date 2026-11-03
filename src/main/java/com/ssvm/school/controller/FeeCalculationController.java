package com.ssvm.school.controller;

import com.ssvm.school.dto.response.FeePaymentResponseDTO;
import com.ssvm.school.services.FeeCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/${endpoint.feecalculation:feecalculation}")
public class FeeCalculationController {

    private final FeeCalculationService feeCalculationService;

    public FeeCalculationController(FeeCalculationService feeCalculationService) {
        this.feeCalculationService = feeCalculationService;
    }

    @GetMapping("/get/pending")
    public ResponseEntity<BigDecimal> getPendingFees() {
        return ResponseEntity.ok(feeCalculationService.calculatePendingFee());
    }

    @GetMapping("/get/pending/byStudent/{enrollmentId}")
    public ResponseEntity<BigDecimal> getPendingFeesByStudent(@PathVariable String enrollmentId) {
        return ResponseEntity.ok(feeCalculationService.calculatePendingFeeForStudent(enrollmentId));
    }

    @GetMapping("/get/paid/byStudent/{enrollmentId}")
    public ResponseEntity<List<FeePaymentResponseDTO>> getPaidFeesByStudent(@PathVariable String enrollmentId) {
        return ResponseEntity.ok(feeCalculationService.calculatePaidFeeForStudent(enrollmentId));
    }


}
