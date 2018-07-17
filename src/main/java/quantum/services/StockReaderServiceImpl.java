package quantum.services;

import quantum.domain.Average;
import quantum.domain.Movimento;
import quantum.domain.Retorno;

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

    private static final Comparator<Movimento> COMPARE_BY_CLOSE = comparing (Movimento::getClose);
    private static final Comparator<Retorno> COMPARE_BY_VALUE = comparing(Retorno::getValor);

    @Override
    public Function<Stream<Movimento>,Stream<Movimento>> fechamentosMaximo() {
        return getGroupByClose(COMPARE_BY_CLOSE);
    }

    @Override
    public Function<Stream<Movimento>,Stream<Movimento>> fechamentosMinimo() {
        return getGroupByClose(COMPARE_BY_CLOSE.reversed());
    }

    @Override
    public Function<Stream<Movimento>, Stream<Retorno>> retornosMaximo() {
        return getGroupByRetorno(COMPARE_BY_VALUE);
    }

    @Override
    public Function<Stream<Movimento>, Stream<Retorno>> retornosMinimo() {
        return getGroupByRetorno(COMPARE_BY_VALUE.reversed());
    }

    @Override
    public Function<Stream<Movimento>, Stream<Average>> volumesMedio() {
        return null; //(Stream<Movimento> movimentoObservable) -> movimentoObservable
//                    .filter(m -> m.getVolume() > 0L)
//                    .collect(Collectors.groupingBy((Movimento m) -> m.getId())
//                    .collect((k, g) -> {
//                        g.inject(new Average(k)) { a, c -> a.add(c.volume) }
//                    });
    }

    private Function<Stream<Movimento>,Stream<Movimento>> getGroupByClose(final Comparator<Movimento> comparator) {
        return (Stream<Movimento> stream) -> {
            Map<String, List<Movimento>> mapa = stream.collect(Collectors.groupingBy(Movimento::getId));
            final Stream.Builder<Movimento> result = Stream.builder();
            mapa.forEach((k,g) -> result.add(g.stream().max(comparator).get()));
            return result.build();
        };
    }

    class TempRetorno {
        Retorno current = new Retorno();

        Retorno setCorrente(Movimento m) {
            this.current = current.setCorrente(m);
            return this.current;
        }
    }

    private Function<Stream<Movimento>,Stream<Retorno>> getGroupByRetorno(final Comparator comparator) {
        return (Stream<Movimento> stream) -> {
            TempRetorno initial = new TempRetorno();
            Map<String, List<Retorno>> mapa = stream
                    .map(v -> initial.setCorrente(v))
                    .filter(r -> r.isValid())
                    .collect(Collectors.groupingBy(Retorno::getId));
            final Stream.Builder builder = Stream.builder();
            mapa.forEach((k,g) -> builder.add(g.stream().max(comparator).get()));
            return builder.build();
        };
    }
}
