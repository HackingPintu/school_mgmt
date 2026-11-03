package com.ssvm.school.entity;

import com.ssvm.school.constants.FeeStatus;
import com.ssvm.school.constants.FeeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fee_due")
public class FeeDue {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @NotNull
    private StudentEnrollment enrollment;

    private FeeType feeType;
    private BigDecimal amountDue;
    private BigDecimal amountPaid;
    @Enumerated(EnumType.STRING)
    private FeeStatus status;
}