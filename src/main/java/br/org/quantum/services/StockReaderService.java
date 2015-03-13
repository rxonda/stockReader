package br.org.quantum.services;

import br.org.quantum.domain.Stock;

import java.util.Collection;

/**
 * Created by xonda on 12/03/2015.
 */
public interface StockReaderService {
    Collection<Stock> list();
}
