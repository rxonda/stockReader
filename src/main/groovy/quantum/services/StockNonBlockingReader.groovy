package quantum.services

import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import rx.Observable

/**
 * Created by xonda on 25/03/2015.
 */
interface StockNonBlockingReader {
    Closure<Observable<Movimento>> fechamentosMaximo()

    Closure<Observable<Movimento>> fechamentosMinimo()

    Closure<Observable<Retorno>> retornosMaximo()

    Closure<Observable<Retorno>> retornosMinimo()

    Closure<Observable<Average>> volumesMedio()
}
