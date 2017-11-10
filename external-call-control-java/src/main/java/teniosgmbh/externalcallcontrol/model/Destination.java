package teniosgmbh.externalcallcontrol.model;

public class Destination {

    private String destinationType;
    private String destination;
    private Integer timeout; // timeout of this destination only

    public Destination() {}

    public Destination(String destinationType, String destination, Integer timeout) {
        this.destinationType = destinationType;
        this.destination = destination;
        this.timeout = timeout;
    }

    public String getDestinationType() {
        return destinationType;
    }
    public void setDestinationType(final String destinationType) {
        this.destinationType = destinationType;
    }

    public String getDestination() {
        return destination;
    }
    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public Integer getTimeout() {
        return timeout;
    }
    public void setTimeout(final Integer timeout) {
        this.timeout = timeout;
    }

}
