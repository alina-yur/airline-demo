package com.example.airlinedemo.controller;

import com.example.airlinedemo.service.DestinationStatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DestinationStatsController {

    private final DestinationStatsService charts;

    public DestinationStatsController(DestinationStatsService charts) {
        this.charts = charts;
    }

    @GetMapping(value = "/destinations", produces = "image/svg+xml")
    public String destinationsPie(@RequestParam(required = false) String title) {
        return charts.renderDestinationsPieSvg(title);
    }
}
