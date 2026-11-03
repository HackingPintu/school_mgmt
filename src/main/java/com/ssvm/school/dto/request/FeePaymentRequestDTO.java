package com.ssvm.school.dto.request;

import com.ssvm.school.constants.PaymentMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeePaymentRequestDTO {

    @NotNull
    private String dueId;
    @NotNull
    @PositiveOrZero
    private BigDecimal amountPaid;
    @NotNull
    private LocalDate paymentDate;
    @NotNull
    private PaymentMode paymentMode;

}
