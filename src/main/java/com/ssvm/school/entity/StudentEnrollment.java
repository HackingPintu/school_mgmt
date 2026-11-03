package com.ssvm.school.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "student_enrollments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"class_id", "academic_year_id", "roll_no"}),
                @UniqueConstraint(columnNames = {"student_id", "academic_year_id"})
        })
public class StudentEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Student student;
    @ManyToOne
    private SchoolClass schoolClass;
    @ManyToOne
    private AcademicYear academicYear;
    private LocalDate enrollmentDate;
    private boolean status;
}