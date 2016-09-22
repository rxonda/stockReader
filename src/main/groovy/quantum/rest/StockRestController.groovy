package quantum.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import quantum.domain.Movimento
import quantum.domain.VolumeMedio
import quantum.services.StockReaderService

/**
 * Created by xonda on 13/03/2015.
 */
@RestController
@RequestMapping("/stocks")
class StockRestController {
    @Autowired
    private StockReaderService stockReaderService

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody Collection<Movimento> list() {
        stockReaderService.list()
    }

    @RequestMapping(value = "/fechamentoMaximo", method = RequestMethod.GET)
    @ResponseBody Collection<Movimento> listFechamentoMaximo() {
        stockReaderService.fechamentosMaximo()
    }

    @RequestMapping(value = "/fechamentoMinimo", method = RequestMethod.GET)
    @ResponseBody Collection<Movimento> listFechamentoMinimo() {
        stockReaderService.fechamentosMinimo()
    }

    @RequestMapping(value = "/retornoMaximo", method = RequestMethod.GET)
    @ResponseBody Collection<Movimento> listRetornoMaximo() {
        stockReaderService.retornosMaximo()
    }

    @RequestMapping(value = "/retornoMinimo", method = RequestMethod.GET)
    @ResponseBody Collection<Movimento> listRetornoMinimo() {
        stockReaderService.retornosMinimo()
    }

    @RequestMapping(value = "/volumeMedio", method = RequestMethod.GET)
    @ResponseBody Collection<VolumeMedio> listVolumeMedio() {
        stockReaderService.volumesMedio()
    }
}
