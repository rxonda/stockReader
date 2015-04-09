package br.org.quantum.services;

import br.org.quantum.Application;
import br.org.quantum.domain.Average;
import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.Retorno;
import br.org.quantum.domain.VolumeMedio;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.observers.TestSubscriber;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by xonda on 08/04/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class MovimentoNonBlockingReaderServiceTest {
    @Autowired
    private StockNonBlockingReader stockNonBlockingReader;

    @Test
    public void deveListarFechamentosMaximo() {
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>();
        stockNonBlockingReader.fechamentosMaximo().subscribe(mockSubscriber);

        Iterator<Collection> itt = mockSubscriber.getOnNextEvents().iterator();

        Collection col = itt.next();
        Assert.assertEquals("A Qtd de fechamentos deve ser ", 3, col.size());
        Iterator<Movimento> it = col.iterator();
        assertMovimento(it.next(), "PETR4", data("2013-01-04"), new BigDecimal("20.43"), 36141000L);
        assertMovimento(it.next(), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L);
        assertMovimento(it.next(), "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L);
    }

    @Test
    public void deveListarFechamentosMinimo() {
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>();
        stockNonBlockingReader.fechamentosMinimo().subscribe(mockSubscriber);

        Iterator<Collection> itt = mockSubscriber.getOnNextEvents().iterator();

        Collection col = itt.next();
        Assert.assertEquals("A Qtd de fechamentos deve ser ", 3, col.size());
        Iterator<Movimento> it = col.iterator();
        assertMovimento(it.next(), "PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L);
        assertMovimento(it.next(), "OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L);
        assertMovimento(it.next(), "VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L);
    }

    @Test
    public void deveListarRetornoMaximo() {
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>();
        stockNonBlockingReader.retornosMaximo().subscribe(mockSubscriber);

        Iterator<Collection> itt = mockSubscriber.getOnNextEvents().iterator();

        Collection col = itt.next();
        Assert.assertEquals("A Qtd de fechamentos deve ser ", 3, col.size());
        Iterator<Retorno> it = col.iterator();
        assertRetorno(it.next(), "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L);
        assertRetorno(it.next(), "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L);
        assertRetorno(it.next(), "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L);
    }

    @Test
    public void deveListarRetornoMinimo() {
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>();
        stockNonBlockingReader.retornosMinimo().subscribe(mockSubscriber);

        Iterator<Collection> itt = mockSubscriber.getOnNextEvents().iterator();

        Collection col = itt.next();
        Assert.assertEquals("A Qtd de fechamentos deve ser ", 3, col.size());
        Iterator<Retorno> it = col.iterator();
        assertRetorno(it.next(), "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L);
        assertRetorno(it.next(), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L);
        assertRetorno(it.next(), "VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L);
    }

    @Test
    public void deveListarVolumeMedioCadaAcao() {

        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>();
        stockNonBlockingReader.volumesMedio().subscribe(mockSubscriber);

        Iterator<Collection> itt = mockSubscriber.getOnNextEvents().iterator();

        Collection col = itt.next();
        Assert.assertEquals("A Qtd de fechamentos deve ser ", 3, col.size());
        Iterator<Average> it = col.iterator();
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

    private void assertRetorno(Retorno r, String id, Date date, BigDecimal close, Long volume) {
        Assert.assertEquals("O id do movimento deve ser ", id, r.getId());
        Assert.assertEquals("A data do movimento deve ser ", date, r.getDate());
        Assert.assertEquals("O fechamento deve ser ", close, r.getClose());
        Assert.assertEquals("O volume deve ser ", volume, r.getVolume());
    }

    private void assertVolumeMedio(Average v, String id, Double volume) {
        Assert.assertEquals("O id do movimento deve ser ", id, v.getId());
        Assert.assertEquals("O volume deve ser ", volume, v.getVolume(), 0.001d);
    }

    private Date data(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
