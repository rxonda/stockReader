package quantum.dao

import quantum.domain.Movimento

import java.util.stream.Stream

/**
 * Created by xonda on 15/03/2015.
 */
public interface StockDataSource {
    Stream<Movimento> list()
}
