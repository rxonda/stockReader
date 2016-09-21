package quantum.dao

import quantum.domain.Movimento
import rx.Observable

/**
 * Created by xonda on 23/03/2015.
 */
public interface StockNonBlockingDatasource {
    Observable<Movimento> list()
}
