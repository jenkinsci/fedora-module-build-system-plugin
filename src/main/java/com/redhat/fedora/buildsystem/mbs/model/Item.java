
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
    "id",
    "koji_tag",
    "name",
    "owner",
    "state",
    "state_name",
    "state_reason",
    "tasks",
    "time_completed",
    "time_modified",
    "time_submitted"
})
public class Item implements Serializable {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("koji_tag")
    private String kojiTag;
    @JsonProperty("name")
    private String name;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("state")
    private Long state;
    @JsonProperty("state_name")
    private String stateName;
    @JsonProperty("state_reason")
    private Object stateReason;
    @JsonProperty("tasks")
    private Tasks tasks;
    @JsonProperty("time_completed")
    private String timeCompleted;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("time_submitted")
    private String timeSubmitted;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
    }

    /**
     * 
     * @param id
     * @param stateReason
     * @param kojiTag
     * @param timeSubmitted
     * @param stateName
     * @param name
     * @param timeModified
     * @param state
     * @param owner
     * @param timeCompleted
     * @param tasks
     */
    public Item(Long id, String kojiTag, String name, String owner, Long state, String stateName, Object stateReason, Tasks tasks, String timeCompleted, String timeModified, String timeSubmitted) {
        super();
        this.id = id;
        this.kojiTag = kojiTag;
        this.name = name;
        this.owner = owner;
        this.state = state;
        this.stateName = stateName;
        this.stateReason = stateReason;
        this.tasks = tasks;
        this.timeCompleted = timeCompleted;
        this.timeModified = timeModified;
        this.timeSubmitted = timeSubmitted;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("koji_tag")
    public String getKojiTag() {
        return kojiTag;
    }

    @JsonProperty("koji_tag")
    public void setKojiTag(String kojiTag) {
        this.kojiTag = kojiTag;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("owner")
    public String getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @JsonProperty("state")
    public Long getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(Long state) {
        this.state = state;
    }

    @JsonProperty("state_name")
    public String getStateName() {
        return stateName;
    }

    @JsonProperty("state_name")
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @JsonProperty("state_reason")
    public Object getStateReason() {
        return stateReason;
    }

    @JsonProperty("state_reason")
    public void setStateReason(Object stateReason) {
        this.stateReason = stateReason;
    }

    @JsonProperty("tasks")
    public Tasks getTasks() {
        return tasks;
    }

    @JsonProperty("tasks")
    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    @JsonProperty("time_completed")
    public String getTimeCompleted() {
        return timeCompleted;
    }

    @JsonProperty("time_completed")
    public void setTimeCompleted(String timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    @JsonProperty("time_modified")
    public String getTimeModified() {
        return timeModified;
    }

    @JsonProperty("time_modified")
    public void setTimeModified(String timeModified) {
        this.timeModified = timeModified;
    }

    @JsonProperty("time_submitted")
    public String getTimeSubmitted() {
        return timeSubmitted;
    }

    @JsonProperty("time_submitted")
    public void setTimeSubmitted(String timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", kojiTag='" + kojiTag + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", state=" + state +
                ", stateName='" + stateName + '\'' +
                ", stateReason=" + stateReason +
                ", tasks=" + tasks +
                ", timeCompleted='" + timeCompleted + '\'' +
                ", timeModified='" + timeModified + '\'' +
                ", timeSubmitted='" + timeSubmitted + '\'' +
                '}';
    }
}
