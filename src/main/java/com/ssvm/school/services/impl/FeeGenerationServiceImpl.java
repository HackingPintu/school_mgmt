package com.ssvm.school.services.impl;

import com.ssvm.school.constants.FeeType;
import com.ssvm.school.dto.response.FeeStructureResponseDTO;
import com.ssvm.school.entity.StudentEnrollment;
import com.ssvm.school.services.FeeDueService;
import com.ssvm.school.services.FeeGenerationService;
import com.ssvm.school.services.FeeStructureService;
import com.ssvm.school.services.StudentEnrollmentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FeeGenerationServiceImpl implements FeeGenerationService {

    private final StudentEnrollmentService studentEnrollmentService;
    private final FeeStructureService feeStructureService;
    private final FeeDueService feeDueService;

    public FeeGenerationServiceImpl(StudentEnrollmentService studentEnrollmentService,
                                    FeeStructureService feeStructureService,
                                    FeeDueService feeDueService) {
        this.studentEnrollmentService = studentEnrollmentService;
        this.feeStructureService = feeStructureService;
        this.feeDueService = feeDueService;
    }


    @Override
    public BigDecimal generateMonthlyFees() {

        List<StudentEnrollment> studentEnrollments = studentEnrollmentService.findAll();

        return studentEnrollments.stream()
                .flatMap(studentEnrollment -> {
                    List<FeeStructureResponseDTO> fees = feeStructureService.getFeeByClass(
                            studentEnrollment.getSchoolClass().getClassName()
                    );
                    return fees.stream()
                            .filter(fee -> fee.getFeeType().equals(FeeType.MONTHLY))
                            .map(fee -> {
                                feeDueService.createFeeDue(studentEnrollment, fee.getFeeType(), fee.getAmount());
                                return fee.getAmount();
                            });
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
