package quantum.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import quantum.dao.StockNonBlockingDatasource
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

    @Autowired
    private StockNonBlockingDatasource stockNonBlockingDatasource

    @Override
    Observable<List<Movimento>> list() {
        stockNonBlockingDatasource.list()
                .toList()
    }

    @Override
    Observable<List<Movimento>> fechamentosMaximo() {
        getGroupByClose(COMPARE_BY_CLOSE)
    }

    @Override
    Observable<List<Movimento>> fechamentosMinimo() {
        getGroupByClose(COMPARE_BY_CLOSE.reversed())
    }

    @Override
    Observable<List<Retorno>> retornosMaximo() {
        getGroupByRetorno(COMPARE_BY_VALUE)
    }

    @Override
    Observable<List<Retorno>> retornosMinimo() {
        getGroupByRetorno(COMPARE_BY_VALUE.reversed())
    }

    @Override
    Observable<List<Average>> volumesMedio() {
        stockNonBlockingDatasource.list()
                .filter { m -> m.getVolume() > 0L }
                .groupBy { m -> m.getId() }
                .flatMap { g -> g.reduce(new Average(g.getKey()), { a, c -> a.add(c.getVolume()) }) }
                .toList()
    }

    private Observable<List<Movimento>> getGroupByClose(Comparator comparator) {
        stockNonBlockingDatasource.list()
                .groupBy { m -> m.getId() }
                .flatMap { g -> g.reduce(new Movimento(""), { m, m2 -> !m.getId().equals(m2.getId()) ? m2 : comparator.compare(m, m2) < 0 ? m2 : m }) }
                .toList()
    }

    private Observable<List<Retorno>> getGroupByRetorno(Comparator comparator) {
        stockNonBlockingDatasource.list()
                .scan(new Retorno(), { a, c -> a.setCorrente(c) })
                .filter { r -> r.isValid() }
                .groupBy { r -> r.getId() }
                .flatMap { g -> g.reduce(new Retorno(), { r1, r2 -> !r1.isValid() ? r2 : comparator.compare(r1, r2) < 0 ? r2 : r1 }) }
                .toList()
    }
}
