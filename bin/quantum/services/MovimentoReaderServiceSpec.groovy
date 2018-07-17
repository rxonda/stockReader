package quantum.services

import quantum.dao.StockDataSource
import quantum.dao.StockFileReaderDataSource
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import spock.lang.Specification

import java.util.stream.Stream

import static quantum.TestUtils.assertMovimento
import static quantum.TestUtils.assertRetorno
import static quantum.TestUtils.assertVolumeMedio
import static quantum.TestUtils.data

/**
 * Created by xonda on 12/03/2015.
 */
class MovimentoReaderServiceSpec extends Specification {

    private StockReaderService stockReaderService = new StockReaderServiceImpl()

    private StockDataSource stockDataSource  = new StockFileReaderDataSource()

    void "deve Listar Fechamentos Maximo"() {
        when:
        Stream<Movimento> movimentos = stockReaderService.fechamentosMaximo().apply(stockDataSource.list())

        then:
        Iterator<Movimento> it = movimentos.iterator()
        assertMovimento(it.next(), "PETR4", data("2013-01-04"), new BigDecimal("20.43"), 36141000L)
        assertMovimento(it.next(), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
        assertMovimento(it.next(), "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
    }

    void "deve Listar Fechamentos Minimo"() {
        when:
        Stream<Movimento> movimentos = stockReaderService.fechamentosMinimo().apply(stockDataSource.list())
        
        then:
        Iterator<Movimento> it = movimentos.iterator()
        assertMovimento(it.next(), "PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L)
        assertMovimento(it.next(), "OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L)
        assertMovimento(it.next(), "VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L)
    }

    void "deve Listar Retorno Maximo"() {
        when:
        Stream<Retorno> retornos = stockReaderService.retornosMaximo().apply(stockDataSource.list())
        
        then:
        Iterator<Optional<Retorno>> it = retornos.iterator()
        assertRetorno(it.next(), "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L)
        assertRetorno(it.next(), "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L)
        assertRetorno(it.next(), "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
    }

    void "deve Listar Retorno Minimo"() {
        when:
        Stream<Retorno> retornos = stockReaderService.retornosMinimo().apply(stockDataSource.list())
        
        then:
        Iterator<Optional<Retorno>> it = retornos.iterator();
        assertRetorno(it.next(), "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L)
        assertRetorno(it.next(), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
        assertRetorno(it.next(), "VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L)
    }

    void "deve Listar Volume Medio Cada Acao"() {
        when:
        Stream<Average> movimentos = stockReaderService.volumesMedio().apply(stockDataSource.list())
        
        then:
        Iterator<Average> it = movimentos.iterator()
        assertVolumeMedio(it.next(), "PETR4", 31236450d)
        assertVolumeMedio(it.next(), "OGXP3", 42023700d)
        assertVolumeMedio(it.next(), "VALE5", 19956466.6666667d)
    }
}
