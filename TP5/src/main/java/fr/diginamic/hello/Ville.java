package fr.diginamic.hello;

public class Ville {
    private String nom;
    private int nbHabitants;
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
