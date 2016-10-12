package quantum.services

import quantum.domain.Movimento
import quantum.domain.Average

import java.util.stream.Stream

/**
 * Created by xonda on 12/03/2015.
 */
interface StockReaderService {
    Closure<Stream<Movimento>> fechamentosMaximo()

    Closure<Stream<Movimento>> fechamentosMinimo()

    Closure<Stream<Movimento>> retornosMaximo()

    Closure<Stream<Movimento>> retornosMinimo()

    Closure<Stream<Average>> volumesMedio()
}
