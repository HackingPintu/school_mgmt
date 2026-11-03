package com.ssvm.school.services;

import com.ssvm.school.dto.request.StudentRequestDTO;
import com.ssvm.school.dto.response.StudentResponseDTO;
import com.ssvm.school.entity.Student;
import org.springframework.data.domain.Page;


public interface StudentService {
    StudentResponseDTO createStudent(StudentRequestDTO student);
    Page<StudentResponseDTO> getPaginatedStudents(int page, int size, String sortBy, String direction);
    void deleteStudent(String id);
    StudentResponseDTO updateStudent(String id, StudentRequestDTO student);
    Student findByIdAndStatusTrue(String id);
}