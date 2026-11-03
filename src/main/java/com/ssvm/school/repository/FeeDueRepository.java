package com.ssvm.school.repository;

import com.ssvm.school.entity.FeeDue;
import com.ssvm.school.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeeDueRepository extends JpaRepository<FeeDue, String> {

    List<FeeDue> findByEnrollment(StudentEnrollment studentEnrollment);

}
