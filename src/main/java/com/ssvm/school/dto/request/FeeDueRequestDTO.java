package com.ssvm.school.dto.request;

import com.ssvm.school.constants.FeeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeDueRequestDTO {

    @NotBlank(message = "Enrollment ID is required")
    private String enrollmentId;

    @NotNull(message = "Fee type is required")
    private FeeType feeType;
}