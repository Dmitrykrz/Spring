package fr.diginamic.hello.dto;

import java.util.List;

public class DepartementDto {
    private int id;
    private String nom;
    private List<VilleDto> villes; // List of DTOs to avoid circular reference

    // Getters and setters
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

    public List<VilleDto> getVilles() {
        return villes;
    }

    public void setVilles(List<VilleDto> villes) {
        this.villes = villes;
    }
}