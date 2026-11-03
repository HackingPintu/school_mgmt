package com.ssvm.school.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Table(name = "students",
        indexes = {
                @Index(name = "idx_student_created_at", columnList = "created_at"),
                @Index(name = "idx_student_name", columnList = "first_name, last_name")
        })
public class Student extends BaseEntity {

    @Column(name = "father_name", nullable = false, length = 50)
    private String fatherName;

    @Column(name = "mother_name", nullable = false, length = 50)
    private String motherName;

    @Column(name = "guardian_number", nullable = false, length = 10)
    private String guardianNumber;

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

}
