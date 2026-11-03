package com.ssvm.school.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO extends BaseEntityRequestDTO {

   private String fatherName;
   private String motherName;
   private String guardianNumber;
   private LocalDate admissionDate;

}
