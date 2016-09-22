package quantum.services

import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import rx.Observable

/**
 * Created by xonda on 25/03/2015.
 */
interface StockNonBlockingReader {
    Observable<List<Movimento>> list()

    Observable<List<Movimento>> fechamentosMaximo()

    Observable<List<Movimento>> fechamentosMinimo()

    Observable<List<Retorno>> retornosMaximo()

    Observable<List<Retorno>> retornosMinimo()

    Observable<List<Average>> volumesMedio()
}
