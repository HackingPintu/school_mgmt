package com.ssvm.school.dto.response;

import com.ssvm.school.constants.FeeStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeDueResponseDTO {

    private String studentName;
    private String className;
    private LocalDate date;
    private BigDecimal amountPaid;
    private BigDecimal amountDue;
    private FeeStatus status;


}
