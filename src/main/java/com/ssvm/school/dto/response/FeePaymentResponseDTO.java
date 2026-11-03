package com.ssvm.school.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeePaymentResponseDTO {

    private BigDecimal amountPaid;
    private LocalDate paidDate;
    private String receiptNo;
}
