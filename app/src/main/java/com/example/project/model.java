package com.example.project;

import java.util.Date;

public class model {
    String titre;
    String auteur;
    String Dateedition;
    String description;
    String nombreexempl;
    String disponibilte;

    public String getAuteur() {
        return auteur;
    }

    public model(String Dateedition, String auteur, String titre, String description, String number, String  disponibilte) {
        this.Dateedition =Dateedition;
        this.auteur=auteur;
        this.nombreexempl=number;
        this.titre=titre;
        this.description=description;
        this.disponibilte=disponibilte;
    }

    public String getDateedition() {
        return Dateedition;
    }

    public void setDateedition(String Dateedition) {
       this.Dateedition = Dateedition;
    }

    public model() {
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNombreexempl() {
        return nombreexempl;
    }

    public void setNombreexempl(String nombreexempl) {
        this.nombreexempl = nombreexempl;
    }

    public String getDisponibilte() {
        return disponibilte;
    }

    public void setDisponibilte(String disponibilte) {
        this.disponibilte = disponibilte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
}
