package com.ssvm.school.services;

import com.ssvm.school.constants.FeeType;
import com.ssvm.school.constants.Standards;
import com.ssvm.school.dto.request.FeeStructureRequestDTO;
import com.ssvm.school.dto.response.FeeStructureResponseDTO;
import com.ssvm.school.entity.FeeStructure;
import com.ssvm.school.entity.StudentEnrollment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeeStructureService {

    FeeStructureResponseDTO createFeeStructure(FeeStructureRequestDTO feeStructureRequestDTO);

    List<FeeStructureResponseDTO> findByStatusTrue();

    List<FeeStructure> getAllDistinctClasses();

    List<FeeStructureResponseDTO> getFeeByClass(Standards standard);

    List<FeeStructureResponseDTO> getFeeByType(FeeType feeType);

    List<FeeStructureResponseDTO> getFeeByAcademicYear(String academicYear);

}
