package com.hotix.myhotixguest.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ziedrebhi on 01/02/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Id",
        "Nom",
        "Description",
        "Capacite",
        "HeureOuvert",
        "HeureFermeture",
        "NumTel"
})
public class ItemRestaurantModel implements Serializable {

    private final static long serialVersionUID = 9185086934057455691L;
    @JsonProperty("Id")
    private long id;
    @JsonProperty("Nom")
    private String nom;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Capacite")
    private long capacite;
    @JsonProperty("HeureOuvert")
    private String heureOuvert;
    @JsonProperty("HeureFermeture")
    private String heureFermeture;
    @JsonProperty("NumTel")
    private long numTel;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public ItemRestaurantModel() {
    }

    /**
     * @param heureOuvert
     * @param id
     * @param capacite
     * @param numTel
     * @param heureFermeture
     * @param description
     * @param nom
     */
    public ItemRestaurantModel(long id, String nom, String description, long capacite, String heureOuvert, String heureFermeture, long numTel) {
        super();
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.capacite = capacite;
        this.heureOuvert = heureOuvert;
        this.heureFermeture = heureFermeture;
        this.numTel = numTel;
    }

    @JsonProperty("Id")
    public long getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(long id) {
        this.id = id;
    }

    public ItemRestaurantModel withId(long id) {
        this.id = id;
        return this;
    }

    @JsonProperty("Nom")
    public String getNom() {
        return nom;
    }

    @JsonProperty("Nom")
    public void setNom(String nom) {
        this.nom = nom;
    }

    public ItemRestaurantModel withNom(String nom) {
        this.nom = nom;
        return this;
    }

    @JsonProperty("Description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("Description")
    public void setDescription(String description) {
        this.description = description;
    }

    public ItemRestaurantModel withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("Capacite")
    public long getCapacite() {
        return capacite;
    }

    @JsonProperty("Capacite")
    public void setCapacite(long capacite) {
        this.capacite = capacite;
    }

    public ItemRestaurantModel withCapacite(long capacite) {
        this.capacite = capacite;
        return this;
    }

    @JsonProperty("HeureOuvert")
    public String getHeureOuvert() {
        return heureOuvert;
    }

    @JsonProperty("HeureOuvert")
    public void setHeureOuvert(String heureOuvert) {
        this.heureOuvert = heureOuvert;
    }

    public ItemRestaurantModel withHeureOuvert(String heureOuvert) {
        this.heureOuvert = heureOuvert;
        return this;
    }

    @JsonProperty("HeureFermeture")
    public String getHeureFermeture() {
        return heureFermeture;
    }

    @JsonProperty("HeureFermeture")
    public void setHeureFermeture(String heureFermeture) {
        this.heureFermeture = heureFermeture;
    }

    public ItemRestaurantModel withHeureFermeture(String heureFermeture) {
        this.heureFermeture = heureFermeture;
        return this;
    }

    @JsonProperty("NumTel")
    public long getNumTel() {
        return numTel;
    }

    @JsonProperty("NumTel")
    public void setNumTel(long numTel) {
        this.numTel = numTel;
    }

    public ItemRestaurantModel withNumTel(long numTel) {
        this.numTel = numTel;
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

    public ItemRestaurantModel withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}