package fr.diginamic.hello;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Departement {


    @Column(unique = true)
    @Id
    private int id;

    @Column(unique = true)
    @NotNull
    private String nom;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ville> villes;

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public List<Ville> getVilles() { return villes; }
    public void setVilles(List<Ville> villes) { this.villes = villes; }
}
