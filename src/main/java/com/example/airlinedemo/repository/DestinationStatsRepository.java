package com.example.airlinedemo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DestinationStatsRepository {

    private final JdbcTemplate jdbc;

    public DestinationStatsRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<DestinationCount> countByDestination() {
        return jdbc.query(
                """
                        SELECT destination, COUNT(*) AS cnt
                        FROM flight_schedules
                        GROUP BY destination
                        ORDER BY cnt DESC
                        """,
                (rs, i) -> new DestinationCount(rs.getString("destination"), rs.getLong("cnt")));
    }

    public record DestinationCount(String destination, long count) {
    }
}
