package br.org.quantum.domain;

/**
 * Created by xonda on 15/03/2015.
 */
public class VolumeMedio {
    private final String id;
    private final Double volume;

    public VolumeMedio(String id, Double volume) {
        this.id = id;
        this.volume = volume;
    }

    public String getId() {
        return id;
    }

    public Double getVolume() {
        return volume;
    }

}
