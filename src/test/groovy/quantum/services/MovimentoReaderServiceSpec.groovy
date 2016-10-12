package quantum.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import quantum.Application
import quantum.dao.StockDataSource
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import spock.lang.Specification

import static quantum.TestUtils.assertMovimento
import static quantum.TestUtils.assertRetorno
import static quantum.TestUtils.assertVolumeMedio
import static quantum.TestUtils.data

/**
 * Created by xonda on 12/03/2015.
 */
@ContextConfiguration
@SpringBootTest(classes = Application.class)
public class MovimentoReaderServiceSpec extends Specification {

    @Autowired
    private StockReaderService stockReaderService

    @Autowired
    private StockDataSource stockDataSource

    void "Deve Listar Fechamentos Maximo"() {
        when:
        Collection<Movimento> movimentos = stockReaderService.fechamentosMaximo()(stockDataSource.list())
        
        then:
        Iterator<Movimento> it = movimentos.iterator()
        assertMovimento(it.next(), "PETR4", data("2013-01-04"), new BigDecimal("20.43"), 36141000L)
        assertMovimento(it.next(), "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
        assertMovimento(it.next(), "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
    }

    void "Deve Listar Fechamentos Minimo"() {
        when:
        Collection<Movimento> movimentos = stockReaderService.fechamentosMinimo()(stockDataSource.list())
        
        then:
        assert 3 == movimentos.size(), 'A Qtd de fechamentos deve ser '
        
        Iterator<Movimento> it = movimentos.iterator()
        assertMovimento(it.next(), "PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L)
        assertMovimento(it.next(), "OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L)
        assertMovimento(it.next(), "VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L)
    }

    void "Deve Listar Retorno Maximo"() {
        when:
        Collection<Retorno> retornos = stockReaderService.retornosMaximo()(stockDataSource.list())
        
        then:
        assert 3 == retornos.size(), 'A Qtd de fechamentos deve ser '

        assertRetorno(retornos[0], "PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L)
        assertRetorno(retornos[1], "OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L)
        assertRetorno(retornos[2], "VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L)
    }

    void "Deve Listar Retorno Minimo"() {
        when:
        Collection<Retorno> retornos = stockReaderService.retornosMinimo()(stockDataSource.list())
        
        then:
        assert 3 == retornos.size(), 'A Qtd de fechamentos deve ser'

        assertRetorno(retornos[0], "PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L)
        assertRetorno(retornos[1], "OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L)
        assertRetorno(retornos[2], "VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L)
    }

    void "Deve Listar Volume Medio Cada Acao"() {
        when:
        Collection<Average> movimentos = stockReaderService.volumesMedio()(stockDataSource.list())
        
        then:
        assert 3 == movimentos.size(), 'A Qtd de fechamentos deve ser '

        Iterator<Average> it = movimentos.iterator()
        assertVolumeMedio(it.next(), "PETR4", 31236450d)
        assertVolumeMedio(it.next(), "OGXP3", 42023700d)
        assertVolumeMedio(it.next(), "VALE5", 19956466.6666667d)
    }
}
