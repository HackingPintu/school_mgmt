package com.ssvm.school.dto.response;

import com.ssvm.school.constants.PaymentMode;
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

    private String receiptNo;
    private LocalDate paymentDate;
    private String studentName;
    private String className;
    private PaymentMode paymentMode;
    private BigDecimal amountPaid;
}
