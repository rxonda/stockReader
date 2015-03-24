package br.org.quantum.dao;

import br.org.quantum.domain.Movimento;
import rx.Observable;

/**
 * Created by xonda on 23/03/2015.
 */
public interface StockNonBlockingDatasource {
    Observable<Movimento> list();
}
