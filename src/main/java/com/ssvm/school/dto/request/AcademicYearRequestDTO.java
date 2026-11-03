package com.ssvm.school.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademicYearRequestDTO {

    @Pattern(regexp = "^\\d{4}-\\d{2}$")
    private String yearLabel;
}
