package br.org.quantum.services;

import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.VolumeMedio;

import java.util.Collection;

/**
 * Created by xonda on 12/03/2015.
 */
public interface StockReaderService {
    Collection<Movimento> list();

    Collection<Movimento> fechamentosMaximo();

    Collection<Movimento> fechamentosMinimo();

    Collection<Movimento> retornosMaximo();

    Collection<Movimento> retornosMinimo();

    Collection<VolumeMedio> volumesMedio();
}
