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
import reactor.core.scheduler.Scheduler;

public class StockHandler {
    private final StockDataSource dataSource;
    private final StockReaderService service;
    private final Scheduler scheduler;

    public StockHandler(StockDataSource dataSource, StockReaderService service, Scheduler scheduler) {
        this.dataSource = dataSource;
        this.service = service;
        this.scheduler = scheduler;
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        Flux<Movimento> movimento = Flux.fromStream(dataSource.list()).subscribeOn(scheduler);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movimento, Movimento.class);
    }

    public Mono<ServerResponse> listFechamentoMaximo(ServerRequest request) {
        Flux<Movimento> movimento = Flux.defer(() -> Flux.fromIterable(service.fechamentosMaximo(dataSource.list()))).subscribeOn(scheduler);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movimento, Movimento.class);
    }

    public Mono<ServerResponse> listFechamentoMinimo(ServerRequest request) {
        Flux<Movimento> movimento = Flux.defer(() -> Flux.fromIterable(service.fechamentosMinimo(dataSource.list()))).subscribeOn(scheduler);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movimento, Movimento.class);
    }

    public Mono<ServerResponse> listRetornoMaximo(ServerRequest request) {
        Flux<Retorno> retorno = Flux.defer(() -> Flux.fromIterable(service.retornosMaximo(dataSource.list())));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(retorno, Retorno.class);
    }

    public Mono<ServerResponse> listRetornoMinimo(ServerRequest request) {
        Flux<Retorno> retorno = Flux.defer(() -> Flux.fromIterable(service.retornosMinimo(dataSource.list())));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(retorno, Retorno.class);
    }

    public Mono<ServerResponse> listVolumeMedio(ServerRequest request) {
        Flux<Average> average = Flux.defer(() -> Flux.fromIterable(service.volumesMedio(dataSource.list())));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(average, Average.class);
    }
}
