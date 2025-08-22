package fr.diginamic.hello.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la classe Ville, utilisé pour la communication avec les clients REST.
 * Il simplifie le modèle de données en évitant les références circulaires.
 */
public class VilleDto {

    private int id;

    @NotNull
    @Size(min=2, message="Le nom de la ville doit avoir plus de 2 caractères.")
    private String nom;

    @Min(1)
    private int nbHabitants;

    // L'ID du département est maintenant utilisé au lieu du nom
    @Min(1)
    private int idDepartement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbHabitants() {
        return nbHabitants;
    }

    public void setNbHabitants(int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }
}