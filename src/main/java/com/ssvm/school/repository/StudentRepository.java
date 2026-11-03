package com.ssvm.school.repository;

import com.ssvm.school.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Page<Student> findByStatusTrue(Pageable pageable);


    Optional<Student> findByIdAndStatusTrue(String id);

}
