package br.org.quantum.services;

import br.org.quantum.dao.StockNonBlockingDatasource;
import br.org.quantum.domain.Average;
import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.Retorno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

/**
 * Created by xonda on 25/03/2015.
 *
 *
 */
@Service
public class StockNonBlockingReaderImpl implements StockNonBlockingReader {

    private static final Comparator COMPARE_BY_CLOSE = comparing(Movimento::getClose);
    private static final Comparator COMPARE_BY_VALUE = comparing(Retorno::getValor);

    @Autowired
    private StockNonBlockingDatasource stockNonBlockingDatasource;

    @Override
    public Observable<List<Movimento>> list() {
        return stockNonBlockingDatasource.list()
                .toList();
    }

    @Override
    public Observable<List<Movimento>> fechamentosMaximo() {
        return getGroupByClose(COMPARE_BY_CLOSE);
    }

    @Override
    public Observable<List<Movimento>> fechamentosMinimo() {
        return getGroupByClose(COMPARE_BY_CLOSE.reversed());
    }

    @Override
    public Observable<List<Retorno>> retornosMaximo() {
        return getGroupByRetorno(COMPARE_BY_VALUE);
    }

    @Override
    public Observable<List<Retorno>> retornosMinimo() {
        return getGroupByRetorno(COMPARE_BY_VALUE.reversed());
    }

    @Override
    public Observable<List<Average>> volumesMedio() {
        return stockNonBlockingDatasource.list()
                .filter(m -> m.getVolume() > 0L)
                .groupBy(m -> m.getId())
                .flatMap(g -> g.reduce(new Average(g.getKey()), (a, c) -> a.add(c.getVolume())))
                .toList();
    }

    private Observable<List<Movimento>> getGroupByClose(Comparator comparator) {
        return stockNonBlockingDatasource.list()
                .groupBy(m -> m.getId())
                .flatMap(g -> g.reduce(new Movimento(""),
                        (m, m2) -> !m.getId().equals(m2.getId()) ? m2 : comparator.compare(m, m2) < 0 ? m2 : m))
                .toList();
    }

    private Observable<List<Retorno>> getGroupByRetorno(Comparator comparator) {
        return stockNonBlockingDatasource.list()
                .scan(new Retorno(), (a, c) -> a.setCorrente(c))
                .filter(r -> r.isValid())
                .groupBy(r -> r.getId())
                .flatMap(g -> g.reduce(new Retorno(), (r1, r2) -> !r1.isValid() ? r2 : comparator.compare(r1, r2) < 0 ? r2 : r1))
                .toList();
    }
}
