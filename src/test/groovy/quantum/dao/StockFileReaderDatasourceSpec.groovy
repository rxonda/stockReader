package quantum.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import quantum.Application
import quantum.domain.Movimento
import spock.lang.Specification

import java.util.stream.Stream

import static quantum.TestUtils.assertMovimento
import static quantum.TestUtils.data

@ContextConfiguration
@SpringBootTest(classes = Application.class)
class StockFileReaderDatasourceSpec extends Specification {

    @Autowired
    StockDataSource stockDataSource

    def "deve Listar Movimentacao"() {
        when:
        Stream<Movimento> movimentos = stockDataSource.list()

        then:
        notThrown(Throwable)

        and:
        Iterator<Movimento> col = movimentos.iterator()

        assertMovimento(col.next(), "OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L)
        assertMovimento(col.next(), "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L)
        assertMovimento(col.next(), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
        assertMovimento(col.next(), "PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L)
        assertMovimento(col.next(), "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L)
        assertMovimento(col.next(), "PETR4", data("2013-01-04"), new BigDecimal("20.43"), 36141000L)
        assertMovimento(col.next(), "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L)
        assertMovimento(col.next(), "VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L)
        assertMovimento(col.next(), "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
        assertMovimento(col.next(), "VALE5", data("2013-01-03"), new BigDecimal("42.09"), 15001800L)
        assertMovimento(col.next(), "VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L)
        assert !col.hasNext()
    }

    def "deve Listar primeira pagina Movimentacao com 5 linhas"() {
        when:
        Stream<Movimento> movimentos = stockDataSource.list([start: 0, offset: 5])

        then:
        notThrown(Throwable)

        and:
        Iterator<Movimento> col = movimentos.iterator()

        assertMovimento(col.next(), "OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L)
        assertMovimento(col.next(), "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L)
        assertMovimento(col.next(), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
        assertMovimento(col.next(), "PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L)
        assertMovimento(col.next(), "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L)
        assert !col.hasNext()
    }

    def "deve Listar segunda pagina Movimentacao com 3 linhas"() {
        when:
        Stream<Movimento> movimentos = stockDataSource.list([start: 5, offset: 3])

        then:
        notThrown(Throwable)

        and:
        Iterator<Movimento> col = movimentos.iterator()

        assertMovimento(col.next(), "PETR4", data("2013-01-04"), new BigDecimal("20.43"), 36141000L)
        assertMovimento(col.next(), "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L)
        assertMovimento(col.next(), "VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L)
        assert !col.hasNext()
    }
}