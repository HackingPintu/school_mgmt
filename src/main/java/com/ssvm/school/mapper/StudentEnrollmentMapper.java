package com.ssvm.school.mapper;

import com.ssvm.school.dto.request.StudentEnrollmentRequestDTO;
import com.ssvm.school.entity.StudentEnrollment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentEnrollmentMapper {

//    void updateStudentEnrollment(StudentEnrollmentRequestDTO dto, @MappingTarget StudentEnrollment studentEnrollment);
}
