package quantum.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.DeferredResult
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import quantum.services.StockNonBlockingReader
import rx.schedulers.Schedulers

import java.util.concurrent.ExecutorService

/**
 * Created by xonda on 23/03/2015.
 */
@RestController
@RequestMapping("/nonblocking")
public class StockNonBlockingRestController {
    @Autowired
    private StockNonBlockingReader stockReaderService

    @Autowired
    private ExecutorService executorService

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> list() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>()

        stockReaderService.list()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe({ v -> deferredResult.setResult(v) })

        return deferredResult
    }

    @RequestMapping(value = "/fechamentoMaximo", method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> listFechamentoMaximo() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>()

        stockReaderService.fechamentosMaximo()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe({ v -> deferredResult.setResult(v) })

        return deferredResult
    }

    @RequestMapping(value = "/fechamentoMinimo", method = RequestMethod.GET)
    public DeferredResult<Collection<Movimento>> listFechamentoMinimo() {
        DeferredResult<Collection<Movimento>> deferredResult = new DeferredResult<>()

        stockReaderService.fechamentosMinimo()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe({ v -> deferredResult.setResult(v) })

        return deferredResult
    }

    @RequestMapping(value = "/retornoMaximo", method = RequestMethod.GET)
    public DeferredResult<Collection<Retorno>> listRetornoMaximo() {
        DeferredResult<Collection<Retorno>> deferredResult = new DeferredResult<>()

        stockReaderService.retornosMaximo()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe({ v -> deferredResult.setResult(v) })

        return deferredResult
    }

    @RequestMapping(value = "/retornoMinimo", method = RequestMethod.GET)
    public DeferredResult<Collection<Retorno>> listRetornoMinimo() {
        DeferredResult<Collection<Retorno>> deferredResult = new DeferredResult<>()

        stockReaderService.retornosMinimo()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe({ v -> deferredResult.setResult(v) }, { t -> deferredResult.setErrorResult(t) })

        return deferredResult
    }

    @RequestMapping(value = "/volumeMedio", method = RequestMethod.GET)
    public DeferredResult<Collection<Average>> listVolumeMedio() {
        DeferredResult<Collection<Average>> deferredResult = new DeferredResult<>()

        stockReaderService.volumesMedio()
                .subscribeOn(Schedulers.from(executorService))
                .subscribe({ v -> deferredResult.setResult(v) }, { t -> deferredResult.setErrorResult(t) })

        return deferredResult
    }

}
