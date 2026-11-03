package com.ssvm.school.repository;

import com.ssvm.school.entity.AcademicYear;
import com.ssvm.school.entity.SchoolClass;
import com.ssvm.school.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, String> {

    List<StudentEnrollment> findAllByStatusIsTrue();

    List<StudentEnrollment> findBySchoolClassAndStatusTrue(SchoolClass schoolClass);

    List<StudentEnrollment> findByAcademicYearAndStatusTrue(AcademicYear academicYear);

    List<StudentEnrollment> findByStatusIsTrue();

//    List<StudentEnrollment> findBySchoolClassAndAcademicYearAndStatusTrue(SchoolClass schoolClass, AcademicYear academicYear);

}
