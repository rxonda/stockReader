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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Average)) {
            return false;
        }
        if(obj == this) {
            return true;
        }

        Average other = (Average)obj;

        return (this.id.equals(other.id) && (this.volume - other.volume < 0.00000001));
    }

    @Override
    public String toString() {
        return "Average{" +
                "id='" + id + '\'' +
                ", volume=" + volume +
                '}';
    }
}
