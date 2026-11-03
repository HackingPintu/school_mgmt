package com.ssvm.school.dto.request;


import com.ssvm.school.constants.FeeType;
import com.ssvm.school.constants.Standards;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FeeStructureRequestDTO {

    private Standards className;
    private String academicYear;
    private FeeType feeType;
    private BigDecimal amount;

}
