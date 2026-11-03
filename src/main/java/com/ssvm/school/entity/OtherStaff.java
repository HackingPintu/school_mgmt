package com.ssvm.school.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "others_staff")
public class OtherStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 10)
    private String id;

    private String name;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    private BigDecimal salary;

}
