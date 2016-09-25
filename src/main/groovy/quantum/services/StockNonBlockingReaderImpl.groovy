package quantum.services

import org.springframework.stereotype.Service
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import rx.Observable

import static java.util.Comparator.comparing

/**
 * Created by xonda on 25/03/2015.
 */
@Service
class StockNonBlockingReaderImpl implements StockNonBlockingReader {

    private static final Comparator<Movimento> COMPARE_BY_CLOSE = comparing { c -> c.getClose() }
    private static final Comparator<Retorno> COMPARE_BY_VALUE = comparing{ c -> c.getValor() }

    @Override
    Closure<Observable<Movimento>> fechamentosMaximo() {
        getGroupByClose(COMPARE_BY_CLOSE)
    }

    @Override
    Closure<Observable<Movimento>> fechamentosMinimo() {
        getGroupByClose(COMPARE_BY_CLOSE.reversed())
    }

    @Override
    Closure<Observable<Retorno>> retornosMaximo() {
        getGroupByRetorno(COMPARE_BY_VALUE)
    }

    @Override
    Closure<Observable<Retorno>> retornosMinimo() {
        getGroupByRetorno(COMPARE_BY_VALUE.reversed())
    }

    @Override
    Closure<Observable<Average>> volumesMedio() {
        { Observable<Movimento> movimentoObservable ->
            movimentoObservable
                    .filter { m -> m.volume > 0L }
                    .groupBy { m -> m.id }
                    .flatMap { g -> g.reduce(new Average(g.key)) { a, c -> a.add(c.volume) } }
        }
    }

    private Closure<Observable<Movimento>> getGroupByClose(Comparator comparator) {
        { Observable<Movimento> movimentoObservable ->
            movimentoObservable
                    .groupBy { m -> m.id }
                    .flatMap { g -> g.reduce(new Movimento(id:'')) { m, m2 -> !m.id.equals(m2.id) ? m2 : comparator.compare(m, m2) < 0 ? m2 : m } }
        }
    }

    private Closure<Observable<Retorno>> getGroupByRetorno(Comparator comparator) {
        { Observable<Movimento> movimentoObservable ->
            movimentoObservable
                    .scan(new Retorno()) { a, c -> a.setCorrente(c) }
                    .filter { r -> r.valid }
                    .groupBy { r -> r.id }
                    .flatMap { g -> g.reduce(new Retorno()) { r1, r2 -> !r1.valid ? r2 : comparator.compare(r1, r2) < 0 ? r2 : r1 } }
        }
    }
}
