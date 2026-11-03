package com.ssvm.school.services;

import com.ssvm.school.constants.Standards;
import com.ssvm.school.dto.request.SchoolClassRequestDTO;
import com.ssvm.school.dto.response.SchoolClassResponseDTO;
import com.ssvm.school.entity.SchoolClass;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SchoolClassService {

    SchoolClassResponseDTO create(SchoolClassRequestDTO schoolClassRequestDTO);

    SchoolClassResponseDTO update(SchoolClassRequestDTO schoolClassRequestDTO);

    void delete(String id);

    List<SchoolClassResponseDTO> findAll();

    Optional<SchoolClass> findByStandards(Standards standards);

    SchoolClass findByStandardAndStatusTrue(Standards standards);

}
