DROP TABLE IF EXISTS flights;

CREATE TABLE flights (
  id              VARCHAR(36) PRIMARY KEY,
  flight_number   VARCHAR(10) NOT NULL,
  origin          VARCHAR(3)  NOT NULL,
  destination     VARCHAR(3)  NOT NULL,
  departure_time  TIMESTAMP   NOT NULL,
  status          VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
  gate            VARCHAR(10)
);

CREATE INDEX idx_flights_departure_time ON flights(departure_time);
