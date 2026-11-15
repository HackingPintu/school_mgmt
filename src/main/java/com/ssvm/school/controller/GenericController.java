package com.ssvm.school.controller;

import com.ssvm.school.dto.response.FeePaymentResponseDTO;
import com.ssvm.school.services.FeePaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GenericController {

    private final FeePaymentService feePaymentService;

    public GenericController(FeePaymentService feePaymentService) {
        this.feePaymentService = feePaymentService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

    @GetMapping("/receipt/{paymentId}")
    public String receipt(@PathVariable("paymentId") String paymentId, Model model) {
        FeePaymentResponseDTO dto = feePaymentService.getReceipt(paymentId);
        model.addAttribute("receiptNo", dto.getReceiptNo());
        model.addAttribute("paymentDate", dto.getPaymentDate());
        model.addAttribute("studentName", dto.getStudentName());
        model.addAttribute("className", dto.getClassName());
        model.addAttribute("paymentMode", dto.getPaymentMode());
        model.addAttribute("amountPaid", dto.getAmountPaid());

        return "receipt";
    }
}