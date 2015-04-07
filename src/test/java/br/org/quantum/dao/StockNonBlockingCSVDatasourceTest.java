package br.org.quantum.dao;


import br.org.quantum.Application;
import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.Retorno;
import br.org.quantum.services.StockNonBlockingReader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observers.TestSubscriber;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import static java.util.Comparator.comparing;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class StockNonBlockingCSVDatasourceTest {

    @Autowired
    private StockNonBlockingDatasource stockNonBlockingDatasource;

    @Autowired
    private StockNonBlockingReader stockNonBlockingReader;

    @Test
    public void deveListarMovimentacao() {
        Subscriber<Movimento> subscriber = Mockito.mock(Subscriber.class);
        stockNonBlockingDatasource.list().subscribe(subscriber);

        ArgumentCaptor<Movimento> movimentoArgumentCaptor = ArgumentCaptor.forClass(Movimento.class);
        ArgumentCaptor<Throwable> throwableArgumentCaptor = ArgumentCaptor.forClass(Throwable.class);

        Mockito.verify(subscriber, Mockito.times(1)).onStart();
        Mockito.verify(subscriber, Mockito.times(11)).onNext(movimentoArgumentCaptor.capture());
        Mockito.verify(subscriber, Mockito.times(1)).onCompleted();
        Mockito.verify(subscriber, Mockito.times(0)).onError(throwableArgumentCaptor.capture());

        assertMovimento(movimentoArgumentCaptor.getAllValues().get(0), "OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L);
        assertMovimento(movimentoArgumentCaptor.getAllValues().get(1), "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L);
        assertMovimento(movimentoArgumentCaptor.getAllValues().get(2), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L);
        assertMovimento(movimentoArgumentCaptor.getAllValues().get(3), "PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L);
        assertMovimento(movimentoArgumentCaptor.getAllValues().get(4), "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L);
        assertMovimento(movimentoArgumentCaptor.getAllValues().get(5), "PETR4", data("2013-01-04"), new BigDecimal("20.43"), 36141000L);
        assertMovimento(movimentoArgumentCaptor.getAllValues().get(6), "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L);
        assertMovimento(movimentoArgumentCaptor.getAllValues().get(7), "VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L);
        assertMovimento(movimentoArgumentCaptor.getAllValues().get(8), "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L);
        assertMovimento(movimentoArgumentCaptor.getAllValues().get(9), "VALE5", data("2013-01-03"), new BigDecimal("42.09"), 15001800L);
        assertMovimento(movimentoArgumentCaptor.getAllValues().get(10), "VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L);
    }

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
        Iterator<Movimento> it = col.iterator();
        assertMovimento(it.next(), "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L);
        assertMovimento(it.next(), "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L);
        assertMovimento(it.next(), "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L);
    }

    @Test
    public void deveListarRetornoMinimo() {
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>();
        stockNonBlockingReader.retornosMinimo().subscribe(mockSubscriber);

        Iterator<Collection> itt = mockSubscriber.getOnNextEvents().iterator();

        Collection col = itt.next();
        Assert.assertEquals("A Qtd de fechamentos deve ser ", 3, col.size());
        Iterator<Movimento> it = col.iterator();
        assertMovimento(it.next(), "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L);
        assertMovimento(it.next(), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L);
        assertMovimento(it.next(), "VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L);
    }



    @Test
    public void teste() {
        Observable<Movimento> o1 = stockNonBlockingDatasource.list();
        Observable.zip(o1, o1.skip(1), (o, p) -> new Retorno(p, o))
                .filter(r -> r.isValid())
                .groupBy(r -> r.getCorrente().getId())
                .flatMap(g -> g.reduce(new Retorno(), (r1, r2) -> !r1.isValid() ? r2 : comparing(Retorno::getValor).compare(r1, r2) < 0 ? r2 : r1))
                .flatMap(r -> Observable.just(r.getCorrente()))
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("completou");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(Object o) {
                        Movimento r = (Movimento) o;
                        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                        System.out.println(String.format("%s %s %f", r.getId(), fmt.format(r.getDate()), r.getClose().doubleValue()));
                    }
                });
    }

    private void assertMovimento(Movimento m, String id, Date date, BigDecimal close, Long volume) {
        Assert.assertEquals("O id do movimento deve ser ", id, m.getId());
        Assert.assertEquals("A data do movimento deve ser ", date, m.getDate());
        Assert.assertEquals("O fechamento deve ser ", close, m.getClose());
        Assert.assertEquals("O volume deve ser ", volume, m.getVolume());
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