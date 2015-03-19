package fr.istic.m2gla.mmm.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author appsrox.com
 */
@JsonAutoDetect
public class Contact {

    @JsonProperty
    private String email;
    @JsonProperty
    private String regId;

    public Contact(String email, String regId, double latitude, double longitude) {
        this.email = email;
        this.regId = regId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @JsonProperty

    private double latitude;
    @JsonProperty
    private double longitude;

    public Contact() {
    }

    public Contact(String email, String regId) {
        this.email = email;
        this.regId = regId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

}
