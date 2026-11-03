package com.ssvm.school.services.impl;

import com.ssvm.school.dto.request.StudentRequestDTO;
import com.ssvm.school.dto.response.StudentResponseDTO;
import com.ssvm.school.entity.Address;
import com.ssvm.school.entity.Student;
import com.ssvm.school.mapper.StudentMapper;
import com.ssvm.school.repository.StudentRepository;
import com.ssvm.school.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;


    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {

        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }


    @Override
    @Transactional
    @Tool(name = "create_student", description = "Create a student using the student request dto and return in the student response dto")
    public StudentResponseDTO createStudent(StudentRequestDTO req) {

        Student student = Student.builder()
                .firstName(req.getFirstName())
                .middleName(req.getMiddleName())
                .lastName(req.getLastName())
                .aadhaarNumber(req.getAadhaarNumber())
                .dateOfBirth(req.getDateOfBirth())
                .gender(req.getGender())
                .bloodGroup(req.getBloodGroup())
                .address(Address.builder()
                        .street(req.getStreet())
                        .city(req.getCity())
                        .state(req.getState())
                        .pinCode(req.getPinCode())
                        .build())
                .status(req.isStatus())
                .fatherName(req.getFatherName())
                .motherName(req.getMotherName())
                .guardianNumber(req.getGuardianNumber())
                .admissionDate(req.getAdmissionDate())
                .build();

        Student savedStudent = studentRepository.save(student);

        return getResponseDTO(savedStudent);

    }

    private static @NonNull StudentResponseDTO getResponseDTO(Student savedStudent) {
        StudentResponseDTO response = new StudentResponseDTO();

        response.setId(savedStudent.getId());
        response.setFirstName(savedStudent.getFirstName());
        response.setMiddleName(savedStudent.getMiddleName());
        response.setLastName(savedStudent.getLastName());
        response.setAadhaarNumber(savedStudent.getAadhaarNumber());
        response.setDateOfBirth(savedStudent.getDateOfBirth());
        response.setGender(savedStudent.getGender());
        response.setStatus(savedStudent.isStatus());

        if (savedStudent.getAddress() != null) {
            response.setStreet(savedStudent.getAddress().getStreet());
            response.setCity(savedStudent.getAddress().getCity());
            response.setState(savedStudent.getAddress().getState());
            response.setPinCode(savedStudent.getAddress().getPinCode());
        }

        response.setFatherName(savedStudent.getFatherName());
        response.setMotherName(savedStudent.getMotherName());
        response.setGuardianNumber(savedStudent.getGuardianNumber());
        response.setAdmissionDate(savedStudent.getAdmissionDate());
        return response;
    }

    @Override
    @Tool(name = "get_paginated_student", description = "Return students with pagination")
    public Page<StudentResponseDTO> getPaginatedStudents(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Student> studentPage = studentRepository.findByStatusTrue(pageable);
        return studentPage.map(StudentServiceImpl::getResponseDTO);
    }

    @Override
    public void deleteStudent(String id) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No student found"));
        if (student.isStatus()) {
            student.setStatus(false);
            studentRepository.save(student);
        } else {
            throw new EntityNotFoundException("No student found");
        }

    }

    @Override
    @Transactional
    public StudentResponseDTO updateStudent(String id, StudentRequestDTO student) {
        Optional<Student> studentOptional = Optional.of(studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found")));

        studentMapper.updateStudent(student, studentOptional.get());

        return getResponseDTO(studentRepository.save(studentOptional.get()));
    }

    @Override
    public Student findByIdAndStatusTrue(String id) {
        return studentRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new EntityNotFoundException("No student found"));
    }

}
