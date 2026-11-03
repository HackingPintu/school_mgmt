package com.ssvm.school.dto.request;

import com.ssvm.school.constants.Standards;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolClassRequestDTO {

    private Standards className;
    private String teacherId;
}
