package quantum.services;

import quantum.domain.Average;
import quantum.domain.Movimento;
import quantum.domain.Retorno;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by xonda on 12/03/2015.
 */
public interface StockReaderService {

    List<Movimento> fechamentosMaximo(Stream<Movimento> stream);

    List<Movimento> fechamentosMinimo(Stream<Movimento> stream);

    List<Retorno> retornosMaximo(Stream<Movimento> stream);

    List<Retorno> retornosMinimo(Stream<Movimento> stream);

    List<Average> volumesMedio(Stream<Movimento> stream);

}
