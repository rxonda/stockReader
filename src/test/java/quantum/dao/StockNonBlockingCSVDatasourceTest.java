package quantum.dao;


import quantum.Application;
import quantum.domain.Movimento;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.observers.TestSubscriber;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class StockNonBlockingCSVDatasourceTest {

    @Autowired
    private StockNonBlockingDatasource stockNonBlockingDatasource;

    @Test
    public void deveListarMovimentacao() {
        TestSubscriber<Movimento> subscriber = new TestSubscriber<>();
        stockNonBlockingDatasource.list().subscribe(subscriber);

        List<Movimento> col = subscriber.getOnNextEvents();

        List<Throwable> errors = subscriber.getOnErrorEvents();

        Assert.assertEquals("A qtd de erros deve ser ", 0, errors.size());

        assertMovimento(col.get(0), "OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L);
        assertMovimento(col.get(1), "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L);
        assertMovimento(col.get(2), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L);
        assertMovimento(col.get(3), "PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L);
        assertMovimento(col.get(4), "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L);
        assertMovimento(col.get(5), "PETR4", data("2013-01-04"), new BigDecimal("20.43"), 36141000L);
        assertMovimento(col.get(6), "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L);
        assertMovimento(col.get(7), "VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L);
        assertMovimento(col.get(8), "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L);
        assertMovimento(col.get(9), "VALE5", data("2013-01-03"), new BigDecimal("42.09"), 15001800L);
        assertMovimento(col.get(10), "VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L);
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