package com.hotix.myhotixguest.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ziedrebhi on 31/01/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "DateFacturation",
        "Montant",
        "Designation",
        "Arrangement",
        "Comment"
})
public class ItemFactureModel {
    @JsonProperty("DateFacturation")
    private String dateFacturation;
    @JsonProperty("Montant")
    private int montant;
    @JsonProperty("Designation")
    private String designation;
    @JsonProperty("Arrangement")
    private String arrangement;
    @JsonProperty("Comment")
    private String comment;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("DateFacturation")
    public String getDateFacturation() {
        return dateFacturation;
    }

    @JsonProperty("DateFacturation")
    public void setDateFacturation(String dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    @JsonProperty("Montant")
    public int getMontant() {
        return montant;
    }

    @JsonProperty("Montant")
    public void setMontant(int montant) {
        this.montant = montant;
    }

    @JsonProperty("Designation")
    public String getDesignation() {
        return designation;
    }

    @JsonProperty("Designation")
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @JsonProperty("Arrangement")
    public String getArrangement() {
        return arrangement;
    }

    @JsonProperty("Arrangement")
    public void setArrangement(String arrangement) {
        this.arrangement = arrangement;
    }

    @JsonProperty("Comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("Comment")
    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public String toString() {
        return "ItemFactureModel{" +
                "dateFacturation='" + dateFacturation + '\'' +
                ", montant=" + montant +
                ", designation='" + designation + '\'' +
                ", arrangement='" + arrangement + '\'' +
                ", comment='" + comment + '\'' +
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
