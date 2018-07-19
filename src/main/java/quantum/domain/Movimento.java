package quantum.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xonda on 12/03/2015.
 */
public class Movimento {
    private final String id;
    private final Date date;
    private final BigDecimal close;
    private final Long volume;

    public Movimento() {
        this.id = "";
        this.date = null;
        this.close = null;
        this.volume = null;
    }

    public Movimento(String id, Date date, BigDecimal close, Long volume) {
        this.id = id;
        this.date = date;
        this.close = close;
        this.volume = volume;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getClose() {
        return close;
    }

    public Long getVolume() {
        return volume;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Movimento)) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        Movimento other = (Movimento)obj;
        return (
                this.id.equals(other.id)
                        && this.date.equals(other.date)
                        && this.close.equals(other.close)
                        && this.volume.equals(other.volume)
        );
    }

    @Override
    public String toString() {
        return "Movimento{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }
}
