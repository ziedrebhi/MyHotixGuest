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
public class RestaurantModel implements Serializable {

    private final static long serialVersionUID = 5638389831104703493L;
    @JsonProperty("data")
    private List<ItemRestaurantModel> data = null;
    @JsonProperty("status")
    private boolean status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public RestaurantModel() {
    }

    /**
     * @param status
     * @param data
     */
    public RestaurantModel(List<ItemRestaurantModel> data, boolean status) {
        super();
        this.data = data;
        this.status = status;
    }

    @JsonProperty("data")
    public List<ItemRestaurantModel> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<ItemRestaurantModel> data) {
        this.data = data;
    }

    public RestaurantModel withData(List<ItemRestaurantModel> data) {
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

    public RestaurantModel withStatus(boolean status) {
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

    public RestaurantModel withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}