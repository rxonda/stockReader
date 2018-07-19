package quantum.services

import quantum.dao.StockDataSource
import quantum.dao.StockFileReaderDataSource
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import spock.lang.Specification

import java.util.function.Consumer

import static quantum.TestUtils.data

/**
 * Created by xonda on 12/03/2015.
 */
class MovimentoReaderServiceSpec extends Specification {

    private StockReaderService stockReaderService = new StockReaderServiceImpl()

    private StockDataSource stockDataSource  = new StockFileReaderDataSource()

    void "should list Maximum Closings on parallel thread"() {
        setup:
        Flux<Movimento> movimentos = Flux.defer({Flux.fromIterable(stockReaderService.fechamentosMaximo(stockDataSource.list()))}).subscribeOn(Schedulers.parallel())
        Consumer<Movimento> mockConsumer = Mock(Consumer.class) {
            1 * accept(new Movimento("PETR4", data("2013-01-04"), new BigDecimal("20.43"), 36141000L))
            1 * accept(new Movimento("OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L))
            1 * accept(new Movimento("VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L))
        }

        expect:
        movimentos.subscribe(mockConsumer)
        Thread.sleep(1000)
    }

    void "should list Minimum Closings"() {
        setup:
        Flux<Movimento> movimentos = Flux.defer({ Flux.fromIterable(stockReaderService.fechamentosMinimo(stockDataSource.list())) })
        Consumer<Movimento> mockConsumer = Mock(Consumer.class) {
            1 * accept(new Movimento("PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L))
            1 * accept(new Movimento("OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L))
            1 * accept(new Movimento("VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L))
        }

        expect:
        movimentos.subscribe(mockConsumer)
    }

    void "should list Maximum Returns"() {
        setup:
        Flux<Retorno> retornos = Flux.defer({ Flux.fromIterable(stockReaderService.retornosMaximo(stockDataSource.list())) })
        Consumer<Retorno> mockConsumer = Mock(Consumer) {
            1 * accept(new Retorno(new Movimento("PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L), new BigDecimal("0.04"), true))
            1 * accept(new Retorno(new Movimento("OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L), new BigDecimal("0.09"), true))
            1 * accept(new Retorno(new Movimento("VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L), new BigDecimal("0.04"), true))
        }

        expect:
        retornos.subscribe(mockConsumer)
    }

    void "should list Minimum Returns"() {
        setup:
        Flux<Retorno> retornos = Flux.defer({ Flux.fromIterable(stockReaderService.retornosMinimo(stockDataSource.list())) })
        
        Consumer<Retorno> mockConsumer = Mock(Consumer) {
            1 * accept(new Retorno(new Movimento("PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L), new BigDecimal("-0.02"), true))
            1 * accept(new Retorno(new Movimento("OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L), new BigDecimal("0.03"), true))
            1 * accept(new Retorno(new Movimento("VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L), new BigDecimal("-0.02"), true))
        }

        expect:
        retornos.subscribe(mockConsumer)
    }

    void "should list Average Volume of each Share"() {
        setup:
        Flux<Average> movimentos = Flux.defer({ Flux.fromIterable(stockReaderService.volumesMedio(stockDataSource.list())) })
        Consumer<Average> mockConsumer = Mock(Consumer) {
            1 * accept(new Average("PETR4", 31236450d))
            1 * accept(new Average("OGXP3", 42023700d))
            1 * accept(new Average("VALE5", 19956466.6666667d))
        }

        expect:
        movimentos.subscribe(mockConsumer)
    }
}
