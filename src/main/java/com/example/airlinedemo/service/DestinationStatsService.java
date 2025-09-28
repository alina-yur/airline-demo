package com.example.airlinedemo.service;

import com.example.airlinedemo.config.GraalPyContextConfiguration.GraalPyContext;
import com.example.airlinedemo.repository.DestinationStatsRepository;
import com.example.airlinedemo.repository.DestinationStatsRepository.DestinationCount;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DestinationStatsService {

    private final DestinationStatsRepository repo;
    private final GraalPyContext py;

    public DestinationStatsService(DestinationStatsRepository repo, GraalPyContext py) {
        this.repo = repo;
        this.py = py;
    }

    public String renderDestinationsPieSvg(String title) {
        List<DestinationCount> counts = repo.countByDestination();

        List<Object[]> data = new ArrayList<>(counts.size());
        for (DestinationCount dc : counts) {
            data.add(new Object[] { dc.destination(), dc.count() });
        }

        String pySrc = """
                import pygal
                pie = pygal.Pie(inner_radius=0.4, legend_at_bottom=True, print_values=True)
                pie.title = chart_title
                for name, cnt in data:
                    pie.add(name, int(cnt))
                pie.render().decode('utf-8')
                """;

        py.context().getBindings("python").putMember("data", data);
        py.context().getBindings("python").putMember("chart_title",
                title != null ? title : "Destinations (by schedules)");

        Value svg = py.eval(pySrc);
        return svg.asString();
    }
}
