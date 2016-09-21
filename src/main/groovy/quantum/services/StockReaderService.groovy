package quantum.services

import quantum.domain.Movimento
import quantum.domain.VolumeMedio

/**
 * Created by xonda on 12/03/2015.
 */
public interface StockReaderService {
    Collection<Movimento> list()

    Collection<Movimento> fechamentosMaximo()

    Collection<Movimento> fechamentosMinimo()

    Collection<Movimento> retornosMaximo()

    Collection<Movimento> retornosMinimo()

    Collection<VolumeMedio> volumesMedio()
}
