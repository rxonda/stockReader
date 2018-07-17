package quantum.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import quantum.dao.StockDataSource
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.services.StockReaderService

import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * Created by xonda on 13/03/2015.
 */
@RestController
@RequestMapping("/stocks")
class StockRestController {
//    @Autowired
//    private StockReaderService<Stream> stockReaderService
//
//    @Autowired
//    private StockDataSource stockDataSource
//
//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody Collection<Movimento> list() {
//        stockDataSource.list().collect(Collectors.toList())
//    }
//
//    @RequestMapping(value = "/fechamentoMaximo", method = RequestMethod.GET)
//    @ResponseBody Collection<Movimento> listFechamentoMaximo() {
//        stockReaderService.fechamentosMaximo()(stockDataSource.list())
//    }
//
//    @RequestMapping(value = "/fechamentoMinimo", method = RequestMethod.GET)
//    @ResponseBody Collection<Movimento> listFechamentoMinimo() {
//        stockReaderService.fechamentosMinimo()(stockDataSource.list())
//    }
//
//    @RequestMapping(value = "/retornoMaximo", method = RequestMethod.GET)
//    @ResponseBody Collection<Movimento> listRetornoMaximo() {
//        stockReaderService.retornosMaximo()(stockDataSource.list())
//    }
//
//    @RequestMapping(value = "/retornoMinimo", method = RequestMethod.GET)
//    @ResponseBody Collection<Movimento> listRetornoMinimo() {
//        stockReaderService.retornosMinimo()(stockDataSource.list())
//    }
//
//    @RequestMapping(value = "/volumeMedio", method = RequestMethod.GET)
//    @ResponseBody Collection<Average> listVolumeMedio() {
//        stockReaderService.volumesMedio()(stockDataSource.list())
//    }
}
