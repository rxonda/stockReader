package quantum.services

import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import quantum.Application
import quantum.dao.StockNonBlockingDatasource
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import spock.lang.Specification

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by xonda on 08/04/2015.
 */
@ContextConfiguration
@SpringBootTest(classes = Application.class)
class MovimentoNonBlockingReaderServiceSpec extends Specification {
    
    @Autowired
    private StockNonBlockingReader stockNonBlockingReader

    @Autowired
    private StockNonBlockingDatasource stockNonBlockingDatasource

    void "Deve Listar Fechamentos Maximo"() {
        setup:
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()

        when:
        stockNonBlockingReader.fechamentosMaximo()(stockNonBlockingDatasource.list()).subscribe(mockSubscriber)

        then:
        List<Movimento> movimentos = mockSubscriber.getOnNextEvents()

        assert 3 == movimentos.size(), 'A Qtd de fechamentos deve ser'

        assertMovimento(movimentos[0], "PETR4", data('2013-01-04'), new BigDecimal("20.43"), 36141000L)
        assertMovimento(movimentos[1], "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
        assertMovimento(movimentos[2], "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
    }

    void "Deve Listar Fechamentos Minimo"() {
        setup:
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()

        when:
        stockNonBlockingReader.fechamentosMinimo()(stockNonBlockingDatasource.list()).subscribe(mockSubscriber)

        then:
        List<Movimento> movimentos = mockSubscriber.getOnNextEvents()

        assert 3 == movimentos.size(), 'A Qtd de fechamentos deve ser'

        assertMovimento(movimentos[0], "PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L)
        assertMovimento(movimentos[1], "OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L)
        assertMovimento(movimentos[2], "VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L)
    }

    void "Deve Listar Retorno Maximo"() {
        setup:
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()

        when:
        stockNonBlockingReader.retornosMaximo()(stockNonBlockingDatasource.list()).subscribe(mockSubscriber)

        then:
        List<Retorno> retornos = mockSubscriber.getOnNextEvents()

        assert 3 == retornos.size(), 'A Qtd de fechamentos deve ser '

        assertRetorno(retornos[0], "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L)
        assertRetorno(retornos[1], "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L)
        assertRetorno(retornos[2], "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
    }

    void "Deve Listar Retorno Minimo"() {
        setup:
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()

        when:
        stockNonBlockingReader.retornosMinimo()(stockNonBlockingDatasource.list()).subscribe(mockSubscriber)

        then:
        List<Retorno> retornos = mockSubscriber.getOnNextEvents()

        assert 3 == retornos.size(), 'A Qtd de fechamentos deve ser'

        assertRetorno(retornos[0], "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L)
        assertRetorno(retornos[1], "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
        assertRetorno(retornos[2], "VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L)
    }

    void "Deve Listar Volume Medio Cada Acao"() {
        setup:
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()

        when:
        stockNonBlockingReader.volumesMedio()(stockNonBlockingDatasource.list()).subscribe(mockSubscriber)

        then:
        List<Average> averages = mockSubscriber.getOnNextEvents()

        assert 3 == averages.size(), 'A Qtd de fechamentos deve ser '
        
        assertVolumeMedio(averages[0], "PETR4", 31236450d)
        assertVolumeMedio(averages[1], "OGXP3", 42023700d)
        assertVolumeMedio(averages[2], "VALE5", 19956466.6666667d)
    }

    private void assertMovimento(Movimento m, String id, Date date, BigDecimal close, Long volume) {
        assert id == m.id, 'O id do movimento deve ser'
        assert date == m.date, 'A data do movimento deve ser'
        assert close == m.close, 'O fechamento deve ser '
        assert volume == m.volume, 'O volume deve ser '
    }

    private void assertRetorno(Retorno r, String id, Date date, BigDecimal close, Long volume) {
        assert id == r.id, 'O id do movimento deve ser'
        assert date == r.date, 'A data do movimento deve ser'
        assert close == r.close, 'O fechamento deve ser '
        assert volume == r.volume, 'O volume deve ser'
    }

    private void assertVolumeMedio(Average v, String id, Double volume) {
        assert id == v.id, 'O id do movimento deve ser '
        Assert.assertEquals('O volume deve ser ', volume, v.volume, 0.001d)
    }

    private Date data(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd")
        try {
            return fmt.parse(date)
        } catch (ParseException e) {
            throw new RuntimeException(e)
        }
    }
}
