
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
    "nvr",
    "state",
    "state_reason",
    "task_id"
})
public class Rpm implements Serializable {

    @JsonProperty("nvr")
    private Object nvr;
    @JsonProperty("state")
    private Object state;
    @JsonProperty("state_reason")
    private Object stateReason;
    @JsonProperty("task_id")
    private Object taskId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("nvr")
    public Object getNvr() {
        return nvr;
    }

    @JsonProperty("nvr")
    public void setNvr(Object nvr) {
        this.nvr = nvr;
    }

    public Rpm withNvr(Object nvr) {
        this.nvr = nvr;
        return this;
    }

    @JsonProperty("state")
    public Object getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(Object state) {
        this.state = state;
    }

    public Rpm withState(Object state) {
        this.state = state;
        return this;
    }

    @JsonProperty("state_reason")
    public Object getStateReason() {
        return stateReason;
    }

    @JsonProperty("state_reason")
    public void setStateReason(Object stateReason) {
        this.stateReason = stateReason;
    }

    public Rpm withStateReason(Object stateReason) {
        this.stateReason = stateReason;
        return this;
    }

    @JsonProperty("task_id")
    public Object getTaskId() {
        return taskId;
    }

    @JsonProperty("task_id")
    public void setTaskId(Object taskId) {
        this.taskId = taskId;
    }

    public Rpm withTaskId(Object taskId) {
        this.taskId = taskId;
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

    public Rpm withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @JsonIgnore
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
