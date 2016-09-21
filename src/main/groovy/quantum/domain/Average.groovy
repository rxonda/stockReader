package quantum.domain

/**
 * Created by xonda on 08/04/2015.
 */
public class Average {
    private final String id
    private final Integer count
    private final Double volume

    public Average(String id) {
        count = 0
        volume = 0d
        this.id = id
    }

    private Average(String id, Integer count, Double volume) {
        this.count = count
        this.volume = volume
        this.id = id
    }

    public Average add(Long value) {
        if (count == 0) {
            return new Average(this.id, 1, value.doubleValue())
        } else {
            return new Average(this.id, count + 1, this.volume + ((value.doubleValue() - this.volume) / (count + 1)))
        }
    }

    public String getId() {
        return id
    }

    public Double getVolume() {
        return volume
    }
}
