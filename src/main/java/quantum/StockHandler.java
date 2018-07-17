package quantum;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import quantum.dao.StockDataSource;
import reactor.core.publisher.Mono;

public class StockHandler {
    private final StockDataSource dataSource;

    public StockHandler(StockDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> listFechamentoMaximo(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> listFechamentoMinimo(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> listRetornoMaximo(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> listRetornoMinimo(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> listVolumeMedio(ServerRequest request) {
        return null;
    }
}
