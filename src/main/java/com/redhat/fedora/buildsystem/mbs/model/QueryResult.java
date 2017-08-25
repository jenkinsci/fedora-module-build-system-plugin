
package com.redhat.fedora.buildsystem.mbs.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "items",
    "meta"
})
public class QueryResult implements Serializable {

    @JsonProperty("items")
    private List<Item> items = null;
    @JsonProperty("meta")
    private Meta meta;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public QueryResult() {
    }

    /**
     * 
     * @param items
     * @param meta
     */
    public QueryResult(List<Item> items, Meta meta) {
        super();
        this.items = items;
        this.meta = meta;
    }

    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @JsonProperty("meta")
    public Meta getMeta() {
        return meta;
    }

    @JsonProperty("meta")
    public void setMeta(Meta meta) {
        this.meta = meta;
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
        return "QueryResult{" +
                "items=" + items +
                ", meta=" + meta +
                '}';
    }

    public boolean isModuleReady(long id) {
        if (items != null) {
            for (Item item: items) {
                if (item.getId() == id) {
                    if (item.getState() == 5) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
