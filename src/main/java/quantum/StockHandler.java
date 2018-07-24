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

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Integer> pagination = pagination(request);
        Flux<Movimento> movimento = Flux.fromStream(dataSource.list(pagination.get("start"), pagination.get("offset"))).subscribeOn(scheduler);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movimento, Movimento.class);
    }

    public Mono<ServerResponse> listFechamentoMaximo(ServerRequest request) {
        Flux<Movimento> movimento = deferToScheduler(service.fechamentosMaximo(dataSource.list()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movimento, Movimento.class);
    }

    public Mono<ServerResponse> listFechamentoMinimo(ServerRequest request) {
        Flux<Movimento> movimento = deferToScheduler(service.fechamentosMinimo(dataSource.list()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movimento, Movimento.class);
    }

    public Mono<ServerResponse> listRetornoMaximo(ServerRequest request) {
        Flux<Retorno> retorno = deferToScheduler(service.retornosMaximo(dataSource.list()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(retorno, Retorno.class);
    }

    public Mono<ServerResponse> listRetornoMinimo(ServerRequest request) {
        Flux<Retorno> retorno = deferToScheduler(service.retornosMinimo(dataSource.list()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(retorno, Retorno.class);
    }

    public Mono<ServerResponse> listVolumeMedio(ServerRequest request) {
        Flux<Average> average = deferToScheduler(service.volumesMedio(dataSource.list()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(average, Average.class);
    }

    private <T> Flux<T> deferToScheduler(Iterable<T> iterable) {
        return Flux.defer(() -> Flux.fromIterable(iterable)).subscribeOn(scheduler);
    }

    private Map<String, Integer> pagination(ServerRequest request) {
        Map<String, Integer> result = new HashMap<>();
        request.queryParam("start").ifPresent(v -> result.put("start", Integer.valueOf(v)));
        request.queryParam("offset").ifPresent(v -> result.put("offset", Integer.valueOf(v)));
        return result;
    }
}
