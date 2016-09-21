package quantum.domain
/**
 * Created by xonda on 12/03/2015.
 */
public class Movimento {
    private final String id
    private final Date date
    private final BigDecimal close
    private final Long volume

    public Movimento(String id) {
        this(id, null, null, null)
    }

    public Movimento(String id, Date date, BigDecimal close, Long volume) {
        this.id = id
        this.date = date
        this.close = close
        this.volume = volume
    }

    public String getId() {
        return id
    }

    public Date getDate() {
        return date
    }

    public BigDecimal getClose() {
        return close
    }

    public Long getVolume() {
        return volume
    }
}
