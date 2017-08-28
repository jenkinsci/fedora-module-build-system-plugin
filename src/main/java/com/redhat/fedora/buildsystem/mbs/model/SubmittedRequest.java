package com.redhat.fedora.buildsystem.mbs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "component_builds",
    "id",
    "koji_tag",
    "modulemd",
    "name",
    "owner",
    "scmurl",
    "state",
    "state_name",
    "state_reason",
    "state_trace",
    "state_url",
    "stream",
    "tasks",
    "time_completed",
    "time_modified",
    "time_submitted",
    "version"
})
public class SubmittedRequest implements Serializable {

    @JsonProperty("component_builds")
    private List<Long> componentBuilds = new ArrayList<Long>();
    @JsonProperty("id")
    private long id;
    @JsonProperty("koji_tag")
    private Object kojiTag;
    @JsonProperty("modulemd")
    private String modulemd;
    @JsonProperty("name")
    private String name;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("scmurl")
    private String scmurl;
    @JsonProperty("state")
    private long state;
    @JsonProperty("state_name")
    private String stateName;
    @JsonProperty("state_reason")
    private Object stateReason;
    @JsonProperty("state_trace")
    private List<StateTrace> stateTrace = new ArrayList<StateTrace>();
    @JsonProperty("state_url")
    private String stateUrl;
    @JsonProperty("stream")
    private String stream;
    @JsonProperty("tasks")
    private Tasks tasks;
    @JsonProperty("time_completed")
    private Object timeCompleted;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("time_submitted")
    private String timeSubmitted;
    @JsonProperty("version")
    private String version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("component_builds")
    public List<Long> getComponentBuilds() {
        return componentBuilds;
    }

    @JsonProperty("component_builds")
    public void setComponentBuilds(List<Long> componentBuilds) {
        this.componentBuilds = componentBuilds;
    }

    public SubmittedRequest withComponentBuilds(List<Long> componentBuilds) {
        this.componentBuilds = componentBuilds;
        return this;
    }

    @JsonProperty("id")
    public String getId() {
        return new Long(id).toString();
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    public SubmittedRequest withId(long id) {
        this.id = id;
        return this;
    }

    @JsonProperty("koji_tag")
    public Object getKojiTag() {
        return kojiTag;
    }

    @JsonProperty("koji_tag")
    public void setKojiTag(Object kojiTag) {
        this.kojiTag = kojiTag;
    }

    public SubmittedRequest withKojiTag(Object kojiTag) {
        this.kojiTag = kojiTag;
        return this;
    }

    @JsonProperty("modulemd")
    public String getModulemd() {
        return modulemd;
    }

    @JsonProperty("modulemd")
    public void setModulemd(String modulemd) {
        this.modulemd = modulemd;
    }

    public SubmittedRequest withModulemd(String modulemd) {
        this.modulemd = modulemd;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public SubmittedRequest withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("owner")
    public String getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public SubmittedRequest withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    @JsonProperty("scmurl")
    public String getScmurl() {
        return scmurl;
    }

    @JsonProperty("scmurl")
    public void setScmurl(String scmurl) {
        this.scmurl = scmurl;
    }

    public SubmittedRequest withScmurl(String scmurl) {
        this.scmurl = scmurl;
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

    public SubmittedRequest withState(long state) {
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

    public SubmittedRequest withStateName(String stateName) {
        this.stateName = stateName;
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

    public SubmittedRequest withStateReason(Object stateReason) {
        this.stateReason = stateReason;
        return this;
    }

    @JsonProperty("state_trace")
    public List<StateTrace> getStateTrace() {
        return stateTrace;
    }

    @JsonProperty("state_trace")
    public void setStateTrace(List<StateTrace> stateTrace) {
        this.stateTrace = stateTrace;
    }

    public SubmittedRequest withStateTrace(List<StateTrace> stateTrace) {
        this.stateTrace = stateTrace;
        return this;
    }

    @JsonProperty("state_url")
    public String getStateUrl() {
        return stateUrl;
    }

    @JsonProperty("state_url")
    public void setStateUrl(String stateUrl) {
        this.stateUrl = stateUrl;
    }

    public SubmittedRequest withStateUrl(String stateUrl) {
        this.stateUrl = stateUrl;
        return this;
    }

    @JsonProperty("stream")
    public String getStream() {
        return stream;
    }

    @JsonProperty("stream")
    public void setStream(String stream) {
        this.stream = stream;
    }

    public SubmittedRequest withStream(String stream) {
        this.stream = stream;
        return this;
    }

    @JsonProperty("tasks")
    public Tasks getTasks() {
        return tasks;
    }

    @JsonProperty("tasks")
    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public SubmittedRequest withTasks(Tasks tasks) {
        this.tasks = tasks;
        return this;
    }

    @JsonProperty("time_completed")
    public Object getTimeCompleted() {
        return timeCompleted;
    }

    @JsonProperty("time_completed")
    public void setTimeCompleted(Object timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public SubmittedRequest withTimeCompleted(Object timeCompleted) {
        this.timeCompleted = timeCompleted;
        return this;
    }

    @JsonProperty("time_modified")
    public String getTimeModified() {
        return timeModified;
    }

    @JsonProperty("time_modified")
    public void setTimeModified(String timeModified) {
        this.timeModified = timeModified;
    }

    public SubmittedRequest withTimeModified(String timeModified) {
        this.timeModified = timeModified;
        return this;
    }

    @JsonProperty("time_submitted")
    public String getTimeSubmitted() {
        return timeSubmitted;
    }

    @JsonProperty("time_submitted")
    public void setTimeSubmitted(String timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public SubmittedRequest withTimeSubmitted(String timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
        return this;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public SubmittedRequest withVersion(String version) {
        this.version = version;
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

    public SubmittedRequest withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return "SubmittedRequest{" +
                "componentBuilds=" + componentBuilds +
                ", id=" + id +
                ", kojiTag=" + kojiTag +
                ", modulemd='" + modulemd + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", scmurl='" + scmurl + '\'' +
                ", state=" + state +
                ", stateName='" + stateName + '\'' +
                ", stateReason=" + stateReason +
                ", stateTrace=" + stateTrace +
                ", stateUrl='" + stateUrl + '\'' +
                ", stream='" + stream + '\'' +
                ", tasks=" + tasks +
                ", timeCompleted=" + timeCompleted +
                ", timeModified='" + timeModified + '\'' +
                ", timeSubmitted='" + timeSubmitted + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public boolean isModuleReady() {
        return getState() == 5;
    }

}
