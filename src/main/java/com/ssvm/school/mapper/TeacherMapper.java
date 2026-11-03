package com.ssvm.school.mapper;

import com.ssvm.school.dto.request.TeacherRequestDTO;
import com.ssvm.school.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeacherMapper {

    void updateTeacher(TeacherRequestDTO teacherDTO, @MappingTarget Teacher teacher);

}
