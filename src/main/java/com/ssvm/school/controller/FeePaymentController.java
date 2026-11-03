package com.ssvm.school.controller;

import com.ssvm.school.dto.request.FeePaymentRequestDTO;
import com.ssvm.school.dto.response.FeePaymentResponseDTO;
import com.ssvm.school.services.FeePaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@Validated
public class FeePaymentController {

    private final FeePaymentService feePaymentService;

    public FeePaymentController(FeePaymentService feePaymentService) {
        this.feePaymentService = feePaymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<FeePaymentResponseDTO> create(@Valid @RequestBody FeePaymentRequestDTO feePaymentRequestDTO) {
        return ResponseEntity.ok(feePaymentService.payFee(feePaymentRequestDTO));
    }
}
