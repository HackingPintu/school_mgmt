package com.ssvm.school.service;

import com.ssvm.school.dto.request.AcademicYearRequestDTO;
import com.ssvm.school.dto.response.AcademicYearResponseDTO;
import com.ssvm.school.entity.AcademicYear;
import com.ssvm.school.repository.AcademicYearRepository;
import com.ssvm.school.services.impl.AcademicYearServiceImpl;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AcademicYearServiceImplTest {

    @Mock
    private AcademicYearRepository academicYearRepository;

    @InjectMocks
    private AcademicYearServiceImpl academicYearService;

    @Test
    void shouldReturnAcademicYearWhenExistsByYearLabel() {
        //arrange
        AcademicYear academicYear = AcademicYear.builder()
                .yearLabel("2024-25")
                .isActive(true)
                .build();
        when(academicYearRepository.findByYearLabel("2024-25")).thenReturn(Optional.of(academicYear));

        //act
        Optional<AcademicYear> actualYear = academicYearService.findByYearLabel("2024-25");

        //assertion
        assertEquals("2024-25", actualYear.map(AcademicYear::getYearLabel).orElse(""));
        assertEquals(true, actualYear.map(AcademicYear::isActive).orElse(false));

        //verify
        verify(academicYearRepository).findByYearLabel("2024-25");
    }

    @Test
    void shouldThrowExceptionWhenNoAcademicYearFoundByYearLabel() {
        //arrange
        when(academicYearRepository.findByYearLabel("2025-26")).thenReturn(Optional.empty());

        //act
        Optional<AcademicYear> actualYear = academicYearService.findByYearLabel("2025-26");

        //assertion
        assertEquals("", actualYear.map(AcademicYear::getYearLabel).orElse(""));

        //verify
        verify(academicYearRepository).findByYearLabel("2025-26");
    }

    @Test
    void shouldThrowExceptionWhenAcademicYearAlreadyExists() {
        // Arrange
        String yearLabel = "2024-25";
        AcademicYearRequestDTO request = AcademicYearRequestDTO.builder()
                .yearLabel(yearLabel)
                .build();

        AcademicYear existingAcademicYear = AcademicYear.builder()
                .yearLabel(request.getYearLabel())
                .isActive(true)
                .build();

        // Mock that the academic year already exists in the database
        when(academicYearRepository.findByYearLabel(yearLabel))
                .thenReturn(Optional.of(existingAcademicYear));

        // Act
        EntityExistsException exception = assertThrows(EntityExistsException.class, () ->
                academicYearService.createAcademicYear(request)
        );

        // Assert
        assertEquals("Academic year already exists", exception.getMessage());

        // Verify: save should NEVER be called
        verify(academicYearRepository).findByYearLabel(yearLabel);
        verify(academicYearRepository, never()).save(any(AcademicYear.class));
    }

    @Test
    void createAcademicYearIfNotExists() {
        //Arrange
        String yearLabel = "2024-25";
        AcademicYearRequestDTO request = AcademicYearRequestDTO.builder()
                .yearLabel(yearLabel)
                .build();

        AcademicYear academicYear = AcademicYear.builder()
                .yearLabel(request.getYearLabel())
                .isActive(true)
                .build();


        //Mock
        when(academicYearRepository.findByYearLabel(yearLabel)).thenReturn(Optional.empty());
        when(academicYearRepository.save(any(AcademicYear.class))).thenReturn(academicYear);

        //Act
        AcademicYearResponseDTO actualResponse = academicYearService.createAcademicYear(request);

        //Assert
        assertEquals(yearLabel, actualResponse.getAcademicYearName());

        //verify
        verify(academicYearRepository).findByYearLabel(yearLabel);
        verify(academicYearRepository).save(any(AcademicYear.class));

    }

}
