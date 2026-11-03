package com.ssvm.school.services;

import com.ssvm.school.dto.request.FeePaymentRequestDTO;
import com.ssvm.school.dto.response.FeePaymentResponseDTO;
import com.ssvm.school.entity.FeeDue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeePaymentService {

    FeePaymentResponseDTO payFee(FeePaymentRequestDTO feePaymentRequestDTO);

    List<FeePaymentResponseDTO> findByFeeDue(FeeDue feeDue);




}
