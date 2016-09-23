package quantum.domain

/**
 * Created by xonda on 08/04/2015.
 */
class Average {
    private final String id
    private final Integer count
    private final Double volume

    Average(String id) {
        count = 0
        volume = 0d
        this.id = id
    }

    private Average(String id, Integer count, Double volume) {
        this.count = count
        this.volume = volume
        this.id = id
    }

    Average add(Long value) {
        if (count == 0) {
            new Average(this.id, 1, value.doubleValue())
        } else {
            new Average(this.id, count + 1, this.volume + ((value.doubleValue() - this.volume) / (count + 1)))
        }
    }

    public String getId() {
        id
    }

    public Double getVolume() {
        volume
    }
}
