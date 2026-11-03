package com.ssvm.school.repository;

import com.ssvm.school.constants.Standards;
import com.ssvm.school.entity.AcademicYear;
import com.ssvm.school.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, String> {
    Optional<SchoolClass> findByClassName(Standards className);

    Optional<SchoolClass> findByClassNameAndStatusTrue(Standards standard);

}
