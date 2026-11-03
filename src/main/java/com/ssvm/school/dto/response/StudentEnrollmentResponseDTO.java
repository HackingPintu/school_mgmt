package com.ssvm.school.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentEnrollmentResponseDTO {


    private String className;
    private String studentName;
    private String academicYear;

}
