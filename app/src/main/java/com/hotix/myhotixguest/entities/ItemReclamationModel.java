package com.hotix.myhotixguest.entities;

/**
 * Created by ziedrebhi on 01/02/2017.
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
        "DateCreation",
        "Chambre",
        "Object",
        "Description",
        "Traite"
})
public class ItemReclamationModel implements Serializable {

    private final static long serialVersionUID = -4630465528165731606L;
    @JsonProperty("DateCreation")
    private String dateCreation;
    @JsonProperty("Chambre")
    private String chambre;
    @JsonProperty("Object")
    private String object;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Traite")
    private boolean traite;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public ItemReclamationModel() {
    }

    /**
     * @param dateCreation
     * @param chambre
     * @param description
     * @param traite
     * @param object
     */
    public ItemReclamationModel(String dateCreation, String chambre, String object, String description, boolean traite) {
        super();
        this.dateCreation = dateCreation;
        this.chambre = chambre;
        this.object = object;
        this.description = description;
        this.traite = traite;
    }

    @JsonProperty("DateCreation")
    public String getDateCreation() {
        return dateCreation;
    }

    @JsonProperty("DateCreation")
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    @JsonProperty("Chambre")
    public String getChambre() {
        return chambre;
    }

    @JsonProperty("Chambre")
    public void setChambre(String chambre) {
        this.chambre = chambre;
    }

    @JsonProperty("Object")
    public String getObject() {
        return object;
    }

    @JsonProperty("Object")
    public void setObject(String object) {
        this.object = object;
    }

    @JsonProperty("Description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("Description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("Traite")
    public boolean isTraite() {
        return traite;
    }

    @JsonProperty("Traite")
    public void setTraite(boolean traite) {
        this.traite = traite;
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