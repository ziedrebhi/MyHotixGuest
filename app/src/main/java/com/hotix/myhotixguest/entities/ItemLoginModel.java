package com.hotix.myhotixguest.entities;

/**
 * Created by ziedrebhi on 02/02/2017.
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
        "Name",
        "DateArrivee",
        "DateDepart",
        "Master",
        "Produit",
        "Qualite",
        "Arrangement"
})
public class ItemLoginModel implements Serializable {

    private final static long serialVersionUID = -7453183459035118183L;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("DateArrivee")
    private String dateArrivee;
    @JsonProperty("DateDepart")
    private String dateDepart;
    @JsonProperty("Master")
    private boolean master;
    @JsonProperty("Produit")
    private String produit;
    @JsonProperty("Qualite")
    private String qualite;
    @JsonProperty("Arrangement")
    private String arrangement;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public ItemLoginModel() {
    }

    /**
     * @param produit
     * @param arrangement
     * @param dateDepart
     * @param name
     * @param qualite
     * @param dateArrivee
     * @param master
     */
    public ItemLoginModel(String name, String dateArrivee, String dateDepart, boolean master, String produit, String qualite, String arrangement) {
        super();
        this.name = name;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.master = master;
        this.produit = produit;
        this.qualite = qualite;
        this.arrangement = arrangement;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    public ItemLoginModel withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("DateArrivee")
    public String getDateArrivee() {
        return dateArrivee;
    }

    @JsonProperty("DateArrivee")
    public void setDateArrivee(String dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public ItemLoginModel withDateArrivee(String dateArrivee) {
        this.dateArrivee = dateArrivee;
        return this;
    }

    @JsonProperty("DateDepart")
    public String getDateDepart() {
        return dateDepart;
    }

    @JsonProperty("DateDepart")
    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public ItemLoginModel withDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
        return this;
    }

    @JsonProperty("Master")
    public boolean isMaster() {
        return master;
    }

    @JsonProperty("Master")
    public void setMaster(boolean master) {
        this.master = master;
    }

    public ItemLoginModel withMaster(boolean master) {
        this.master = master;
        return this;
    }

    @JsonProperty("Produit")
    public String getProduit() {
        return produit;
    }

    @JsonProperty("Produit")
    public void setProduit(String produit) {
        this.produit = produit;
    }

    public ItemLoginModel withProduit(String produit) {
        this.produit = produit;
        return this;
    }

    @JsonProperty("Qualite")
    public String getQualite() {
        return qualite;
    }

    @JsonProperty("Qualite")
    public void setQualite(String qualite) {
        this.qualite = qualite;
    }

    public ItemLoginModel withQualite(String qualite) {
        this.qualite = qualite;
        return this;
    }

    @JsonProperty("Arrangement")
    public String getArrangement() {
        return arrangement;
    }

    @JsonProperty("Arrangement")
    public void setArrangement(String arrangement) {
        this.arrangement = arrangement;
    }

    public ItemLoginModel withArrangement(String arrangement) {
        this.arrangement = arrangement;
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

    public ItemLoginModel withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}