package br.org.quantum.services;

import br.org.quantum.dao.StockDataSource;
import br.org.quantum.domain.Movimento;
import br.org.quantum.domain.VolumeMedio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Created by xonda on 12/03/2015.
 */
@Service
public class StockReaderServiceImpl implements StockReaderService {

    @Autowired
    private StockDataSource dataSource;

    @Override
    public Collection<Movimento> list() {
        return dataSource.list().collect(Collectors.toList());
    }

    @Override
    public Collection<Movimento> fechamentosMaximo() {
        return dataSource.list()
                .collect(Collectors.groupingBy(Movimento::getId, Collectors.maxBy(comparing(Movimento::getClose))))
                .values().stream().map(Optional::get).collect(Collectors.toList());
    }

    @Override
    public Collection<Movimento> fechamentosMinimo() {
        return dataSource.list()
                .collect(Collectors.groupingBy(Movimento::getId, Collectors.minBy(comparing(Movimento::getClose))))
                .values().stream().map(Optional::get).collect(Collectors.toList());
    }

    @Override
    public Collection<Movimento> retornosMaximo() {

        Collection<Movimento> listagem = new ArrayList<>();
        Iterator<Movimento> it = dataSource.list().iterator();
        Movimento anterior = null;
        BigDecimal maxRetorno = null;
        Movimento maxMovimento = null;
        while(it.hasNext()) {
            Movimento corrente = it.next();
            //Nao estou calculando o retorno se nao houve movimento
            //no dia anterior.
            if (anterior == null) {
                anterior = corrente;
            } else if (anterior.getId().equals(corrente.getId())) {
                BigDecimal retorno = corrente.getClose().divide(anterior.getClose(), 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE);
                if (maxRetorno == null || maxRetorno.compareTo(retorno) < 0){
                    maxRetorno = retorno;
                    maxMovimento = corrente;
                }
                anterior = corrente;
            } else {
                listagem.add(maxMovimento);
                maxRetorno = null;
                anterior = corrente;
            }
        }
        if(maxRetorno!=null) {
            listagem.add(maxMovimento);
        }

        return listagem;
    }

    @Override
    public Collection<Movimento> retornosMinimo() {
        Collection<Movimento> listagem = new ArrayList<>();
        Iterator<Movimento> it = dataSource.list().iterator();
        Movimento anterior = null;
        BigDecimal minRetorno = null;
        Movimento minMovimento = null;
        while(it.hasNext()) {
            Movimento corrente = it.next();
            //Nao estou calculando o retorno se nao houve movimento
            //no dia anterior.
            if (anterior == null) {
                anterior = corrente;
            } else if (anterior.getId().equals(corrente.getId())) {
                BigDecimal retorno = corrente.getClose().divide(anterior.getClose(), 2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.ONE);
                if (minRetorno == null || minRetorno.compareTo(retorno) > 0){
                    minRetorno = retorno;
                    minMovimento = corrente;
                }
                anterior = corrente;
            } else {
                listagem.add(minMovimento);
                minRetorno = null;
                anterior = corrente;
            }
        }
        if(minRetorno!=null) {
            listagem.add(minMovimento);
        }

        return listagem;
    }

    @Override
    public Collection<VolumeMedio> volumesMedio() {
        Map<String, Double> resultado = dataSource.list()
                .filter(m -> m.getVolume() > 0L)
                .collect(Collectors.groupingBy(Movimento::getId, Collectors.averagingLong(Movimento::getVolume)));
        Collection<VolumeMedio> listagem = new ArrayList<>();
        resultado.forEach((id, vl) -> listagem.add(new VolumeMedio(id, vl)));
        return listagem;
    }
}
