package com.example.airlinedemo.model;

public enum Airport {
    ZRH("ZRH", "Zurich Airport", "Zurich"),
    CDG("CDG", "Charles de Gaulle Airport", "Paris"),
    LHR("LHR", "Heathrow Airport", "London"),
    AMS("AMS", "Amsterdam Airport Schiphol", "Amsterdam"),
    BCN("BCN", "Barcelona–El Prat Airport", "Barcelona"),
    FCO("FCO", "Leonardo da Vinci Airport", "Rome"),
    VIE("VIE", "Vienna International Airport", "Vienna"),
    MUC("MUC", "Munich Airport", "Munich"),
    FRA("FRA", "Frankfurt Airport", "Frankfurt"),
    MAD("MAD", "Madrid–Barajas Airport", "Madrid");

    private final String code;
    private final String name;
    private final String city;

    Airport(String code, String name, String city) {
        this.code = code;
        this.name = name;
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public static Airport fromCode(String code) {
        for (Airport airport : values()) {
            if (airport.code.equals(code)) {
                return airport;
            }
        }
        throw new IllegalArgumentException("Unknown airport code: " + code);
    }

    @Override
    public String toString() {
        return code + " - " + name;
    }
}