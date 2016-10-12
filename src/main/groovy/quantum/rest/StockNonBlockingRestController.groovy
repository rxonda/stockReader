package quantum.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import quantum.dao.StockDataSource
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import quantum.services.StockNonBlockingReader
import rx.Observable

import static quantum.rx.RxUtils.convertFromStream

/**
 * Created by xonda on 23/03/2015.
 */
@RestController
@RequestMapping("/nonblocking")
class StockNonBlockingRestController {
    @Autowired
    private StockNonBlockingReader stockReaderService

    @Autowired
    private StockDataSource stockDataSource

    @RequestMapping(method = RequestMethod.GET)
    Observable<Movimento> list() {
        convertFromStream(stockDataSource.list())
    }

    @RequestMapping(value = "/fechamentoMaximo", method = RequestMethod.GET)
    Observable<Movimento> listFechamentoMaximo() {
        applyObservable(stockReaderService.fechamentosMaximo())
    }

    @RequestMapping(value = "/fechamentoMinimo", method = RequestMethod.GET)
    Observable<Movimento> listFechamentoMinimo() {
        applyObservable(stockReaderService.fechamentosMinimo())
    }

    @RequestMapping(value = "/retornoMaximo", method = RequestMethod.GET)
    Observable<Retorno> listRetornoMaximo() {
        applyObservable(stockReaderService.retornosMaximo())
    }

    @RequestMapping(value = "/retornoMinimo", method = RequestMethod.GET)
    Observable<Retorno> listRetornoMinimo() {
        applyObservable(stockReaderService.retornosMinimo())
    }

    @RequestMapping(value = "/volumeMedio", method = RequestMethod.GET)
    Observable<Average> listVolumeMedio() {
        applyObservable(stockReaderService.volumesMedio())
    }

    private Observable applyObservable(Closure<Observable> closure) {
        closure(convertFromStream(stockDataSource.list()))
    }
}
