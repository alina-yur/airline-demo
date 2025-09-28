package com.example.airlinedemo.config;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.graalvm.python.embedding.GraalPyResources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraalPyContextConfiguration {

    @Bean(destroyMethod = "close")
    public GraalPyContext graalPyContext() {
        Context context = GraalPyResources.createContext();
        context.initialize("python");
        return new GraalPyContext(context);
    }

    public record GraalPyContext(Context context) {
        public Value eval(String source) {
            return context.eval("python", source);
        }

        public void close() {
            context.close(true);
        }
    }
}
