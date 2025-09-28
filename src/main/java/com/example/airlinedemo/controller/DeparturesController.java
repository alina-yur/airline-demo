package com.example.airlinedemo.controller;

import com.example.airlinedemo.repository.FlightDao;
import com.example.airlinedemo.model.Airport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class DeparturesController {

    private final FlightDao flights;

    public DeparturesController(FlightDao flights) {
        this.flights = flights;
    }

    @GetMapping({ "/", "/departures" })
    public String departures(Model model) {
        LocalDate today = LocalDate.now();
        model.addAttribute("flights", flights.findDeparturesOn(today));
        model.addAttribute("date", today);
        model.addAttribute("airport", "All Airports");
        model.addAttribute("airports", Airport.values());
        return "departures";
    }

    @GetMapping("/departures/{airportCode}")
    public String departuresByAirport(@PathVariable String airportCode, Model model) {
        Airport airport = Airport.valueOf(airportCode.toUpperCase());
        LocalDate today = LocalDate.now();
        model.addAttribute("flights", flights.findDeparturesOnFrom(today, airport.name()));
        model.addAttribute("date", today);
        model.addAttribute("airport", airport.toString());
        model.addAttribute("airportCode", airport.name());
        model.addAttribute("airports", Airport.values());
        return "departures";
    }
}
