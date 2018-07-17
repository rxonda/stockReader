package quantum.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import quantum.dao.StockDataSource
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import quantum.services.StockReaderService
import rx.Observable

import static quantum.rx.RxUtils.convertFromStream

/**
 * Created by xonda on 23/03/2015.
 */
@RestController
@RequestMapping("/nonblocking")
class StockNonBlockingRestController {

    private static final long PAGE_SIZE = 10L

//    @Autowired
//    private StockReaderService<Observable> stockReaderService
//
//    @Autowired
//    private StockDataSource stockDataSource
//
//    @RequestMapping(method = RequestMethod.GET)
//    Observable<Movimento> list(@RequestParam Map<String, String> params) {
//        convertFromStream(stockDataSource.list(convertRequestPagination(params)))
//    }
//
//    @RequestMapping(value = "/fechamentoMaximo", method = RequestMethod.GET)
//    Observable<Movimento> listFechamentoMaximo() {
//        applyObservable(stockReaderService.fechamentosMaximo())
//    }
//
//    @RequestMapping(value = "/fechamentoMinimo", method = RequestMethod.GET)
//    Observable<Movimento> listFechamentoMinimo() {
//        applyObservable(stockReaderService.fechamentosMinimo())
//    }
//
//    @RequestMapping(value = "/retornoMaximo", method = RequestMethod.GET)
//    Observable<Retorno> listRetornoMaximo() {
//        applyObservable(stockReaderService.retornosMaximo())
//    }
//
//    @RequestMapping(value = "/retornoMinimo", method = RequestMethod.GET)
//    Observable<Retorno> listRetornoMinimo() {
//        applyObservable(stockReaderService.retornosMinimo())
//    }
//
//    @RequestMapping(value = "/volumeMedio", method = RequestMethod.GET)
//    Observable<Average> listVolumeMedio() {
//        applyObservable(stockReaderService.volumesMedio())
//    }
//
//    private Observable applyObservable(Closure<Observable> closure) {
//        closure(convertFromStream(stockDataSource.list()))
//    }
//
//    private static Map convertRequestPagination(Map<String, String> params) {
//        Map result = [:]
//        if(params.start){
//            result.start = Long.parseLong(params.start)
//            result.offset = (params.offset) ? Long.parseLong(params.offset) : PAGE_SIZE
//        } else if(params.number) {
//            long pageNumber = Long.parseLong(params.number)
//            long pageSize = (params.size) ? Long.parseLong(params.size) : PAGE_SIZE
//            result.start = pageNumber * pageSize
//            result.offset = pageSize
//        }
//
//        result
//    }
}
