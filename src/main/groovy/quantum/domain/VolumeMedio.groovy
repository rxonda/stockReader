package quantum.domain

/**
 * Created by xonda on 15/03/2015.
 */
class VolumeMedio {
    private final String id
    private final Double volume

    VolumeMedio(String id, Double volume) {
        this.id = id
        this.volume = volume
    }

    String getId() {
        id
    }

    Double getVolume() {
        volume
    }

}
