package com.ssvm.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GenericController {

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

    @GetMapping("/receipt")
    public String receipt(Model model) {
        model.addAttribute("receiptNo", "Deeptendu_Gupta/2026-27/1");
        model.addAttribute("paymentDate", "2026-10-28");
        model.addAttribute("studentName", "Deeptendu Gupta");
        model.addAttribute("className", "I");
        model.addAttribute("paymentMode", "CASH");
        model.addAttribute("amountPaid", 500);

        return "receipt"; 
    }
}