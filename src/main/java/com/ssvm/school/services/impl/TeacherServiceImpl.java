package com.ssvm.school.services.impl;

import com.ssvm.school.constants.TeacherQualification;
import com.ssvm.school.constants.TeacherType;
import com.ssvm.school.constants.TeachingLevel;
import com.ssvm.school.dto.request.TeacherRequestDTO;
import com.ssvm.school.dto.response.TeacherResponseDTO;
import com.ssvm.school.entity.Address;
import com.ssvm.school.entity.Teacher;
import com.ssvm.school.mapper.TeacherMapper;
import com.ssvm.school.repository.TeacherRepository;
import com.ssvm.school.services.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    @Transactional
    public TeacherResponseDTO createTeacher(TeacherRequestDTO teacherRequestDTO) {
        Teacher teacher = Teacher.builder()
                .firstName(teacherRequestDTO.getFirstName())
                .middleName(teacherRequestDTO.getMiddleName())
                .lastName(teacherRequestDTO.getLastName())
                .aadhaarNumber(teacherRequestDTO.getAadhaarNumber())
                .dateOfBirth(teacherRequestDTO.getDateOfBirth())
                .gender(teacherRequestDTO.getGender())
                .bloodGroup(teacherRequestDTO.getBloodGroup())
                .address(Address.builder()
                        .street(teacherRequestDTO.getStreet())
                        .city(teacherRequestDTO.getCity())
                        .pinCode(teacherRequestDTO.getPinCode())
                        .state(teacherRequestDTO.getState())
                        .build())
                .status(teacherRequestDTO.isStatus())
                .email(teacherRequestDTO.getEmail())
                .salary(teacherRequestDTO.getSalary())
                .phoneNumber(teacherRequestDTO.getPhoneNumber())
                .dateOfJoining(teacherRequestDTO.getDateOfJoining())
                .teacherType(teacherRequestDTO.getTeacherType())
                .teachingLevel(teacherRequestDTO.getTeachingLevel())
                .teacherQualification(teacherRequestDTO.getTeacherQualification())
                .build();

        Teacher savedTeacher = teacherRepository.save(teacher);

        return getResponseDTO(savedTeacher);
    }

    @Override
    @Transactional
    public TeacherResponseDTO updateTeacher(String id, TeacherRequestDTO teacherRequestDTO) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        teacherMapper.updateTeacher(teacherRequestDTO, teacher);
        return getResponseDTO(teacherRepository.save(teacher));
    }

    @Override
    public void deleteTeacher(String id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        if (teacher.isStatus()) {
            teacher.setStatus(false);
            teacherRepository.save(teacher);
        } else throw new EntityNotFoundException("Teacher not found");
    }

    @Override
    public List<TeacherResponseDTO> getTeacherByType(TeacherType type) {
        List<Teacher> list = teacherRepository.getTeacherByTeacherType(type);
        List<TeacherResponseDTO> result = new ArrayList<>();
        for (Teacher teacher : list) {
            result.add(getResponseDTO(teacher));
        }
        return result;
    }

    @Override
    public List<TeacherResponseDTO> getTeacherByQualification(TeacherQualification teacherQualification) {
        List<Teacher> list = teacherRepository.getTeacherByTeacherQualification(teacherQualification);
        List<TeacherResponseDTO> result = new ArrayList<>();
        for (Teacher teacher : list) {
            result.add(getResponseDTO(teacher));
        }
        return result;
    }

    @Override
    public List<TeacherResponseDTO> getTeacherByLevel(TeachingLevel level) {
        List<Teacher> list = teacherRepository.getTeacherByTeachingLevel(level);
        List<TeacherResponseDTO> result = new ArrayList<>();
        for (Teacher teacher : list) {
            result.add(getResponseDTO(teacher));
        }
        return result;
    }

    @Override
    public Teacher findById(String id) {
        return teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
    }

    @Override
    public List<TeacherResponseDTO> getAllActiveTeachers() {
        List<Teacher> list = teacherRepository.findByStatusTrue();
        return list.stream().map(TeacherServiceImpl::getResponseDTO).toList();
    }

    private static @NonNull TeacherResponseDTO getResponseDTO(Teacher teacher) {
        TeacherResponseDTO response = new TeacherResponseDTO();

        response.setId(teacher.getId());
        response.setFirstName(teacher.getFirstName());
        response.setMiddleName(teacher.getMiddleName());
        response.setLastName(teacher.getLastName());
        response.setAadhaarNumber(teacher.getAadhaarNumber());
        response.setDateOfBirth(teacher.getDateOfBirth());
        response.setGender(teacher.getGender());
        response.setStatus(teacher.isStatus());

        if (teacher.getAddress() != null) {
            response.setStreet(teacher.getAddress().getStreet());
            response.setCity(teacher.getAddress().getCity());
            response.setState(teacher.getAddress().getState());
            response.setPinCode(teacher.getAddress().getPinCode());
        }

        response.setEmail(teacher.getEmail());
        response.setSalary(teacher.getSalary());
        response.setPhoneNumber(teacher.getPhoneNumber());
        response.setDateOfJoining(teacher.getDateOfJoining());
        response.setTeacherType(teacher.getTeacherType());
        response.setTeachingLevel(teacher.getTeachingLevel());
        response.setTeacherQualification(teacher.getTeacherQualification());

        return response;
    }
}
