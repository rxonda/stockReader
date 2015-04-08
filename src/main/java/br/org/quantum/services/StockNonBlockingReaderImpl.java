package br.org.quantum.services;

import br.org.quantum.dao.StockNonBlockingDatasource;
import br.org.quantum.domain.Average;
import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.Retorno;
import br.org.quantum.domain.VolumeMedio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Comparator.comparing;

/**
 * Created by xonda on 25/03/2015.
 */
@Service
public class StockNonBlockingReaderImpl implements StockNonBlockingReader {

    @Autowired
    private StockNonBlockingDatasource stockNonBlockingDatasource;

    @Override
    public Observable<List<Movimento>> list() {
        return stockNonBlockingDatasource.list()
                .toList();
    }

    @Override
    public Observable<List<Movimento>> fechamentosMaximo() {
        return stockNonBlockingDatasource.list()
                .groupBy(m -> m.getId())
                .flatMap(g -> g.reduce(new Movimento("", null, BigDecimal.ZERO, Long.MIN_VALUE),
                        (m, m2) -> comparing(Movimento::getClose).compare(m, m2) < 0 ? m2 : m))
                .toList();
    }

    @Override
    public Observable<List<Movimento>> fechamentosMinimo() {
        return stockNonBlockingDatasource.list()
                .groupBy(m -> m.getId())
                .flatMap(g -> g.reduce(new Movimento("", null, null, Long.MAX_VALUE),
                        (m, m2) -> m.getId().equals("") ? m2 : comparing(Movimento::getClose).compare(m, m2) > 0 ? m2 : m))
                .toList();
    }

    @Override
    public Observable<List<Movimento>> retornosMaximo() {
        return stockNonBlockingDatasource.list()
                .scan(new Retorno(), (a, c) -> a.setCorrente(c))
                .filter(r -> r.isValid())
                .groupBy(r -> r.getCorrente().getId())
                .flatMap(g -> g.reduce(new Retorno(), (r1, r2) -> !r1.isValid() ? r2 : comparing(Retorno::getValor).compare(r1, r2) < 0 ? r2 : r1))
                .flatMap(r -> Observable.just(r.getCorrente()))
                .toList();
    }

    @Override
    public Observable<List<Movimento>> retornosMinimo() {
        return stockNonBlockingDatasource.list()
                .scan(new Retorno(), (a, c) -> a.setCorrente(c))
                .filter(r -> r.isValid())
                .groupBy(r -> r.getCorrente().getId())
                .flatMap(g -> g.reduce(new Retorno(), (r1, r2) -> !r1.isValid() ? r2 : comparing(Retorno::getValor).compare(r1, r2) > 0 ? r2 : r1))
                .flatMap(r -> Observable.just(r.getCorrente()))
                .toList();
    }

    @Override
    public Observable<List<VolumeMedio>> volumesMedio() {
        return stockNonBlockingDatasource.list()
                .filter(m -> m.getVolume() > 0L)
                .groupBy(m -> m.getId())
                .flatMap(g -> g.reduce(new Average(g.getKey()), (a, c) -> a.add(c.getVolume())))
                .flatMap(r -> Observable.just(r.getVolumeMedio()))
                .toList();
    }
}
