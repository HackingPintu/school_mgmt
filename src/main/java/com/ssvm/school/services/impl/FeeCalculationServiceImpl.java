package com.ssvm.school.services.impl;


import com.ssvm.school.dto.response.FeePaymentResponseDTO;
import com.ssvm.school.entity.FeeDue;
import com.ssvm.school.services.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Slf4j
public class FeeCalculationServiceImpl implements FeeCalculationService {

    private final FeeDueService feeDueService;
    private final FeePaymentService feePaymentService;

    public FeeCalculationServiceImpl(FeeDueService feeDueService
            , FeePaymentService feePaymentService) {
        this.feeDueService = feeDueService;
        this.feePaymentService = feePaymentService;
    }

    @Override
    public BigDecimal calculatePendingFee() {
        List<FeeDue> feeDues = feeDueService.getAllFeeDue();

        return feeDues.stream()
                .map(FeeDue::getAmountDue)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculatePendingFeeForStudent(String enrollmentId) {
        List<FeeDue> feeDues = feeDueService.getAllByStudentEnrollment(enrollmentId);

        return feeDues.stream()
                .map(FeeDue::getAmountDue)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<FeePaymentResponseDTO> calculatePaidFeeForStudent(String enrollmentId) {
        List<FeeDue> feeDues = feeDueService.getAllByStudentEnrollment(enrollmentId);
        if (feeDues.isEmpty()) {
            throw new EntityNotFoundException("Fee Due not found for enrollment " + enrollmentId);
        }
        return feeDues.stream()
                .map(feePaymentService::findByFeeDue)
                .flatMap(Collection::stream)
                .toList();

    }

}
