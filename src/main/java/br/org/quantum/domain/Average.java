package br.org.quantum.domain;

/**
 * Created by xonda on 08/04/2015.
 */
public class Average {
    private final String id;
    private final Integer count;
    private final Double value;

    public Average(String id) {
        count = 0;
        value = 0d;
        this.id = id;
    }

    private Average(String id, Integer count, Double value) {
        this.count = count;
        this.value = value;
        this.id = id;
    }

    public Average add(Long value) {
        if (count == 0) {
            return new Average(this.id, 1, value.doubleValue());
        } else {
            return new Average(this.id, count + 1, (((this.value * count) + value.doubleValue()) / (count + 1)));
        }
    }

    public VolumeMedio getVolumeMedio() {
        return new VolumeMedio(id, value);
    }
}
