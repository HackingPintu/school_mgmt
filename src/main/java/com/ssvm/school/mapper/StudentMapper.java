package com.ssvm.school.mapper;

import com.ssvm.school.dto.request.StudentRequestDTO;
import com.ssvm.school.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StudentMapper {
    void updateStudent(StudentRequestDTO studentDTO, @MappingTarget Student student);
}
