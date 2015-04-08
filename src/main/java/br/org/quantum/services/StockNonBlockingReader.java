package br.org.quantum.services;

import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.VolumeMedio;
import rx.Observable;

import java.util.Collection;
import java.util.List;

/**
 * Created by xonda on 25/03/2015.
 */
public interface StockNonBlockingReader {
    Observable<List<Movimento>> list();

    Observable<List<Movimento>> fechamentosMaximo();

    Observable<List<Movimento>> fechamentosMinimo();

    Observable<List<Movimento>> retornosMaximo();

    Observable<List<Movimento>> retornosMinimo();

    Observable<List<VolumeMedio>> volumesMedio();
}
