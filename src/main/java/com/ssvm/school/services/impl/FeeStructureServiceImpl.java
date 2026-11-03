package com.ssvm.school.services.impl;

import com.ssvm.school.constants.FeeType;
import com.ssvm.school.constants.Standards;
import com.ssvm.school.dto.request.FeeStructureRequestDTO;
import com.ssvm.school.dto.response.FeeStructureResponseDTO;
import com.ssvm.school.entity.AcademicYear;
import com.ssvm.school.entity.FeeStructure;
import com.ssvm.school.entity.SchoolClass;
import com.ssvm.school.repository.FeeStructureRepository;
import com.ssvm.school.services.AcademicYearService;
import com.ssvm.school.services.FeeStructureService;
import com.ssvm.school.services.SchoolClassService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FeeStructureServiceImpl implements FeeStructureService {

    private final FeeStructureRepository feeStructureRepository;
    private final SchoolClassService schoolClassService;
    private final AcademicYearService academicYearService;

    public FeeStructureServiceImpl(FeeStructureRepository feeStructureRepository,
                                   AcademicYearService academicYearService,
                                   SchoolClassService schoolClassService) {
        this.feeStructureRepository = feeStructureRepository;
        this.schoolClassService = schoolClassService;
        this.academicYearService = academicYearService;
    }

    @Override
    @Transactional
    public FeeStructureResponseDTO createFeeStructure(FeeStructureRequestDTO feeStructureRequestDTO) {

        SchoolClass schoolClass = schoolClassService.findByStandardAndStatusTrue(feeStructureRequestDTO.getClassName());
        AcademicYear academicYear = academicYearService.findByYearLabelAndStatusTrue(feeStructureRequestDTO.getAcademicYear());

        FeeStructure feeStructure = FeeStructure.builder()
                .schoolClass(schoolClass)
                .academicYear(academicYear)
                .feeType(feeStructureRequestDTO.getFeeType())
                .amount(feeStructureRequestDTO.getAmount())
                .status(true)
                .build();
        return getFeeStructureResponseDTO(feeStructureRepository.save(feeStructure));
    }

    @Override
    public List<FeeStructureResponseDTO> findByStatusTrue() {
        return feeStructureRepository.findByStatusIsTrue().stream()
                .map(FeeStructureServiceImpl::getFeeStructureResponseDTO).toList();
    }

    @Override
    public List<FeeStructure> getAllDistinctClasses() {
        return feeStructureRepository.findDistinctSchoolClassByStatusIsTrue();
    }

    @Override
    public List<FeeStructureResponseDTO> getFeeByClass(Standards standard) {

        SchoolClass schoolClass = schoolClassService.findByStandardAndStatusTrue(standard);
        if (schoolClass == null) {
            throw new EntityNotFoundException("Class not found");
        }
        List<FeeStructure> feeList = feeStructureRepository.findBySchoolClassAndStatusIsTrue(schoolClass);

        if (feeList.isEmpty()) {
            throw new EntityNotFoundException("No fee found for class " + schoolClass.getClassName());
        }

        return feeList.stream().map(FeeStructureServiceImpl::getFeeStructureResponseDTO).toList();

    }

    @Override
    public List<FeeStructureResponseDTO> getFeeByType(FeeType feeType) {
        List<FeeStructure> list = feeStructureRepository.findByFeeTypeAndStatusIsTrue(feeType);
        if (list.isEmpty()) {
            throw new EntityNotFoundException("No fee found for type " + feeType);
        }
        return list.stream()
                .map(FeeStructureServiceImpl::getFeeStructureResponseDTO)
                .sorted(Comparator.comparing(FeeStructureResponseDTO::getClassName))
                .toList();
    }

    @Override
    public List<FeeStructureResponseDTO> getFeeByAcademicYear(String academicYear) {
        List<FeeStructure> list = feeStructureRepository.findByAcademicYearAndStatusIsTrue(academicYear);
        if (list.isEmpty()) {
            throw new EntityNotFoundException("No fee found for academic year " + academicYear);
        }
        return list.stream()
                .map(FeeStructureServiceImpl::getFeeStructureResponseDTO)
                .sorted(
                        Comparator.comparing(FeeStructureResponseDTO::getClassName)
                                .thenComparing(FeeStructureResponseDTO::getFeeType)
                )
                .toList();
    }


    private static @NotNull FeeStructureResponseDTO getFeeStructureResponseDTO(FeeStructure feeStructure) {
        FeeStructureResponseDTO feeStructureResponseDTO = new FeeStructureResponseDTO();
        feeStructureResponseDTO.setAmount(feeStructure.getAmount());
        feeStructureResponseDTO.setFeeType(feeStructure.getFeeType());
        feeStructureResponseDTO.setClassName(feeStructure.getSchoolClass().getClassName());
        return feeStructureResponseDTO;
    }
}
