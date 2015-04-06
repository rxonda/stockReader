package br.org.quantum.services;

import br.org.quantum.dao.StockNonBlockingDatasource;
import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.VolumeMedio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.math.BigDecimal;
import java.util.Collection;
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
    public Observable<Collection<Movimento>> fechamentosMinimo() {
        return null;
    }

    @Override
    public Observable<Collection<Movimento>> retornosMaximo() {
        return null;
    }

    @Override
    public Observable<Collection<Movimento>> retornosMinimo() {
        return null;
    }

    @Override
    public Observable<Collection<VolumeMedio>> volumesMedio() {
        return null;
    }
}
