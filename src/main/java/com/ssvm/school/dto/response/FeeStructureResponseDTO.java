package com.ssvm.school.dto.response;

import com.ssvm.school.constants.FeeType;
import com.ssvm.school.constants.Standards;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class FeeStructureResponseDTO {

    private BigDecimal amount;
    private Standards className;
    private FeeType feeType;

}
