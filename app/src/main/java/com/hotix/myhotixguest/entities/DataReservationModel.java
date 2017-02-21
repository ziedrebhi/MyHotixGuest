package com.hotix.myhotixguest.entities;

/**
 * Created by ziedrebhi on 21/02/2017.
 */

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "status"
})
public class DataReservationModel implements Serializable {

    private final static long serialVersionUID = 7769243857241221151L;
    @JsonProperty("data")
    private DataRes data;
    @JsonProperty("status")
    private boolean status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public DataReservationModel() {
    }

    /**
     * @param status
     * @param data
     */
    public DataReservationModel(DataRes data, boolean status) {
        super();
        this.data = data;
        this.status = status;
    }

    @JsonProperty("data")
    public DataRes getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(DataRes data) {
        this.data = data;
    }

    @JsonProperty("status")
    public boolean isStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(boolean status) {
        this.status = status;
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