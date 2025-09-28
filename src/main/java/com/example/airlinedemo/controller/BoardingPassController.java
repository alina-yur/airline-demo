package com.example.airlinedemo.controller;

import com.example.airlinedemo.service.BoardingPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardingPassController {

    @Autowired
    private BoardingPassService boardingPassService;

    @GetMapping("/boarding-pass")
    public String showForm() {
        return "boarding-pass-form";
    }

    @PostMapping("/boarding-pass")
    public String generateBoardingPass(@RequestParam String passengerName,
            @RequestParam String bookingReference,
            Model model) {

        String qrCode = boardingPassService.generateQRCode(passengerName, bookingReference, "LX123");

        model.addAttribute("passengerName", passengerName.toUpperCase());
        model.addAttribute("bookingReference", bookingReference);
        model.addAttribute("qrCode", qrCode);

        return "boarding-pass";
    }
}