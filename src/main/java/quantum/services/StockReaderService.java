package quantum.services;

import quantum.domain.Movimento;
import quantum.domain.Average;
import quantum.domain.Retorno;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by xonda on 12/03/2015.
 */
public interface StockReaderService {
    Function<Stream<Movimento>,Stream<Movimento>> fechamentosMaximo();

    Function<Stream<Movimento>,Stream<Movimento>> fechamentosMinimo();

    Function<Stream<Movimento>,Stream<Retorno>> retornosMaximo();

    Function<Stream<Movimento>,Stream<Retorno>> retornosMinimo();

    Function<Stream<Movimento>,Stream<Average>> volumesMedio();
}
