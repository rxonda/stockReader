package br.org.quantum.rest;

import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.VolumeMedio;
import br.org.quantum.services.StockNonBlockingReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.schedulers.Schedulers;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

/**
 * Created by xonda on 23/03/2015.
 */
@RestController
@RequestMapping("/nonblocking")
public class StockNonBlockingRestController {
    @Autowired
    private StockNonBlockingReader stockReaderService;

    @Autowired
    private ExecutorService executorService;

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> list() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>();

        stockReaderService.list()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe(m -> deferredResult.setResult(m));

        return deferredResult;
    }

    @RequestMapping(value = "/fechamentoMaximo", method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> listFechamentoMaximo() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>();

        stockReaderService.fechamentosMaximo()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe(m -> deferredResult.setResult(m));

        return deferredResult;
    }

    @RequestMapping(value = "/fechamentoMinimo", method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> listFechamentoMinimo() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>();

        stockReaderService.fechamentosMinimo()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe(m -> deferredResult.setResult(m));

        return deferredResult;
    }

    @RequestMapping(value = "/retornoMaximo", method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> listRetornoMaximo() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>();

        stockReaderService.retornosMaximo()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe(m -> deferredResult.setResult(m));

        return deferredResult;
    }

    @RequestMapping(value = "/retornoMinimo", method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> listRetornoMinimo() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>();

        stockReaderService.retornosMinimo()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe(m -> deferredResult.setResult(m), t -> deferredResult.setErrorResult(t));

        return deferredResult;
    }

    @RequestMapping(value = "/volumeMedio", method = RequestMethod.GET)
    public DeferredResult<Collection<VolumeMedio>> listVolumeMedio() {
        DeferredResult<Collection<VolumeMedio>> deferredResult = new DeferredResult<>();

        stockReaderService.volumesMedio()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe(m -> deferredResult.setResult(m), t -> deferredResult.setErrorResult(t));

        return deferredResult;
    }

}
