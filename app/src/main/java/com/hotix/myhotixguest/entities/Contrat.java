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
        "Id",
        "DateDebut",
        "DateFin"
})
public class Contrat implements Serializable {

    private final static long serialVersionUID = 641939247830766896L;
    @JsonProperty("Id")
    private int id;
    @JsonProperty("DateDebut")
    private String dateDebut;
    @JsonProperty("DateFin")
    private String dateFin;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public Contrat() {
    }

    /**
     * @param id
     * @param dateFin
     * @param dateDebut
     */
    public Contrat(int id, String dateDebut, String dateFin) {
        super();
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    @JsonProperty("Id")
    public int getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("DateDebut")
    public String getDateDebut() {
        return dateDebut;
    }

    @JsonProperty("DateDebut")
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    @JsonProperty("DateFin")
    public String getDateFin() {
        return dateFin;
    }

    @JsonProperty("DateFin")
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
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