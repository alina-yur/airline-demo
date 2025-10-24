package com.example.airlinedemo.controller;

import com.example.airlinedemo.service.DestinationStatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DestinationStatsController {

    private final DestinationStatsService charts;

    public DestinationStatsController(DestinationStatsService charts) {
        this.charts = charts;
    }

    @GetMapping("/destinations")
    public String stats(Model model) {
        model.addAttribute("pieSvg", charts.renderDestinationsPieSvg("Destinations (by schedules)"));
        model.addAttribute("statusSvg", charts.renderFlightStatusBarSvg());
        return "stats";
    }
}
