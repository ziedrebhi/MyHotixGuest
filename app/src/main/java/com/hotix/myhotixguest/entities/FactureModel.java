package com.hotix.myhotixguest.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ziedrebhi on 31/01/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "status"
})
public class FactureModel {

    @JsonProperty("data")
    private List<ItemFactureModel> data = null;
    @JsonProperty("status")
    private boolean status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("data")
    public List<ItemFactureModel> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<ItemFactureModel> data) {
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

    @Override
    public String toString() {
        return "FactureModel{" +
                "data=" + data +
                ", status=" + status +
                ", additionalProperties=" + additionalProperties +
                '}';
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