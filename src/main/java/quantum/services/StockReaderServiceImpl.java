package quantum.services;

import quantum.domain.Average;
import quantum.domain.Movimento;
import quantum.domain.Retorno;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * Created by xonda on 12/03/2015.
 */
public class StockReaderServiceImpl implements StockReaderService {

    static class TempRetorno {
        Retorno current = new Retorno();

        Retorno setCorrente(Movimento m) {
            this.current = current.setCorrente(m);
            return this.current;
        }
    }

    private static final Comparator<Movimento> COMPARE_BY_CLOSE = comparing (Movimento::getClose);

    private static final Comparator<Retorno> COMPARE_BY_VALUE = comparing(Retorno::getValor);

    private static Function<Stream<Movimento>,List<Movimento>> getGroupByClose(final Comparator<Movimento> comparator) {
        return (Stream<Movimento> stream) -> {
            Map<String, List<Movimento>> mapa = stream.collect(Collectors.groupingBy(Movimento::getId));
            List<Movimento> result = new ArrayList<>();
            mapa.forEach((k,g) -> result.add(g.stream().max(comparator).get()));
            return result;
        };
    }

    private static Function<Stream<Movimento>,List<Retorno>> getGroupByRetorno(final Comparator comparator) {
        return (Stream<Movimento> stream) -> {
            TempRetorno initial = new TempRetorno();
            Map<String, List<Retorno>> mapa = stream
                    .map(v -> initial.setCorrente(v))
                    .filter(r -> r.isValid())
                    .collect(Collectors.groupingBy(Retorno::getId));
            List<Retorno> result = new ArrayList<>();
            mapa.forEach((k,g) -> result.add((Retorno) g.stream().max(comparator).get()));
            return result;
        };
    }

    @Override
    public List<Movimento> fechamentosMaximo(Stream<Movimento> stream) {
        return getGroupByClose(COMPARE_BY_CLOSE).apply(stream);
    }

    @Override
    public List<Movimento> fechamentosMinimo(Stream<Movimento> stream) {
        return getGroupByClose(COMPARE_BY_CLOSE.reversed()).apply(stream);
    }

    @Override
    public List<Retorno> retornosMaximo(Stream<Movimento> stream) {
        return getGroupByRetorno(COMPARE_BY_VALUE).apply(stream);
    }

    @Override
    public List<Retorno> retornosMinimo(Stream<Movimento> stream) {
        return getGroupByRetorno(COMPARE_BY_VALUE.reversed()).apply(stream);
    }

    @Override
    public List<Average> volumesMedio(Stream<Movimento> stream) {
        Map<String, List<Movimento>> mapa = stream.filter(m -> m.getVolume() > 0L)
                .collect(Collectors.groupingBy(Movimento::getId));
        List<Average> result = new ArrayList<>();
        mapa.forEach((k,g) -> result.add(new Average(k, g.stream().mapToLong(Movimento::getVolume).average().getAsDouble())));

        return result;
    }
}
