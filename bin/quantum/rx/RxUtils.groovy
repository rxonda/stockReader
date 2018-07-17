package quantum.rx

import rx.Observable

import java.util.stream.Stream

/**
 * Created by xonda on 10/12/16.
 */
class RxUtils {
    static <T> Observable<T> convertFromStream(Stream<T> movimentoStream) {
        Observable.from(movimentoStream as Iterable)
    }
}
