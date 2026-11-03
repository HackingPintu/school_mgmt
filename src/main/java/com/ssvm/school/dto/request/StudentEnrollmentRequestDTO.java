package com.ssvm.school.dto.request;
import com.ssvm.school.constants.Standards;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEnrollmentRequestDTO {

    private String studentId;
    private Standards schoolClass;
    private String academicYear;

}
