package com.ssvm.school.services;

import com.ssvm.school.constants.FeeType;
import com.ssvm.school.dto.response.FeeDueResponseDTO;
import com.ssvm.school.entity.FeeDue;
import com.ssvm.school.entity.Student;
import com.ssvm.school.entity.StudentEnrollment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public interface FeeDueService {

    void createFeeDueInitial(String enrollmentId);

    FeeDue findById(String id);

    void updateFeeDue(String id, BigDecimal amount);

    List<FeeDue> getAllFeeDue();

    List<FeeDue> getAllByStudentEnrollment(String enrollmentId);

    FeeDueResponseDTO createFeeDue(StudentEnrollment enrollment, FeeType feeType, BigDecimal amount);


}
