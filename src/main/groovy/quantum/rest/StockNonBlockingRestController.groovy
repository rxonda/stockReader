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
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by xonda on 23/03/2015.
 */
@RestController
@RequestMapping("/nonblocking")
class StockNonBlockingRestController {
    @Autowired
    private StockNonBlockingReader stockReaderService

    @RequestMapping(method = RequestMethod.GET)
    DeferredResult<Collection<Movimento>> list() {
        wrappResult(stockReaderService.list())
    }

    @RequestMapping(value = "/fechamentoMaximo", method = RequestMethod.GET)
    DeferredResult<Collection<Movimento>> listFechamentoMaximo() {
        wrappResult(stockReaderService.fechamentosMaximo())
    }

    @RequestMapping(value = "/fechamentoMinimo", method = RequestMethod.GET)
    DeferredResult<Collection<Movimento>> listFechamentoMinimo() {
        wrappResult(stockReaderService.fechamentosMinimo())
    }

    @RequestMapping(value = "/retornoMaximo", method = RequestMethod.GET)
    DeferredResult<Collection<Retorno>> listRetornoMaximo() {
        wrappResult(stockReaderService.retornosMaximo())
    }

    @RequestMapping(value = "/retornoMinimo", method = RequestMethod.GET)
    DeferredResult<Collection<Retorno>> listRetornoMinimo() {
        wrappResult(stockReaderService.retornosMinimo())
    }

    @RequestMapping(value = "/volumeMedio", method = RequestMethod.GET)
    DeferredResult<Collection<Average>> listVolumeMedio() {
        wrappResult(stockReaderService.volumesMedio())
    }

    private wrappResult = { Observable observable ->
        DeferredResult deferredResult = new DeferredResult()
        observable
                .subscribeOn(Schedulers.io())
                .toList()
                .subscribe({ v -> deferredResult.result = v }, { t -> deferredResult.errorResult = t })
        deferredResult
    }
}
