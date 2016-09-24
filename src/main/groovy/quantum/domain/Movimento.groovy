package quantum.domain

import groovy.transform.Immutable

/**
 * Created by xonda on 12/03/2015.
 */
@Immutable
class Movimento {
    String id
    Date date
    BigDecimal close
    Long volume
}
