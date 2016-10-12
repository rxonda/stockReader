package quantum.services

import org.springframework.stereotype.Service
import quantum.domain.Average
import quantum.domain.Movimento
import quantum.domain.Retorno

import java.util.stream.Collectors
import java.util.stream.Stream

import static java.util.Comparator.comparing

/**
 * Created by xonda on 12/03/2015.
 */
@Service
class StockReaderServiceImpl implements StockReaderService {

    private static final Comparator<Movimento> COMPARE_BY_CLOSE = comparing { c -> c.getClose() }
    private static final Comparator<Retorno> COMPARE_BY_VALUE = comparing{ c -> c.getValor() }

    @Override
    Closure<Collection<Movimento>> fechamentosMaximo() {
        getGroupByClose(COMPARE_BY_CLOSE)
    }

    @Override
    Closure<Collection<Movimento>> fechamentosMinimo() {
        getGroupByClose(COMPARE_BY_CLOSE.reversed())
    }

    @Override
    Closure<Collection<Retorno>> retornosMaximo() {
        getGroupByRetorno(COMPARE_BY_VALUE)
    }

    @Override
    Closure<Collection<Retorno>> retornosMinimo() {
        getGroupByRetorno(COMPARE_BY_VALUE.reversed())
    }

    @Override
    Closure<Collection<Average>> volumesMedio() {
        { Stream<Movimento> movimentoObservable ->
            movimentoObservable
                    .filter { m -> m.volume > 0L }
                    .collect(Collectors.groupingBy{ m -> m.id })
                    .collect { k, g ->
                        g.inject(new Average(k)) { a, c -> a.add(c.volume) }
                    }
        }
    }

    private Closure<Collection<Movimento>> getGroupByClose(Comparator comparator) {
        { Stream<Movimento> movimentoObservable ->
            movimentoObservable
                    .collect(Collectors.groupingBy { m -> m.id })
                    .collect { String k, List g ->
                        g.inject (new Movimento(id:'')) { m, m2 ->
                            !m.id.equals(m2.id) ? m2 : comparator.compare(m, m2) < 0 ? m2 : m
                        }
                    }
        }
    }

    private Closure<Collection<Retorno>> getGroupByRetorno(Comparator comparator) {
        { Stream<Movimento> movimentoObservable ->
            movimentoObservable
                    .collect(Collectors.groupingBy { m -> m.id })
                    .collect { String id, Collection movimentos ->
                        movimentos.inject(new Retorno()) { r, m ->
                                    r.setCorrente(m)
                                }
                    }
        }
    }
}
