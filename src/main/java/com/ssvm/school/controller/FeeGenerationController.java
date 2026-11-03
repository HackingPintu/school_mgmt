package com.ssvm.school.controller;

import com.ssvm.school.services.FeeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/feegeneration")
public class FeeGenerationController {

    private final FeeGenerationService feeGenerationService;

    public FeeGenerationController(FeeGenerationService feeGenerationService) {
        this.feeGenerationService = feeGenerationService;
    }

    @GetMapping
    public ResponseEntity<BigDecimal> generateMonthlyFees() {
        return ResponseEntity.ok(feeGenerationService.generateMonthlyFees());
    }
}
