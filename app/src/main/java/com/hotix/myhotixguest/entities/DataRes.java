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
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Tarifs",
        "Arrangements",
        "TypeProduits",
        "Contrat"
})
public class DataRes implements Serializable {

    private final static long serialVersionUID = 7494798640477567553L;
    @JsonProperty("Tarifs")
    private List<Tarif> tarifs = null;
    @JsonProperty("Arrangements")
    private List<Arrangement> arrangements = null;
    @JsonProperty("TypeProduits")
    private List<TypeProduit> typeProduits = null;
    @JsonProperty("Contrat")
    private Contrat contrat;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public DataRes() {
    }

    /**
     * @param typeProduits
     * @param tarifs
     * @param contrat
     * @param arrangements
     */
    public DataRes(List<Tarif> tarifs, List<Arrangement> arrangements, List<TypeProduit> typeProduits, Contrat contrat) {
        super();
        this.tarifs = tarifs;
        this.arrangements = arrangements;
        this.typeProduits = typeProduits;
        this.contrat = contrat;
    }

    @JsonProperty("Tarifs")
    public List<Tarif> getTarifs() {
        return tarifs;
    }

    @JsonProperty("Tarifs")
    public void setTarifs(List<Tarif> tarifs) {
        this.tarifs = tarifs;
    }

    @JsonProperty("Arrangements")
    public List<Arrangement> getArrangements() {
        return arrangements;
    }

    @JsonProperty("Arrangements")
    public void setArrangements(List<Arrangement> arrangements) {
        this.arrangements = arrangements;
    }

    @JsonProperty("TypeProduits")
    public List<TypeProduit> getTypeProduits() {
        return typeProduits;
    }

    @JsonProperty("TypeProduits")
    public void setTypeProduits(List<TypeProduit> typeProduits) {
        this.typeProduits = typeProduits;
    }

    @JsonProperty("Contrat")
    public Contrat getContrat() {
        return contrat;
    }

    @JsonProperty("Contrat")
    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
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