package com.ssvm.school.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String pinCode;
    private String street;
    private String city;
    private String state;
}
