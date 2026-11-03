package com.ssvm.school.services;

import com.ssvm.school.constants.Standards;
import com.ssvm.school.dto.request.StudentEnrollmentRequestDTO;
import com.ssvm.school.dto.response.StudentEnrollmentResponseDTO;
import com.ssvm.school.entity.StudentEnrollment;


import java.util.List;

public interface StudentEnrollmentService {

    StudentEnrollmentResponseDTO enrollment(StudentEnrollmentRequestDTO enrollment);

    List<StudentEnrollmentResponseDTO> findBySchoolClass(Standards standards);

    List<StudentEnrollmentResponseDTO> findByAcademicYear(String yearLabel);

    List<StudentEnrollment> findByStatusTrue();

    StudentEnrollment findById(String id);

    List<StudentEnrollment> findAll();
}
