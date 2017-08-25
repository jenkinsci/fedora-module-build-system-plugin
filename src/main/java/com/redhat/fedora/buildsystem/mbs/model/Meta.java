
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
    "first",
    "last",
    "page",
    "pages",
    "per_page",
    "total"
})
public class Meta implements Serializable {

    @JsonProperty("first")
    private String first;
    @JsonProperty("last")
    private String last;
    @JsonProperty("page")
    private Long page;
    @JsonProperty("pages")
    private Long pages;

    @Override
    public String toString() {
        return "Meta{" +
                "first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", page=" + page +
                ", pages=" + pages +
                ", perPage=" + perPage +
                ", total=" + total +
                '}';
    }

    @JsonProperty("per_page")
    private Long perPage;
    @JsonProperty("total")
    private Long total;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Meta() {
    }

    /**
     * 
     * @param total
     * @param last
     * @param page
     * @param pages
     * @param perPage
     * @param first
     */
    public Meta(String first, String last, Long page, Long pages, Long perPage, Long total) {
        super();
        this.first = first;
        this.last = last;
        this.page = page;
        this.pages = pages;
        this.perPage = perPage;
        this.total = total;
    }

    @JsonProperty("first")
    public String getFirst() {
        return first;
    }

    @JsonProperty("first")
    public void setFirst(String first) {
        this.first = first;
    }

    @JsonProperty("last")
    public String getLast() {
        return last;
    }

    @JsonProperty("last")
    public void setLast(String last) {
        this.last = last;
    }

    @JsonProperty("page")
    public Long getPage() {
        return page;
    }

    @JsonProperty("page")
    public void setPage(Long page) {
        this.page = page;
    }

    @JsonProperty("pages")
    public Long getPages() {
        return pages;
    }

    @JsonProperty("pages")
    public void setPages(Long pages) {
        this.pages = pages;
    }

    @JsonProperty("per_page")
    public Long getPerPage() {
        return perPage;
    }

    @JsonProperty("per_page")
    public void setPerPage(Long perPage) {
        this.perPage = perPage;
    }

    @JsonProperty("total")
    public Long getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Long total) {
        this.total = total;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
