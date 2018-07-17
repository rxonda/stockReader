package quantum;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import quantum.dao.StockDataSource;
import quantum.domain.Average;
import quantum.domain.Movimento;
import quantum.domain.Retorno;
import quantum.services.StockReaderService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class StockHandler {
    private final StockDataSource dataSource;
    private final StockReaderService service;

    public StockHandler(StockDataSource dataSource, StockReaderService service) {
        this.dataSource = dataSource;
        this.service = service;
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        Flux<Movimento> movimento = Flux.fromStream(dataSource.list());
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movimento, Movimento.class);
    }

    public Mono<ServerResponse> listFechamentoMaximo(ServerRequest request) {
        Flux<Movimento> movimento = Flux.fromStream(service.fechamentosMaximo().apply(dataSource.list()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movimento, Movimento.class);
    }

    public Mono<ServerResponse> listFechamentoMinimo(ServerRequest request) {
        Flux<Movimento> movimento = Flux.fromStream(service.fechamentosMinimo().apply(dataSource.list()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movimento, Movimento.class);
    }

    public Mono<ServerResponse> listRetornoMaximo(ServerRequest request) {
        Flux<Retorno> retorno = Flux.fromStream(service.retornosMaximo().apply(dataSource.list()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(retorno, Retorno.class);
    }

    public Mono<ServerResponse> listRetornoMinimo(ServerRequest request) {
        Flux<Retorno> retorno = Flux.fromStream(service.retornosMinimo().apply(dataSource.list()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(retorno, Retorno.class);
    }

    public Mono<ServerResponse> listVolumeMedio(ServerRequest request) {
        Flux<Average> average = Flux.fromStream(service.volumesMedio().apply(dataSource.list()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(average, Average.class);
    }
}
