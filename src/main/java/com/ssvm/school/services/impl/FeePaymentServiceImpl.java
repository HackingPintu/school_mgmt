package com.ssvm.school.services.impl;

import com.ssvm.school.constants.NotificationType;
import com.ssvm.school.dto.request.FeePaymentRequestDTO;
import com.ssvm.school.dto.response.FeePaymentResponseDTO;
import com.ssvm.school.entity.FeeDue;
import com.ssvm.school.entity.FeePayment;
import com.ssvm.school.event.NotificationEvent;
import com.ssvm.school.repository.FeePaymentRepository;
import com.ssvm.school.services.FeeDueService;
import com.ssvm.school.services.FeePaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FeePaymentServiceImpl implements FeePaymentService {

    private final FeePaymentRepository feePaymentRepository;
    private final FeeDueService feeDueService;
    private final ApplicationEventPublisher eventPublisher;

    public FeePaymentServiceImpl(FeePaymentRepository feePaymentRepository
            , FeeDueService feeDueService, ApplicationEventPublisher eventPublisher) {
        this.feePaymentRepository = feePaymentRepository;
        this.feeDueService = feeDueService;
        this.eventPublisher = eventPublisher;
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
        Map<String, Object> receiptModel = new HashMap<>();
        receiptModel.put("receiptNo", payment.getReceiptNo());
        receiptModel.put("paymentDate", payment.getPaymentDate().toString());
        receiptModel.put("studentName", due.getEnrollment().getStudent().getFirstName() + " " +
                due.getEnrollment().getStudent().getLastName());
        receiptModel.put("className", due.getEnrollment().getAcademicYear().getYearLabel());
        receiptModel.put("paymentMode", payment.getPaymentMode().toString());
        receiptModel.put("amountPaid", payment.getAmountPaid());
        feeDueService.updateFeeDue(due.getId(), payment.getAmountPaid());
        eventPublisher.publishEvent(new NotificationEvent(
                "enginex43@gmail.com",
                "SSVM School Fee Payment",
                "Amount of ₹" + feePaymentRequestDTO.getAmountPaid() + " paid.",
                NotificationType.EMAIL,
                "receipt",
                receiptModel,
                receiptNo + ".pdf"
        ));
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

    @Override
    public FeePaymentResponseDTO getReceipt(String paymentId) {
        FeePayment payment = feePaymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("No such payment found"));
        return getFeePaymentResponseDTO(payment);
    }

    private static @NotNull FeePaymentResponseDTO getFeePaymentResponseDTO(FeePayment payment) {

        String studentName = payment.getFeeDue().getEnrollment().getStudent().getFirstName() + " " + payment.getFeeDue().getEnrollment().getStudent().getLastName();
        String className = payment.getFeeDue().getEnrollment().getSchoolClass().getClassName().toString();

        return FeePaymentResponseDTO.builder()
                .receiptNo(payment.getReceiptNo())
                .paymentDate(payment.getPaymentDate())
                .studentName(studentName)
                .className(className)
                .paymentMode(payment.getPaymentMode())
                .amountPaid(payment.getAmountPaid())
                .build();
    }
}
