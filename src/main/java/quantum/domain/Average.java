package quantum.domain;

/**
 * Created by xonda on 08/04/2015.
 */
public class Average {
    private final String id;
    private final Double volume;

    public Average(String id, Double volume) {
        this.volume = volume;
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public Double getVolume() {
        return this.volume;
    }
}
