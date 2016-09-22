package quantum.dao

import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import quantum.Application
import quantum.domain.Movimento
import rx.observers.TestSubscriber
import spock.lang.Specification

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

@ContextConfiguration
@SpringBootTest(classes = Application.class)
class StockNonBlockingCSVDatasourceSpec extends Specification {

    @Autowired
    StockNonBlockingDatasource stockNonBlockingDatasource

    def "deve Listar Movimentacao"() {
        setup:
        TestSubscriber<Movimento> subscriber = new TestSubscriber<>()

        when:
        stockNonBlockingDatasource.list().subscribe(subscriber)

        then:
        List<Movimento> col = subscriber.getOnNextEvents()

        List<Throwable> errors = subscriber.getOnErrorEvents()

        Assert.assertEquals("A qtd de erros deve ser ", 0, errors.size())

        assertMovimento(col[0], "OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L)
        assertMovimento(col[1], "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L)
        assertMovimento(col[2], "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
        assertMovimento(col[3], "PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L)
        assertMovimento(col[4], "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L)
        assertMovimento(col[5], "PETR4", data("2013-01-04"), new BigDecimal("20.43"), 36141000L)
        assertMovimento(col[6], "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L)
        assertMovimento(col[7], "VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L)
        assertMovimento(col[8], "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
        assertMovimento(col[9], "VALE5", data("2013-01-03"), new BigDecimal("42.09"), 15001800L)
        assertMovimento(col[10], "VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L)
    }

    private void assertMovimento(Movimento m, String id, Date date, BigDecimal close, Long volume) {
        assert id == m.id, 'O id do movimento deve ser'
        assert date == m.date, 'A data do movimento deve ser'
        assert close == m.close, 'O fechamento deve ser'
        assert volume == m.volume, 'O volume deve ser'
    }

    private Date data(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd")
        try {
            fmt.parse(date)
        } catch (ParseException e) {
            throw new RuntimeException(e)
        }
    }
}