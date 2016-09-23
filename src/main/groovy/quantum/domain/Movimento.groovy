package quantum.domain
/**
 * Created by xonda on 12/03/2015.
 */
class Movimento {
    private final String id
    private final Date date
    private final BigDecimal close
    private final Long volume

    Movimento(String id) {
        this(id, null, null, null)
    }

    Movimento(String id, Date date, BigDecimal close, Long volume) {
        this.id = id
        this.date = date
        this.close = close
        this.volume = volume
    }

    String getId() {
        id
    }

    Date getDate() {
        date
    }

    BigDecimal getClose() {
        close
    }

    Long getVolume() {
        volume
    }
}
