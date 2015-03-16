package br.org.quantum.rest;

import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.VolumeMedio;
import br.org.quantum.services.StockReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by xonda on 13/03/2015.
 */
@RestController
public class StockRestController {
    @Autowired
    private StockReaderService stockReaderService;

    @RequestMapping("/all")
    public @ResponseBody Collection<Movimento> list() {
        return stockReaderService.list();
    }

    @RequestMapping("/fechamentoMaximo")
    public @ResponseBody Collection<Movimento> listFechamentoMaximo() {
        return stockReaderService.fechamentosMaximo();
    }

    @RequestMapping("/fechamentoMinimo")
    public @ResponseBody Collection<Movimento> listFechamentoMinimo() {
        return stockReaderService.fechamentosMinimo();
    }

    @RequestMapping("/retornoMaximo")
    public @ResponseBody Collection<Movimento> listRetornoMaximo() {
        return stockReaderService.retornosMaximo();
    }

    @RequestMapping("/retornoMinimo")
    public @ResponseBody Collection<Movimento> listRetornoMinimo() {
        return stockReaderService.retornosMinimo();
    }

    @RequestMapping("/volumeMedio")
    public @ResponseBody Collection<VolumeMedio> listVolumeMedio() {
        return stockReaderService.volumesMedio();
    }
}
