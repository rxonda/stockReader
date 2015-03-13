package br.org.quantum.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xonda on 12/03/2015.
 */
public class Stock {
    private String id;
    private Date date;
    private BigDecimal close;
    private Long volume;

    public Stock(String id, Date date, BigDecimal close, Long volume) {
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
}
