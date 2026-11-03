package com.ssvm.school.services;

import com.ssvm.school.constants.TeacherQualification;
import com.ssvm.school.constants.TeacherType;
import com.ssvm.school.constants.TeachingLevel;
import com.ssvm.school.dto.request.TeacherRequestDTO;
import com.ssvm.school.dto.response.TeacherResponseDTO;
import com.ssvm.school.entity.Teacher;

import java.util.List;

public interface TeacherService {

    TeacherResponseDTO createTeacher(TeacherRequestDTO teacherRequestDTO);

    TeacherResponseDTO updateTeacher(String id, TeacherRequestDTO teacherRequestDTO);

    void deleteTeacher(String id);

    List<TeacherResponseDTO> getTeacherByType(TeacherType type);

    List<TeacherResponseDTO> getTeacherByQualification(TeacherQualification teacherQualification);

    List<TeacherResponseDTO> getTeacherByLevel(TeachingLevel level);

    Teacher findById(String id);

    List<TeacherResponseDTO> getAllActiveTeachers();
}
