package br.org.quantum.rest;

import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.VolumeMedio;
import br.org.quantum.services.StockReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by xonda on 13/03/2015.
 */
@RestController
@RequestMapping("/stocks")
public class StockRestController {
    @Autowired
    private StockReaderService stockReaderService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Collection<Movimento> list() {
        return stockReaderService.list();
    }

    @RequestMapping(value = "/fechamentoMaximo", method = RequestMethod.GET)
    public @ResponseBody Collection<Movimento> listFechamentoMaximo() {
        return stockReaderService.fechamentosMaximo();
    }

    @RequestMapping(value = "/fechamentoMinimo", method = RequestMethod.GET)
    public @ResponseBody Collection<Movimento> listFechamentoMinimo() {
        return stockReaderService.fechamentosMinimo();
    }

    @RequestMapping(value = "/retornoMaximo", method = RequestMethod.GET)
    public @ResponseBody Collection<Movimento> listRetornoMaximo() {
        return stockReaderService.retornosMaximo();
    }

    @RequestMapping(value = "/retornoMinimo", method = RequestMethod.GET)
    public @ResponseBody Collection<Movimento> listRetornoMinimo() {
        return stockReaderService.retornosMinimo();
    }

    @RequestMapping(value = "/volumeMedio", method = RequestMethod.GET)
    public @ResponseBody Collection<VolumeMedio> listVolumeMedio() {
        return stockReaderService.volumesMedio();
    }
}
