package com.example.airlinedemo.service;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;
import org.graalvm.python.embedding.GraalPyResources;

@Service
public class BoardingPassService {

    public String generateQRCode(String passengerName, String bookingRef, String flightNumber) {
        String qrData = String.format("M1 %-20s E%-7s%s",
                passengerName.toUpperCase(),
                bookingRef,
                flightNumber);

        try (Context context = GraalPyResources
                .contextBuilder()
                .allowAllAccess(true)
                .build()) {

            Value qrcode = context.eval("python", "import qrcode, io; qrcode");
            Value io = context.getBindings("python").getMember("io");

            Value qr = qrcode.getMember("QRCode").newInstance();
            qr.invokeMember("add_data", qrData);
            qr.invokeMember("make", true);

            Value buf = io.getMember("StringIO").newInstance();
            qr.invokeMember("print_ascii", buf);
            buf.invokeMember("seek", 0);
            return buf.invokeMember("getvalue").asString();
        } catch (Exception e) {
            return "Ooops";
        }
    }
}
