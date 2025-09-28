package com.example.airlinedemo.repository;

import com.example.airlinedemo.model.Flight;
import com.example.airlinedemo.model.FlightStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FlightDao {

    private final JdbcTemplate jdbc;

    public FlightDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Flight> MAPPER = (rs, i) -> {
        Flight f = new Flight();
        f.setId(rs.getString("id"));
        f.setFlightNumber(rs.getString("flight_number"));
        f.setOrigin(rs.getString("origin"));
        f.setDestination(rs.getString("destination"));
        f.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        String statusStr = rs.getString("status");
        try {
            f.setStatus(statusStr == null ? FlightStatus.SCHEDULED : FlightStatus.valueOf(statusStr));
        } catch (IllegalArgumentException e) {
            f.setStatus(FlightStatus.SCHEDULED);
        }
        f.setGate(rs.getString("gate"));
        return f;
    };

    public List<Flight> findDeparturesOn(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return jdbc.query("""
                SELECT id, flight_number, origin, destination, departure_time, status, gate
                FROM flights
                WHERE departure_time >= ? AND departure_time < ?
                ORDER BY departure_time
                """,
                MAPPER,
                Timestamp.valueOf(start),
                Timestamp.valueOf(end));
    }

    public List<Flight> findDeparturesOnFrom(LocalDate date, String origin) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        String normOrigin = origin == null ? null : origin.trim().toUpperCase();

        return jdbc.query("""
                SELECT id, flight_number, origin, destination, departure_time, status, gate
                FROM flights
                WHERE origin = ? AND departure_time >= ? AND departure_time < ?
                ORDER BY departure_time
                """,
                MAPPER,
                normOrigin,
                Timestamp.valueOf(start),
                Timestamp.valueOf(end));
    }

    public List<Flight> findAllOrderByTime() {
        return jdbc.query("""
                SELECT id, flight_number, origin, destination, departure_time, status, gate
                FROM flights
                ORDER BY departure_time
                """, MAPPER);
    }

    public Flight findById(String id) {
        return jdbc.queryForObject("""
                SELECT id, flight_number, origin, destination, departure_time, status, gate
                FROM flights WHERE id = ?
                """, MAPPER, id);
    }
}
