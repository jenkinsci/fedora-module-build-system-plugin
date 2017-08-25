
package com.redhat.fedora.buildsystem.mbs.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "reason",
    "state",
    "state_name",
    "time"
})
public class StateTrace implements Serializable {

    @JsonProperty("reason")
    private Object reason;
    @JsonProperty("state")
    private long state;
    @JsonProperty("state_name")
    private String stateName;
    @JsonProperty("time")
    private String time;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("reason")
    public Object getReason() {
        return reason;
    }

    @JsonProperty("reason")
    public void setReason(Object reason) {
        this.reason = reason;
    }

    public StateTrace withReason(Object reason) {
        this.reason = reason;
        return this;
    }

    @JsonProperty("state")
    public long getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(long state) {
        this.state = state;
    }

    public StateTrace withState(long state) {
        this.state = state;
        return this;
    }

    @JsonProperty("state_name")
    public String getStateName() {
        return stateName;
    }

    @JsonProperty("state_name")
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public StateTrace withStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    public StateTrace withTime(String time) {
        this.time = time;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public StateTrace withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return "StateTrace{" +
                "reason=" + reason +
                ", state=" + state +
                ", stateName='" + stateName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
