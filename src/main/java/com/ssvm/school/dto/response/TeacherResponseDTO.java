package com.ssvm.school.dto.response;

import com.ssvm.school.constants.TeacherQualification;
import com.ssvm.school.constants.TeacherType;
import com.ssvm.school.constants.TeachingLevel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseDTO extends BaseEntityResponseDTO {
    private String email;
    private BigDecimal salary;
    private String phoneNumber;
    private LocalDate dateOfJoining;
    private TeacherType teacherType;
    private TeachingLevel teachingLevel;
    private TeacherQualification teacherQualification;
}
