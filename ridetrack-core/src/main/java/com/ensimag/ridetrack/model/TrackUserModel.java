package com.ensimag.ridetrack.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class TrackUserModel {
/*
    espace
    application
    groupe
    contrat
  */
    @Id
    @GeneratedValue
    private long id;

    private String nom;

    private String prenom;

    private String pseudo;

    private String email;

    public TrackUserModel(){
    }

    public TrackUserModel(String nom, String prenom, String pseudo, String email){
        this.nom= nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.email=email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getEmail() {
        return email;
    }
}
