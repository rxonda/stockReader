package quantum.services

import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno
import rx.Observable

/**
 * Created by xonda on 25/03/2015.
 */
interface StockNonBlockingReader {
    Observable<Movimento> list()

    Observable<Movimento> fechamentosMaximo()

    Observable<Movimento> fechamentosMinimo()

    Observable<Retorno> retornosMaximo()

    Observable<Retorno> retornosMinimo()

    Observable<Average> volumesMedio()
}
