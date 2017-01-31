package com.hotix.myhotixguest.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by ziedrebhi on 31/01/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FactureModel {

    List<FactureItemModel> data;
    Boolean status;

    public FactureModel() {
    }

    @Override
    public String toString() {
        return "FactureModel{" +
                "data=" + data +
                ", status=" + status +
                '}';
    }

    public List<FactureItemModel> getData() {
        return data;
    }

    public void setData(List<FactureItemModel> data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FactureItemModel {
        String dateFacturation;
        float Montant;
        String Designation;
        String Arrangement;
        String Comment;

        public FactureItemModel() {
        }

        public FactureItemModel(String dateFacturation, float montant, String designation, String arrangement, String comment) {
            this.dateFacturation = dateFacturation;
            Montant = montant;
            Designation = designation;
            Arrangement = arrangement;
            Comment = comment;
        }

        @Override
        public String toString() {
            return "FactureItemModel{" +
                    "dateFacturation='" + dateFacturation + '\'' +
                    ", Montant=" + Montant +
                    ", Designation='" + Designation + '\'' +
                    ", Arrangement='" + Arrangement + '\'' +
                    ", Comment='" + Comment + '\'' +
                    '}';
        }

        public String getDateFacturation() {
            return dateFacturation;
        }

        public void setDateFacturation(String dateFacturation) {
            this.dateFacturation = dateFacturation;
        }

        public float getMontant() {
            return Montant;
        }

        public void setMontant(float montant) {
            Montant = montant;
        }

        public String getDesignation() {
            return Designation;
        }

        public void setDesignation(String designation) {
            Designation = designation;
        }

        public String getArrangement() {
            return Arrangement;
        }

        public void setArrangement(String arrangement) {
            Arrangement = arrangement;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }
    }
}
