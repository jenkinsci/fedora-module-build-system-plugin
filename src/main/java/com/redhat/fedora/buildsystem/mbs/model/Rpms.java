package com.redhat.fedora.buildsystem.mbs.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rpms implements Serializable {

    @JsonIgnore
    private Map<String, Rpm> additionalProperties = new HashMap<String, Rpm>();

    @JsonAnyGetter
    public Map<String, Rpm> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Rpm value) {
        value.setName(name);
        this.additionalProperties.put(name, value);
    }

    public Rpms withAdditionalProperty(String name, Rpm value) {
        value.setName(name);
        this.additionalProperties.put(name, value);
        return this;
    }

    public Map<String, Rpm> getRpmList() {
        return this.additionalProperties;
    }

    @Override
    public String toString() {
        return "Rpms{" +
                "additionalProperties=" + additionalProperties +
                '}';
    }
}
