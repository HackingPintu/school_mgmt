package com.ssvm.school.services;


import com.ssvm.school.dto.response.FeePaymentResponseDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface FeeCalculationService {

    BigDecimal calculatePendingFee();

    BigDecimal calculatePendingFeeForStudent(String enrollmentId);

    List<FeePaymentResponseDTO> calculatePaidFeeForStudent(String enrollmentId);


}
