package quantum.config;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import quantum.StockHandler;
import quantum.dao.StockDataSource;
import quantum.dao.StockFileReaderDataSource;
import quantum.services.StockReaderService;
import quantum.services.StockReaderServiceImpl;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebConfig {
    @Bean
    Scheduler fileScheduler(Environment env) {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(env.getRequiredProperty("file.connection.pool.size", Integer.class)));
    }

    @Bean
    StockHandler handler(Scheduler scheduler) {
        StockDataSource repository = new StockFileReaderDataSource();
        StockReaderService service = new StockReaderServiceImpl();
        return new StockHandler(repository, service, scheduler);
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction(StockHandler handler) {
        return nest(path("/nonblocking"),
                nest(accept(APPLICATION_JSON),
                        route(GET("/fechamentoMaximo"), handler::listFechamentoMaximo)
                                .andRoute(GET("/fechamentoMinimo"), handler::listFechamentoMinimo)
                                .andRoute(GET("/retornoMaximo"), handler::listRetornoMaximo)
                                .andRoute(GET("/retornoMinimo"), handler::listRetornoMinimo)
                                .andRoute(GET("/volumeMedio"), handler::listVolumeMedio)
                                .andRoute(method(HttpMethod.GET), handler::list)
                )
        );
    }

    @Bean
    HttpHandler webHandler(RouterFunction<ServerResponse> routerFunction) {
        return RouterFunctions.toHttpHandler(routerFunction);
    }

    @Bean
    ReactiveWebServerFactory reactiveWebServerFactory(Environment env) {
        return new NettyReactiveWebServerFactory(env.getRequiredProperty("server.port", Integer.class));
    }
}
