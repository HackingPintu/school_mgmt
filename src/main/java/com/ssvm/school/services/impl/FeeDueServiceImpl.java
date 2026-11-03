package com.ssvm.school.services.impl;

import com.ssvm.school.constants.FeeStatus;
import com.ssvm.school.constants.FeeType;
import com.ssvm.school.dto.response.FeeDueResponseDTO;
import com.ssvm.school.dto.response.FeeStructureResponseDTO;
import com.ssvm.school.entity.FeeDue;
import com.ssvm.school.entity.StudentEnrollment;
import com.ssvm.school.repository.FeeDueRepository;
import com.ssvm.school.services.FeeDueService;
import com.ssvm.school.services.FeeStructureService;
import com.ssvm.school.services.StudentEnrollmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FeeDueServiceImpl implements FeeDueService {

    private final FeeDueRepository feeDueRepository;
    private final StudentEnrollmentService studentEnrollmentService;
    private final FeeStructureService feeStructureService;

    public FeeDueServiceImpl(FeeDueRepository feeDueRepository,
                             StudentEnrollmentService studentEnrollmentService,
                             FeeStructureService feeStructureService) {
        this.feeDueRepository = feeDueRepository;
        this.studentEnrollmentService = studentEnrollmentService;
        this.feeStructureService = feeStructureService;
    }


    @Override
    @Transactional
    public void createFeeDueInitial(String enrollmentId) {
        StudentEnrollment enrollment = studentEnrollmentService.findById(enrollmentId);
        List<FeeStructureResponseDTO> feeStructures = feeStructureService.getFeeByClass(enrollment.getSchoolClass().getClassName());

        List<FeeDue> feeDuesToSave = feeStructures.stream()
                .filter(dto -> dto.getFeeType() != FeeType.MONTHLY)
                .map(dto -> FeeDue.builder()
                        .enrollment(enrollment)
                        .amountDue(dto.getAmount())
                        .feeType(dto.getFeeType())
                        .amountPaid(BigDecimal.ZERO)
                        .status(FeeStatus.UNPAID)
                        .build()
                )
                .toList();

        if (!feeDuesToSave.isEmpty()) {
            feeDueRepository.saveAll(feeDuesToSave);
        }
    }


    @Override
    public FeeDue findById(String id) {
        return feeDueRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No FeeDue with id " + id));
    }

    @Override
    @Transactional
    public void updateFeeDue(String id, BigDecimal amount) {
        FeeDue feeDue = findById(id);
        feeDue.setAmountDue(feeDue.getAmountDue().subtract(amount));
        if (feeDue.getAmountDue().equals(BigDecimal.ZERO)) {
            feeDue.setStatus(FeeStatus.PAID);
        } else {
            feeDue.setStatus(FeeStatus.PARTIAL_PAID);
        }
        feeDue.setAmountPaid(feeDue.getAmountPaid().add(amount));
        feeDueRepository.save(feeDue);
    }

    @Override
    public List<FeeDue> getAllFeeDue() {
        return feeDueRepository.findAll();
    }

    @Override
    public List<FeeDue> getAllByStudentEnrollment(String enrollmentId) {
        StudentEnrollment enrollment = studentEnrollmentService.findById(enrollmentId);
        return feeDueRepository.findByEnrollment(enrollment);
    }

    @Override
    public FeeDueResponseDTO createFeeDue(StudentEnrollment enrollment, FeeType feeType, BigDecimal amountDue) {
        String studentName = enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName();
        FeeDue feeDue = FeeDue.builder()
                .enrollment(enrollment)
                .amountDue(amountDue)
                .feeType(feeType)
                .amountPaid(BigDecimal.ZERO)
                .status(FeeStatus.UNPAID)
                .build();
        return getFeeDueResponseDTO(feeDueRepository.save(feeDue),
                enrollment.getSchoolClass().getClassName().toString(), studentName);
    }

    private static @NotNull FeeDueResponseDTO getFeeDueResponseDTO(FeeDue feeDue, String className, String studentName) {

        return FeeDueResponseDTO.builder()
                .studentName(studentName)
                .className(className)
                .amountDue(feeDue.getAmountDue())
                .status(feeDue.getStatus())
                .amountPaid(feeDue.getAmountPaid())
                .date(LocalDate.now())
                .status(feeDue.getStatus())
                .build();
    }
}
