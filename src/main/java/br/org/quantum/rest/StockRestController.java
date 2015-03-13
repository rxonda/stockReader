package br.org.quantum.rest;

import br.org.quantum.domain.Stock;
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
//@RestController("/stocks")
public class StockRestController {
    @Autowired
    private StockReaderService stockReaderService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Collection<Stock> list() {
        return stockReaderService.list();
    }

}
