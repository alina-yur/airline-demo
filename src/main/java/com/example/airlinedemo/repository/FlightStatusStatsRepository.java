package com.example.airlinedemo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FlightStatusStatsRepository {

    private final JdbcTemplate jdbc;

    public FlightStatusStatsRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<StatusCount> countByStatus() {
        return jdbc.query(
                """
                        SELECT status, COUNT(*) AS cnt
                        FROM flights
                        GROUP BY status
                        ORDER BY cnt DESC
                        """,
                (rs, i) -> new StatusCount(rs.getString("status"), rs.getLong("cnt")));
    }

    public record StatusCount(String status, long count) {
    }
}
