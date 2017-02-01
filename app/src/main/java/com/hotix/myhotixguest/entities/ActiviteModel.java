package com.hotix.myhotixguest.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ziedrebhi on 01/02/2017.
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "status"
})
public class ActiviteModel implements Serializable {

    private final static long serialVersionUID = 2763925257690996163L;
    @JsonProperty("data")
    private List<ItemActiviteModel> data = null;
    @JsonProperty("status")
    private boolean status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public ActiviteModel() {
    }

    /**
     * @param status
     * @param data
     */
    public ActiviteModel(List<ItemActiviteModel> data, boolean status) {
        super();
        this.data = data;
        this.status = status;
    }

    @JsonProperty("data")
    public List<ItemActiviteModel> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<ItemActiviteModel> data) {
        this.data = data;
    }

    public ActiviteModel withData(List<ItemActiviteModel> data) {
        this.data = data;
        return this;
    }

    @JsonProperty("status")
    public boolean isStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(boolean status) {
        this.status = status;
    }

    public ActiviteModel withStatus(boolean status) {
        this.status = status;
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

    public ActiviteModel withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}