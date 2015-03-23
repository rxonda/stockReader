package br.org.quantum.rest;

import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.VolumeMedio;
import br.org.quantum.services.StockReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

/**
 * Created by xonda on 23/03/2015.
 */
@RestController
@RequestMapping("/nonblocking")
public class StockNonBlockingRestController {
    @Autowired
    private StockReaderService stockReaderService;

    @Autowired
    ExecutorService executorService;

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> list() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>();
        executorService.execute(() -> deferredResult.setResult(stockReaderService.list()));
        return deferredResult;
    }

    @RequestMapping(value = "/fechamentoMaximo", method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> listFechamentoMaximo() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>();
        executorService.execute(() -> deferredResult.setResult(stockReaderService.fechamentosMaximo()));
        return deferredResult;
    }

    @RequestMapping(value = "/fechamentoMinimo", method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> listFechamentoMinimo() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>();
        executorService.execute(() -> deferredResult.setResult(stockReaderService.fechamentosMinimo()));
        return deferredResult;
    }

    @RequestMapping(value = "/retornoMaximo", method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> listRetornoMaximo() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>();
        executorService.execute(() -> deferredResult.setResult(stockReaderService.retornosMaximo()));
        return deferredResult;
    }

    @RequestMapping(value = "/retornoMinimo", method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> listRetornoMinimo() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>();
        executorService.execute(() -> deferredResult.setResult(stockReaderService.retornosMinimo()));
        return deferredResult;
    }

    @RequestMapping(value = "/volumeMedio", method = RequestMethod.GET)
    public DeferredResult<Collection<VolumeMedio>> listVolumeMedio() {
        DeferredResult<Collection<VolumeMedio>> deferredResult = new DeferredResult<>();
        executorService.execute(() -> deferredResult.setResult(stockReaderService.volumesMedio()));
        return deferredResult;
    }

}
