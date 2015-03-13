package br.org.quantum.services;

import br.org.quantum.Application;
import br.org.quantum.domain.Stock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

/**
 * Created by xonda on 12/03/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class StockReaderServiceTest {

    @Autowired
    private StockReaderService stockReaderService;

    @Test
    public void deveLerAListagemDeStock() {
        Collection<Stock> stocks = stockReaderService.list();
        Assert.assertEquals("A qtd de stocks deve ser ", 2290, stocks.size());
    }
}
