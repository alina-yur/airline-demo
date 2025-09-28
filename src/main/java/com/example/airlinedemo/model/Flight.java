package com.example.airlinedemo.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight {
    private String id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private FlightStatus status;
    private String gate;

    public Flight() {
    }

    public Flight(String id, String flightNumber, String origin, String destination,
            LocalDateTime departureTime, FlightStatus status, String gate) {
        this.id = Objects.requireNonNull(id);
        this.flightNumber = Objects.requireNonNull(flightNumber);
        this.origin = Objects.requireNonNull(origin);
        this.destination = Objects.requireNonNull(destination);
        this.departureTime = Objects.requireNonNull(departureTime);
        this.status = status == null ? FlightStatus.SCHEDULED : status;
        this.gate = gate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    @Override
    public String toString() {
        return flightNumber + " " + origin + "→" + destination + " @" + departureTime;
    }
}
