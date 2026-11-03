package com.ssvm.school.entity;

import com.ssvm.school.constants.FeeType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "fee_structure",
        uniqueConstraints = @UniqueConstraint(columnNames = {"school_class_id", "academic_year_id", "fee_type"}))
public class FeeStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private SchoolClass schoolClass;
    @ManyToOne
    private AcademicYear academicYear;
    @Enumerated(EnumType.STRING)
    private FeeType feeType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    private boolean status = true;
}