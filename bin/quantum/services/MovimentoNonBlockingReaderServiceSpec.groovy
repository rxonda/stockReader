package quantum.services

import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import rx.observers.TestSubscriber
import spock.lang.Specification

import static quantum.TestUtils.assertMovimento
import static quantum.TestUtils.assertRetorno
import static quantum.TestUtils.assertVolumeMedio
import static quantum.TestUtils.data
import static quantum.rx.RxUtils.convertFromStream

/**
 * Created by xonda on 08/04/2015.
 */
class MovimentoNonBlockingReaderServiceSpec extends Specification {
    
//    private StockNonBlockingReader stockNonBlockingReader
//
//    private StockDataSource stockDataSource
//
//    void "Deve Listar Fechamentos Maximo"() {
//        setup:
//        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()
//
//        when:
//        stockNonBlockingReader.fechamentosMaximo()(convertFromStream(stockDataSource.list())).subscribe(mockSubscriber)
//
//        then:
//        List<Movimento> movimentos = mockSubscriber.getOnNextEvents()
//
//        assert 3 == movimentos.size(), 'A Qtd de fechamentos deve ser'
//
//        assertMovimento(movimentos[0], "PETR4", data('2013-01-04'), new BigDecimal("20.43"), 36141000L)
//        assertMovimento(movimentos[1], "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
//        assertMovimento(movimentos[2], "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
//    }
//
//    void "Deve Listar Fechamentos Minimo"() {
//        setup:
//        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()
//
//        when:
//        stockNonBlockingReader.fechamentosMinimo()(convertFromStream(stockDataSource.list())).subscribe(mockSubscriber)
//
//        then:
//        List<Movimento> movimentos = mockSubscriber.getOnNextEvents()
//
//        assert 3 == movimentos.size(), 'A Qtd de fechamentos deve ser'
//
//        assertMovimento(movimentos[0], "PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L)
//        assertMovimento(movimentos[1], "OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L)
//        assertMovimento(movimentos[2], "VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L)
//    }
//
//    void "Deve Listar Retorno Maximo"() {
//        setup:
//        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()
//
//        when:
//        stockNonBlockingReader.retornosMaximo()(convertFromStream(stockDataSource.list())).subscribe(mockSubscriber)
//
//        then:
//        List<Retorno> retornos = mockSubscriber.getOnNextEvents()
//
//        assert 3 == retornos.size(), 'A Qtd de fechamentos deve ser '
//
//        assertRetorno(retornos[0], "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L)
//        assertRetorno(retornos[1], "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L)
//        assertRetorno(retornos[2], "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
//    }
//
//    void "Deve Listar Retorno Minimo"() {
//        setup:
//        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()
//
//        when:
//        stockNonBlockingReader.retornosMinimo()(convertFromStream(stockDataSource.list())).subscribe(mockSubscriber)
//
//        then:
//        List<Retorno> retornos = mockSubscriber.getOnNextEvents()
//
//        assert 3 == retornos.size(), 'A Qtd de fechamentos deve ser'
//
//        assertRetorno(retornos[0], "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L)
//        assertRetorno(retornos[1], "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
//        assertRetorno(retornos[2], "VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L)
//    }
//
//    void "Deve Listar Volume Medio Cada Acao"() {
//        setup:
//        TestSubscriber<Collection> mockSubscriber = new TestSubscriber<>()
//
//        when:
//        stockNonBlockingReader.volumesMedio()(convertFromStream(stockDataSource.list())).subscribe(mockSubscriber)
//
//        then:
//        List<Average> averages = mockSubscriber.getOnNextEvents()
//
//        assert 3 == averages.size(), 'A Qtd de fechamentos deve ser '
//
//        assertVolumeMedio(averages[0], "PETR4", 31236450d)
//        assertVolumeMedio(averages[1], "OGXP3", 42023700d)
//        assertVolumeMedio(averages[2], "VALE5", 19956466.6666667d)
//    }
}
