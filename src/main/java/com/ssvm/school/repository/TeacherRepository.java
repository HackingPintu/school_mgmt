package com.ssvm.school.repository;

import com.ssvm.school.constants.TeacherQualification;
import com.ssvm.school.constants.TeacherType;
import com.ssvm.school.constants.TeachingLevel;
import com.ssvm.school.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {

    List<Teacher> getTeacherByTeachingLevel(TeachingLevel level);

    List<Teacher> getTeacherByTeacherType(TeacherType type);

    List<Teacher> getTeacherByTeacherQualification(TeacherQualification teacherQualification);

    List<Teacher> findByStatusTrue();

    Optional<Teacher> findByIdAndStatusTrue(String id);

}
