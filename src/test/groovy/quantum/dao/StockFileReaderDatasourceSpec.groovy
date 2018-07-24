package quantum.dao

import quantum.domain.Movimento
import reactor.core.publisher.Flux
import spock.lang.Specification

import java.util.function.Consumer

import static quantum.TestUtils.data

class StockFileReaderDatasourceSpec extends Specification {

    StockDataSource stockDataSource = new StockFileReaderDataSource()

    def "should list on another thread"() {
        setup:
        Flux<Movimento> movimentos = Flux.fromStream(stockDataSource.list())

        Consumer mockConsumer = Mock(Consumer.class) {
            1 * accept(new Movimento("OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L))
            1 * accept(new Movimento("OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L))
            1 * accept(new Movimento("OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L))
            1 * accept(new Movimento("PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L))
            1 * accept(new Movimento("PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L))
            1 * accept(new Movimento("PETR4", data("2013-01-04"), new BigDecimal("20.43"), 36141000L))
            1 * accept(new Movimento("PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L))
            1 * accept(new Movimento("VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L))
            1 * accept(new Movimento("VALE5", data("2013-01-02"), new BigDecimal("42.60"), 18515700L))
            1 * accept(new Movimento("VALE5", data("2013-01-03"), new BigDecimal("42.09"), 15001800L))
            1 * accept(new Movimento("VALE5", data("2013-01-04"), new BigDecimal("41.36"), 26351900L))
        }

        expect:
        movimentos.subscribe(mockConsumer)
    }

    def "should list first page with 5 lines"() {
        setup:
        Flux<Movimento> movimentos = Flux.fromStream(stockDataSource.list(0,5))

        Consumer<Movimento> movimentoConsumer = Mock(Consumer.class) {
            1 * accept(new Movimento("OGXP3", data("2013-01-01"), new BigDecimal("4.38"), 0L))
            1 * accept(new Movimento("OGXP3", data("2013-01-02"), new BigDecimal("4.76"), 45904000L))
            1 * accept(new Movimento("OGXP3", data("2013-01-03"), new BigDecimal("4.90"), 38143400L))
            1 * accept(new Movimento("PETR4", data("2013-01-02"), new BigDecimal("19.69"), 30182600L))
            1 * accept(new Movimento("PETR4", data("2013-01-03"), new BigDecimal("20.40"), 30552600L))
        }

        expect:
        movimentos.subscribe(movimentoConsumer)
    }

    def "should list second page with 3 lines"() {
        setup:
        Flux<Movimento> movimentos = Flux.fromStream(stockDataSource.list(5,3))

        Consumer<Movimento> movimentoConsumer = Mock(Consumer.class) {
            1 * accept(new Movimento("PETR4", data("2013-01-04"), new BigDecimal("20.43"), 36141000L))
            1 * accept(new Movimento("PETR4", data("2013-01-07"), new BigDecimal("20.08"), 28069600L))
            1 * accept(new Movimento("VALE5", data("2013-01-01"), new BigDecimal("40.87"), 0L))
        }

        expect:
        movimentos.subscribe(movimentoConsumer)
    }
}