package com.ssvm.school.entity;

import com.ssvm.school.constants.PaymentMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fee_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private FeeDue feeDue;
    @NotNull
    private BigDecimal amountPaid;
    @NotNull
    private LocalDate paymentDate;
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    private String receiptNo;

}