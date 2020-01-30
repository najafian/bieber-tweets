package org.interview.twitter.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TwitterRequestDto implements Serializable {
    private String applicationName;
    private String pinID;
    private String consumerKey;
    private String consumerSecurityKey;

    @JsonProperty("pin")
    public String getPinID() {
        return pinID;
    }

    public void setPinID(String pinID) {
        this.pinID = pinID;
    }

    @JsonProperty("applicationName")
    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    @JsonProperty("applicationName")
    public String getConsumerSecurityKey() {
        return consumerSecurityKey;
    }

    public void setConsumerSecurityKey(String consumerSecurityKey) {
        this.consumerSecurityKey = consumerSecurityKey;
    }

    @JsonProperty("appName")
    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String toString() {
        return "TwitterRequestDto{" +
                "applicationName='" + applicationName + '\'' +
                ", pinID='" + pinID + '\'' +
                ", consumerKey='" + consumerKey + '\'' +
                ", consumerSecurityKey='" + consumerSecurityKey + '\'' +
                '}';
    }
}
