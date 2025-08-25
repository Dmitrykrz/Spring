package fr.diginamic.hello;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    @NotNull
    @Size(min=2, message="Town name must be >2 characters")
    private String nom;


    @Min(1)
    private int nbHabitants;




    /**
     * Constructeur pour créer une ville avec un nom et une population.
     * @param nom  Nom de la ville.
     * @param nbHabitants Nombre d'habitants.
     */
    public Ville(String nom, int nbHabitants) {

        this.nom = nom;
        this.nbHabitants = nbHabitants;

    }

    /**
     * Constructeur par défaut.
     */
    public Ville() {

    }


    @ManyToOne(optional = false)
    @JoinColumn(name = "departement_id", nullable = false)
    @NotNull
    private Departement departement;

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

    public Departement getDepartement() { return departement; }

    public void setDepartement(Departement departement) { this.departement = departement; }


    @Override
    public String toString() {
        return "Ville{" +
                "nom='" + nom + '\'' +
                ", nbHabitants=" + nbHabitants +
                ", id=" + id +
                '}';
    }
}