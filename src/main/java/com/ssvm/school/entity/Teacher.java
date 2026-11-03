package com.ssvm.school.entity;

import com.ssvm.school.constants.TeacherQualification;
import com.ssvm.school.constants.TeacherType;
import com.ssvm.school.constants.TeachingLevel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends BaseEntity {

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "join_date", nullable = false)
    private LocalDate dateOfJoining;

    @Enumerated(EnumType.STRING)
    @Column(name = "teacher_type", nullable = false)
    private TeacherType teacherType;

    @Enumerated(EnumType.STRING)
    @Column(name = "teaching_level", nullable = false)
    private TeachingLevel teachingLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "teaching_qualification", nullable = false)
    private TeacherQualification teacherQualification;

}
