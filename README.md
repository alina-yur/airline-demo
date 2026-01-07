# Airline AI Demo: Spring Boot meets Python 🐍

This is a Spring Boot web app demonstrating how to embed Python packages directly in a Java application using GraalPy. This project uses two Python libraries:
* `qrcode` is used to generate boarding passes with QR codes, following standard airlines patterns;
* `pygal` is used to render flight statistics charts.

All Python code is running and being managed within a Java application.

## Features

The app provides an airline dashboard with two main features:

**Boarding Pass Generator**: The user can enter passenger details and get an ASCII QR code for a boarding pass. Uses Python's `qrcode` through GraalPy's polyglot API.

**Flight Statistics Dashboard**: Visualizes destination distribution and flight status, rendered as SVG directly from Python code running in the Java service layer.

## Tech Stack

- Spring Boot 3.5.5
- Java 25
- GraalVM 25.0.0
- GraalPy (Python embedding)
- Python packages: qrcode 7.4.2, pygal 3.0.5
- Spring Data JDBC with H2 database
- Thymeleaf templates

## Running the App

Run Spring Boot:

```shell
mvn spring-boot:run
```

Then open http://localhost:8080 to see the departures board, or http://localhost:8080/boarding-pass to generate a boarding pass.

## Using Oracle Database

The project also works with Oracle database. To run:

```shell
export WALLET_PATH=/full/path/to/wallet/Wallet_ADB
export ORACLE_USER=your_username
export ORACLE_PASSWORD=your_password
mvn spring-boot:run -Poracle
```

## Building as Native Image

The app compiles to a native executable using GraalVM Native Image:

```shell
sdk install java 25.0.1-graal
mvn -Pnative native:compile
```

The native build includes the Python packages bundled into the binary, so you get fast startup times and lower memory footprint without needing a separate Python installation.

## How It Works

GraalPy lets you run Python code directly from Java through GraalVM's polyglot API. The [BoardingPassService.java](src/main/java/com/example/airlinedemo/service/BoardingPassService.java) creates a Python context, imports `qrcode`, and generates QR codes. The [DestinationStatsService.java](src/main/java/com/example/airlinedemo/service/DestinationStatsService.java) pulls data from the database, passes it to Python's `pygal` library, and returns rendered SVG charts.

Python dependencies are managed in [pom.xml](pom.xml) via the graalpy-maven-plugin, which downloads and bundles them during the build. No separate pip install or virtual environment needed.
