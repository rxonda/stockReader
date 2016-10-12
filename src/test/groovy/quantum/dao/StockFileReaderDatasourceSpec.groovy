package quantum.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import quantum.Application
import quantum.domain.Movimento
import spock.lang.Specification

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.stream.Stream

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

        assertMovimento(col.next(), "OGXP3", "2013-01-01", "4.38", 0L)
        assertMovimento(col.next(), "OGXP3", "2013-01-02", "4.76", 45904000L)
        assertMovimento(col.next(), "OGXP3", "2013-01-03", "4.90", 38143400L)
        assertMovimento(col.next(), "PETR4", "2013-01-02", "19.69", 30182600L)
        assertMovimento(col.next(), "PETR4", "2013-01-03", "20.40", 30552600L)
        assertMovimento(col.next(), "PETR4", "2013-01-04", "20.43", 36141000L)
        assertMovimento(col.next(), "PETR4", "2013-01-07", "20.08", 28069600L)
        assertMovimento(col.next(), "VALE5", "2013-01-01", "40.87", 0L)
        assertMovimento(col.next(), "VALE5", "2013-01-02", "42.60", 18515700L)
        assertMovimento(col.next(), "VALE5", "2013-01-03", "42.09", 15001800L)
        assertMovimento(col.next(), "VALE5", "2013-01-04", "41.36", 26351900L)
    }

    private void assertMovimento(Movimento m, String id, String date, String close, Long volume) {
        assert id == m.id, 'O id do movimento deve ser'
        assert data(date) == m.date, 'A data do movimento deve ser'
        assert new BigDecimal(close) == m.close, 'O fechamento deve ser'
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