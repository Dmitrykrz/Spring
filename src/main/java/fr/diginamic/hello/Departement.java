package fr.diginamic.hello;


import jakarta.persistence.*;

import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Departement {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code")
    private String code;

    @Size(max = 255)
    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ville> villes;

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public List<Ville> getVilles() { return villes; }
    public void setVilles(List<Ville> villes) { this.villes = villes; }
}
