package quantum.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import quantum.dao.StockDataSource
import quantum.domain.Movimento
import quantum.domain.VolumeMedio

import java.util.stream.Collectors

import static java.util.Comparator.comparing

/**
 * Created by xonda on 12/03/2015.
 */
@Service
class StockReaderServiceImpl implements StockReaderService {

    @Autowired
    private StockDataSource dataSource

    @Override
    Collection<Movimento> list() {
        dataSource.list().collect(Collectors.toList())
    }

    @Override
    Collection<Movimento> fechamentosMaximo() {
        dataSource.list()
                .collect(Collectors.groupingBy({ m -> m.getId() }, Collectors.maxBy(comparing { m -> m.getClose() })))
                .values().stream().map{ o -> o.get() }.collect(Collectors.toList())
    }

    @Override
    Collection<Movimento> fechamentosMinimo() {
        dataSource.list()
                .collect(Collectors.groupingBy({ m -> m.getId() }, Collectors.minBy(comparing({ m -> m.getClose() }))))
                .values().stream().map { o -> o.get() }.collect(Collectors.toList())
    }

    @Override
    Collection<Movimento> retornosMaximo() {

        Collection<Movimento> listagem = new ArrayList<>()
        Iterator<Movimento> it = dataSource.list().iterator()
        Movimento anterior = null
        BigDecimal maxRetorno = null
        Movimento maxMovimento = null
        while(it.hasNext()) {
            Movimento corrente = it.next()
            //Nao estou calculando o retorno se nao houve movimento
            //no dia anterior.
            if (anterior == null) {
                anterior = corrente
            } else if (anterior.getId().equals(corrente.getId())) {
                BigDecimal retorno = corrente.getClose().divide(anterior.getClose(), 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE)
                if (maxRetorno == null || maxRetorno.compareTo(retorno) < 0){
                    maxRetorno = retorno
                    maxMovimento = corrente
                }
                anterior = corrente
            } else {
                listagem.add(maxMovimento)
                maxRetorno = null
                anterior = corrente
            }
        }
        if(maxRetorno!=null) {
            listagem.add(maxMovimento)
        }

        listagem
    }

    @Override
    Collection<Movimento> retornosMinimo() {
        Collection<Movimento> listagem = new ArrayList<>()
        Iterator<Movimento> it = dataSource.list().iterator()
        Movimento anterior = null
        BigDecimal minRetorno = null
        Movimento minMovimento = null
        while(it.hasNext()) {
            Movimento corrente = it.next()
            //Nao estou calculando o retorno se nao houve movimento
            //no dia anterior.
            if (anterior == null) {
                anterior = corrente
            } else if (anterior.getId().equals(corrente.getId())) {
                BigDecimal retorno = corrente.getClose().divide(anterior.getClose(), 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE)
                if (minRetorno == null || minRetorno.compareTo(retorno) > 0){
                    minRetorno = retorno
                    minMovimento = corrente
                }
                anterior = corrente
            } else {
                listagem.add(minMovimento)
                minRetorno = null
                anterior = corrente
            }
        }
        if(minRetorno!=null) {
            listagem.add(minMovimento)
        }

        listagem
    }

    @Override
    Collection<VolumeMedio> volumesMedio() {
        Collection<VolumeMedio> listagem = new ArrayList<>()
        dataSource.list()
                .filter { m -> m.getVolume() > 0L }
                .collect(Collectors.groupingBy({ m -> m.getId()}, Collectors.averagingLong({ it.getVolume() })))
                .forEach { id, vl -> listagem.add(new VolumeMedio(id, vl)) }
        listagem
    }
}
