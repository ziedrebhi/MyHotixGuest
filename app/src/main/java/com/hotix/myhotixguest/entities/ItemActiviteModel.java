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
        "Prix",
        "Nom",
        "Categorie",
        "Description",
        "DateDebut",
        "DateFin"
})
public class ItemActiviteModel implements Serializable {

    private final static long serialVersionUID = 2049876673303210460L;
    @JsonProperty("Prix")
    private double prix;
    @JsonProperty("Nom")
    private String nom;
    @JsonProperty("Categorie")
    private String categorie;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("DateDebut")
    private String dateDebut;
    @JsonProperty("DateFin")
    private String dateFin;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public ItemActiviteModel() {
    }

    /**
     * @param prix
     * @param categorie
     * @param description
     * @param dateFin
     * @param nom
     * @param dateDebut
     */
    public ItemActiviteModel(double prix, String nom, String categorie, String description, String dateDebut, String dateFin) {
        super();
        this.prix = prix;
        this.nom = nom;
        this.categorie = categorie;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    @JsonProperty("Prix")
    public double getPrix() {
        return prix;
    }

    @JsonProperty("Prix")
    public void setPrix(double prix) {
        this.prix = prix;
    }

    public ItemActiviteModel withPrix(double prix) {
        this.prix = prix;
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

    public ItemActiviteModel withNom(String nom) {
        this.nom = nom;
        return this;
    }

    @JsonProperty("Categorie")
    public String getCategorie() {
        return categorie;
    }

    @JsonProperty("Categorie")
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public ItemActiviteModel withCategorie(String categorie) {
        this.categorie = categorie;
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

    public ItemActiviteModel withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("DateDebut")
    public String getDateDebut() {
        return dateDebut;
    }

    @JsonProperty("DateDebut")
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ItemActiviteModel withDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    @JsonProperty("DateFin")
    public String getDateFin() {
        return dateFin;
    }

    @JsonProperty("DateFin")
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public ItemActiviteModel withDateFin(String dateFin) {
        this.dateFin = dateFin;
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

    public ItemActiviteModel withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}

