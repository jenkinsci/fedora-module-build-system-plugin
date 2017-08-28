
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

/*
 * The MIT License
 *
 * Copyright (c) Red Hat, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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

    @Override
    public String toString() {
        return "Rpm{" +
                "nvr=" + nvr +
                ", state=" + state +
                ", stateReason=" + stateReason +
                ", taskId=" + taskId +
                ", name='" + name + '\'' +
                '}';
    }
}
