package com.ssvm.school.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO extends BaseEntityResponseDTO {

    private String fatherName;
    private String motherName;
    private String guardianNumber;
    private LocalDate admissionDate;
}
