package com.example.airlinedemo.service;

import com.example.airlinedemo.config.GraalPyContextConfiguration.GraalPyContext;
import com.example.airlinedemo.repository.DestinationStatsRepository;
import com.example.airlinedemo.repository.FlightStatusStatsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DestinationStatsService {

    private final DestinationStatsRepository destinationRepo;
    private final FlightStatusStatsRepository statusRepo;
    private final GraalPyContext py;

    public DestinationStatsService(DestinationStatsRepository destinationRepo,
            FlightStatusStatsRepository statusRepo,
            GraalPyContext py) {
        this.destinationRepo = destinationRepo;
        this.statusRepo = statusRepo;
        this.py = py;
    }

    public String renderDestinationsPieSvg(String title) {
        var counts = destinationRepo.countByDestination();
        List<Object[]> data = new ArrayList<>(counts.size());
        for (var dc : counts)
            data.add(new Object[] { dc.destination(), dc.count() });

        String pySrc = """
                import pygal
                from pygal import Config
                cfg = Config()
                cfg.no_xml_declaration = True
                pie = pygal.Pie(cfg, inner_radius=0.4, legend_at_bottom=True, print_values=True)
                pie.title = chart_title if chart_title else "Destinations"
                for name, cnt in data:
                    pie.add(name, int(cnt))
                pie.render().decode('utf-8')
                """;

        var b = py.context().getBindings("python");
        b.putMember("data", data);
        b.putMember("chart_title", title);
        return py.eval(pySrc).asString();
    }

    public String renderFlightStatusBarSvg() {
        var rows = statusRepo.countByStatus();
        List<Object[]> data = new ArrayList<>(rows.size());
        for (var r : rows)
            data.add(new Object[] { r.status(), r.count() });

        String pySrc = """
                import pygal
                from pygal import Config
                cfg = Config()
                cfg.no_xml_declaration = True
                bar = pygal.HorizontalBar(cfg, show_legend=True, legend_at_bottom=True, print_values=True)
                bar.title = "Flight statuses"
                for name, cnt in data:
                    bar.add(name, int(cnt))
                bar.render().decode('utf-8')
                """;

        var b = py.context().getBindings("python");
        b.putMember("data", data);
        return py.eval(pySrc).asString();
    }
}
