package quantum.services

import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import quantum.Application
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import rx.observers.TestSubscriber
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

    void "Deve Listar Fechamentos Maximo"() {
        setup:
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()

        when:
        stockNonBlockingReader.fechamentosMaximo().subscribe(mockSubscriber)

        then:
        Iterator<Collection> itt = mockSubscriber.getOnNextEvents().iterator()

        Collection col = itt.next()

        assert 3 == col.size(), 'A Qtd de fechamentos deve ser'

        Iterator<Movimento> it = col.iterator()
        assertMovimento(it.next(), "PETR4", data('2013-01-04'), new BigDecimal("20.43"), 36141000L)
        assertMovimento(it.next(), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
        assertMovimento(it.next(), "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
    }

    void "Deve Listar Fechamentos Minimo"() {
        setup:
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()

        when:
        stockNonBlockingReader.fechamentosMinimo().subscribe(mockSubscriber)

        then:
        Iterator<Collection> itt = mockSubscriber.getOnNextEvents().iterator()

        Collection col = itt.next()
        assert 3 == col.size(), 'A Qtd de fechamentos deve ser'

        Iterator<Movimento> it = col.iterator()
        assertMovimento(it.next(), "PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L)
        assertMovimento(it.next(), "OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L)
        assertMovimento(it.next(), "VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L)
    }

    void "Deve Listar Retorno Maximo"() {
        setup:
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()

        when:
        stockNonBlockingReader.retornosMaximo().subscribe(mockSubscriber)

        then:
        Iterator<Collection> itt = mockSubscriber.getOnNextEvents().iterator()

        Collection col = itt.next()
        assert 3 == col.size(), 'A Qtd de fechamentos deve ser '

        Iterator<Retorno> it = col.iterator()
        assertRetorno(it.next(), "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L)
        assertRetorno(it.next(), "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L)
        assertRetorno(it.next(), "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
    }

    void "Deve Listar Retorno Minimo"() {
        setup:
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()

        when:
        stockNonBlockingReader.retornosMinimo().subscribe(mockSubscriber)

        then:
        Iterator<Collection> itt = mockSubscriber.getOnNextEvents().iterator()

        Collection col = itt.next()
        assert 3 == col.size(), 'A Qtd de fechamentos deve ser'

        Iterator<Retorno> it = col.iterator()
        assertRetorno(it.next(), "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L)
        assertRetorno(it.next(), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
        assertRetorno(it.next(), "VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L)
    }

    void "Deve Listar Volume Medio Cada Acao"() {
        setup:
        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()

        when:
        stockNonBlockingReader.volumesMedio().subscribe(mockSubscriber)

        then:
        Iterator<Collection> itt = mockSubscriber.getOnNextEvents().iterator()

        Collection col = itt.next()
        assert 3 == col.size(), 'A Qtd de fechamentos deve ser '
        
        Iterator<Average> it = col.iterator()
        assertVolumeMedio(it.next(), "PETR4", 31236450d)
        assertVolumeMedio(it.next(), "OGXP3", 42023700d)
        assertVolumeMedio(it.next(), "VALE5", 19956466.6666667d)
    }

    private void assertMovimento(Movimento m, String id, Date date, BigDecimal close, Long volume) {
        assert id == m.getId(), 'O id do movimento deve ser'
        assert date == m.getDate(), 'A data do movimento deve ser'
        assert close == m.getClose(), 'O fechamento deve ser '
        assert volume == m.getVolume(), 'O volume deve ser '
    }

    private void assertRetorno(Retorno r, String id, Date date, BigDecimal close, Long volume) {
        assert id == r.getId(), 'O id do movimento deve ser'
        assert date == r.getDate(), 'A data do movimento deve ser'
        assert close == r.getClose(), 'O fechamento deve ser '
        assert volume == r.getVolume(), 'O volume deve ser'
    }

    private void assertVolumeMedio(Average v, String id, Double volume) {
        assert id == v.getId(), 'O id do movimento deve ser '
        Assert.assertEquals('O volume deve ser ', volume, v.getVolume(), 0.001d)
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
