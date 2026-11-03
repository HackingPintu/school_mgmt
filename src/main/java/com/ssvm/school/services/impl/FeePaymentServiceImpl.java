package com.ssvm.school.services.impl;

import com.ssvm.school.dto.request.FeePaymentRequestDTO;
import com.ssvm.school.dto.response.FeePaymentResponseDTO;
import com.ssvm.school.entity.FeeDue;
import com.ssvm.school.entity.FeePayment;
import com.ssvm.school.repository.FeePaymentRepository;
import com.ssvm.school.services.FeeDueService;
import com.ssvm.school.services.FeePaymentService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeePaymentServiceImpl implements FeePaymentService {

    private final FeePaymentRepository feePaymentRepository;
    private final FeeDueService feeDueService;

    public FeePaymentServiceImpl(FeePaymentRepository feePaymentRepository
            , FeeDueService feeDueService) {
        this.feePaymentRepository = feePaymentRepository;
        this.feeDueService = feeDueService;
    }

    @Override
    @Transactional
    public FeePaymentResponseDTO payFee(FeePaymentRequestDTO feePaymentRequestDTO) {
        FeeDue due = feeDueService.findById(feePaymentRequestDTO.getDueId());
        long count = feePaymentRepository.countByFeeDue(due);
        String receiptNo = due.getEnrollment().getStudent().getFirstName() + "_" + due.getEnrollment().getStudent().getLastName()
                + "/" + due.getEnrollment().getAcademicYear().getYearLabel() + "/" + ++count;
        if (feePaymentRequestDTO.getAmountPaid().compareTo(due.getAmountDue()) > 0) {
            throw new IllegalArgumentException("Amount paid must be greater than or equal to amount due");
        }
        FeePayment payment = FeePayment.builder()
                .feeDue(due)
                .amountPaid(feePaymentRequestDTO.getAmountPaid())
                .paymentDate(feePaymentRequestDTO.getPaymentDate())
                .paymentMode(feePaymentRequestDTO.getPaymentMode())
                .receiptNo(receiptNo)
                .build();
        feeDueService.updateFeeDue(due.getId(), payment.getAmountPaid());
        return getFeePaymentResponseDTO(feePaymentRepository.save(payment));
    }

    @Override
    public List<FeePaymentResponseDTO> findByFeeDue(FeeDue feeDue) {
        List<FeePayment> payments = feePaymentRepository.findByFeeDue(feeDue);
        if (payments.isEmpty()) {
            throw new IllegalArgumentException("No payment found for feeDue " + feeDue);
        }

        return payments.stream().map(FeePaymentServiceImpl::getFeePaymentResponseDTO).toList();
    }


    private static @NotNull FeePaymentResponseDTO getFeePaymentResponseDTO(FeePayment payment) {
        return FeePaymentResponseDTO.builder()
                .amountPaid(payment.getAmountPaid())
                .paidDate(payment.getPaymentDate())
                .receiptNo(payment.getReceiptNo())
                .build();
    }
}
