package quantum.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.DeferredResult
import quantum.dao.StockNonBlockingDatasource
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import quantum.services.StockNonBlockingReader
import rx.Observable

/**
 * Created by xonda on 23/03/2015.
 */
@RestController
@RequestMapping("/nonblocking")
class StockNonBlockingRestController {
    @Autowired
    private StockNonBlockingReader stockReaderService

    @Autowired
    private StockNonBlockingDatasource stockNonBlockingDatasource

    @RequestMapping(method = RequestMethod.GET)
    DeferredResult<Collection<Movimento>> list() {
        toBlockAndDeferred(stockNonBlockingDatasource.list())
    }

    @RequestMapping(value = "/fechamentoMaximo", method = RequestMethod.GET)
    DeferredResult<Collection<Movimento>> listFechamentoMaximo() {
        applyObservable(stockReaderService.fechamentosMaximo())
    }

    @RequestMapping(value = "/fechamentoMinimo", method = RequestMethod.GET)
    DeferredResult<Collection<Movimento>> listFechamentoMinimo() {
        applyObservable(stockReaderService.fechamentosMinimo())
    }

    @RequestMapping(value = "/retornoMaximo", method = RequestMethod.GET)
    DeferredResult<Collection<Retorno>> listRetornoMaximo() {
        applyObservable(stockReaderService.retornosMaximo())
    }

    @RequestMapping(value = "/retornoMinimo", method = RequestMethod.GET)
    DeferredResult<Collection<Retorno>> listRetornoMinimo() {
        applyObservable(stockReaderService.retornosMinimo())
    }

    @RequestMapping(value = "/volumeMedio", method = RequestMethod.GET)
    DeferredResult<Collection<Average>> listVolumeMedio() {
        applyObservable(stockReaderService.volumesMedio())
    }

    private DeferredResult<Collection> applyObservable(Closure<Observable> closure) {
        def observable = closure(stockNonBlockingDatasource.list())
        toBlockAndDeferred(observable)
    }

    private toBlockAndDeferred = { Observable observable ->
        DeferredResult<Collection> deferredResult = new DeferredResult<>()
        observable
                .toList()
                .subscribe({ v -> deferredResult.result = v }, { t -> deferredResult.errorResult = t })
        deferredResult
    }
}
