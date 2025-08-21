package fr.diginamic.hello;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Ville {
    @NotNull
    @Size(min=2, message="Town name must be >2 characters")
    private String nom;

    @Min(1)
    private int nbHabitants;

    @Min(0)
    private int id;


    /**
     * @param nom  Name of the ville
     * @param nbHabitants numper of people
     */
    public Ville(String nom, int nbHabitants) {

        this.nom = nom;
        this.nbHabitants = nbHabitants;

    }

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

    @Override
    public String toString() {
        return "Ville{" +
                "nom='" + nom + '\'' +
                ", nbHabitants=" + nbHabitants +
                ", id=" + id +
                '}';
    }
}
