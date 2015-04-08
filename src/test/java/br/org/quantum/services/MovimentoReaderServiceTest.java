package br.org.quantum.services;

import br.org.quantum.Application;
import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.VolumeMedio;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by xonda on 12/03/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class MovimentoReaderServiceTest {

    @Autowired
    private StockReaderService stockReaderService;

    @Test
    public void deveLerAListagemDeMovimento() {
        Collection<Movimento> movimentos = stockReaderService.list();
        Assert.assertEquals("A qtd de movimentos deve ser ", 11, movimentos.size());
    }

    @Test
    public void deveListarFechamentosMaximo() {
        Collection<Movimento> movimentos = stockReaderService.fechamentosMaximo();
        Assert.assertEquals("A Qtd de fechamentos deve ser ", 3, movimentos.size());
        Iterator<Movimento> it = movimentos.iterator();
        assertMovimento(it.next(), "PETR4", data("04/01/2013"), new BigDecimal("20.43"), 36141000L);
        assertMovimento(it.next(), "OGXP3", data("03/01/2013"), new BigDecimal("4.90"), 38143400L);
        assertMovimento(it.next(), "VALE5", data("02/01/2013"), new BigDecimal("42.60"), 18515700L);
    }

    @Test
    public void deveListarFechamentosMinimo() {
        Collection<Movimento> movimentos = stockReaderService.fechamentosMinimo();
        Assert.assertEquals("A Qtd de fechamentos deve ser ", 3, movimentos.size());
        Iterator<Movimento> it = movimentos.iterator();
        assertMovimento(it.next(), "PETR4", data("02/01/2013"), new BigDecimal("19.69"), 30182600L);
        assertMovimento(it.next(), "OGXP3", data("01/01/2013"), new BigDecimal("4.38"), 0L);
        assertMovimento(it.next(), "VALE5", data("01/01/2013"), new BigDecimal("40.87"), 0L);
    }

    @Test
    public void deveListarRetornoMaximo() {
        Collection<Movimento> movimentos = stockReaderService.retornosMaximo();
        Assert.assertEquals("A Qtd de fechamentos deve ser ", 3, movimentos.size());
        Iterator<Movimento> it = movimentos.iterator();
        assertMovimento(it.next(), "OGXP3", data("02/01/2013"), new BigDecimal("4.76"), 45904000L);
        assertMovimento(it.next(), "PETR4", data("03/01/2013"), new BigDecimal("20.40"), 30552600L);
        assertMovimento(it.next(), "VALE5", data("02/01/2013"), new BigDecimal("42.60"), 18515700L);
    }

    @Test
    public void deveListarRetornoMinimo() {
        Collection<Movimento> movimentos = stockReaderService.retornosMinimo();
        Assert.assertEquals("A Qtd de fechamentos deve ser ", 3, movimentos.size());
        Iterator<Movimento> it = movimentos.iterator();
        assertMovimento(it.next(), "OGXP3", data("03/01/2013"), new BigDecimal("4.90"), 38143400L);
        assertMovimento(it.next(), "PETR4", data("07/01/2013"), new BigDecimal("20.08"), 28069600L);
        assertMovimento(it.next(), "VALE5", data("04/01/2013"), new BigDecimal("41.36"), 26351900L);
    }

    @Test
    public void deveListarVolumeMedioCadaAcao() {
        Collection<VolumeMedio> movimentos = stockReaderService.volumesMedio();
        Assert.assertEquals("A Qtd de fechamentos deve ser ", 3, movimentos.size());

        Iterator<VolumeMedio> it = movimentos.iterator();
        assertVolumeMedio(it.next(), "PETR4", 31236450d);
        assertVolumeMedio(it.next(), "OGXP3", 42023700d);
        assertVolumeMedio(it.next(), "VALE5", 19956466.6666667d);
    }

    private void assertMovimento(Movimento m, String id, Date date, BigDecimal close, Long volume) {
        Assert.assertEquals("O id do movimento deve ser ", id, m.getId());
        Assert.assertEquals("A data do movimento deve ser ", date, m.getDate());
        Assert.assertEquals("O fechamento deve ser ", close, m.getClose());
        Assert.assertEquals("O volume deve ser ", volume, m.getVolume());
    }

    private void assertVolumeMedio(VolumeMedio v, String id, Double volume) {
        Assert.assertEquals("O id do movimento deve ser ", id, v.getId());
        Assert.assertEquals("O volume deve ser ", volume, v.getVolume(), 0.001d);
    }

    private Date data(String date) {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
