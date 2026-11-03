package com.ssvm.school.services.impl;

import com.ssvm.school.constants.Standards;
import com.ssvm.school.dto.request.SchoolClassRequestDTO;
import com.ssvm.school.dto.response.SchoolClassResponseDTO;
import com.ssvm.school.entity.SchoolClass;
import com.ssvm.school.entity.Teacher;
import com.ssvm.school.repository.SchoolClassRepository;
import com.ssvm.school.services.SchoolClassService;
import com.ssvm.school.services.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final TeacherService teacherService;

    public SchoolClassServiceImpl(SchoolClassRepository schoolClassRepository, TeacherService teacherService) {
        this.schoolClassRepository = schoolClassRepository;
        this.teacherService = teacherService;
    }

    @Transactional
    @Override
    public SchoolClassResponseDTO create(SchoolClassRequestDTO schoolClassRequestDTO) {
        Teacher teacher = teacherService.findById(schoolClassRequestDTO.getTeacherId());
        if (!teacher.isStatus()) throw new EntityNotFoundException("Teacher not found");
        if (classExists(schoolClassRequestDTO.getClassName()))
            throw new IllegalArgumentException("Class already exists");
        SchoolClass schoolClass = SchoolClass.builder()
                .className(schoolClassRequestDTO.getClassName())
                .status(true)
                .classTeacher(teacher)
                .build();
        return getSchoolClassResponseDTO(schoolClassRepository.save(schoolClass));

    }

    @Transactional
    @Override
    public SchoolClassResponseDTO update(SchoolClassRequestDTO schoolClassRequestDTO) {

        Teacher teacher = teacherService.findById(schoolClassRequestDTO.getTeacherId());
        if (!teacher.isStatus()) {
            throw new IllegalArgumentException("Teacher not found");
        }
        Optional<SchoolClass> schoolClass = findByStandards(schoolClassRequestDTO.getClassName());
        if (schoolClass.isEmpty()) {
            throw new IllegalArgumentException("Class does not exist");
        }
        schoolClass.get().setClassTeacher(teacher);
        SchoolClass updatedClass = schoolClassRepository.save(schoolClass.get());
        String teacherName = teacher.getFirstName() + " " + teacher.getLastName();

        return SchoolClassResponseDTO.builder()
                .classTeacher(teacherName)
                .standard(updatedClass.getClassName())
                .build();
    }

    @Override
    public void delete(String id) {
        Optional<SchoolClass> schoolClassOptional = schoolClassRepository.findById(id);
        if (schoolClassOptional.isPresent()) {
            schoolClassRepository.deleteById(id);
            log.info("Deleted class {}", id);
        } else {
            throw new IllegalArgumentException("Class does not exist");
        }
    }

    @Override
    public List<SchoolClassResponseDTO> findAll() {
        List<SchoolClass> schoolClassList = schoolClassRepository.findAll();
        return schoolClassList.stream()
                .map((SchoolClassServiceImpl::getSchoolClassResponseDTO))
                .sorted(Comparator.comparing(SchoolClassResponseDTO::getStandard))
                .toList();
    }

    @Override
    public Optional<SchoolClass> findByStandards(Standards standards) {
        return schoolClassRepository.findByClassName(standards);
    }

    @Override
    public SchoolClass findByStandardAndStatusTrue(Standards standard) {
        return schoolClassRepository.findByClassNameAndStatusTrue(standard).orElseThrow(() -> new EntityNotFoundException("Class not found"));
    }


    private static @NotNull SchoolClassResponseDTO getSchoolClassResponseDTO(SchoolClass schoolClass) {
        SchoolClassResponseDTO responseDTO = new SchoolClassResponseDTO();
        String teacherName = schoolClass.getClassTeacher().getFirstName() + " " + schoolClass.getClassTeacher().getLastName();
        responseDTO.setStandard(schoolClass.getClassName());
        responseDTO.setClassTeacher(teacherName);
        return responseDTO;
    }

    private boolean classExists(Standards standards) {
        return findByStandards(standards).isPresent();
    }

}
