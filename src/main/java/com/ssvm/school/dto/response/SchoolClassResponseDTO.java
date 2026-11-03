package com.ssvm.school.dto.response;

import com.ssvm.school.constants.Standards;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassResponseDTO {

    private Standards standard;
    private String classTeacher;


}
