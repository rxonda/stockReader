package quantum;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import quantum.dao.StockDataSource;
import quantum.dao.StockFileReaderDataSource;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

public class Application {
    private final static String HOST = "localhost";
    private final static int PORT = 8080;

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.startReactorServer();
        System.out.println("Press ENTER to exit.");
        System.in.read();
    }

    public RouterFunction<ServerResponse> routingFunction() {
        StockDataSource repository = new StockFileReaderDataSource();
        StockHandler handler = new StockHandler(repository);

        return nest(path("/nonblocking"),
                nest(accept(APPLICATION_JSON),
                        route(method(HttpMethod.GET), handler::list)
                                .andRoute(GET("/fechamentoMaximo"), handler::listFechamentoMaximo)
                                .andRoute(GET("/fechamentoMinimo"), handler::listFechamentoMinimo)
                                .andRoute(GET("/retornoMaximo"), handler::listRetornoMaximo)
                                .andRoute(GET("/retornoMinimo"), handler::listRetornoMinimo)
                                .andRoute(GET("/volumeMedio"), handler::listVolumeMedio)
                )
        );
    }

    public void startReactorServer() {
        RouterFunction<ServerResponse> route = routingFunction();
        HttpHandler httpHandler = toHttpHandler(route);

        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create(HOST, PORT);
        server.newHandler(adapter).block();
    }
}
