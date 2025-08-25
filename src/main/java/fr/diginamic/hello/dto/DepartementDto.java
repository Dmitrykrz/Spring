package fr.diginamic.hello.dto;

public class DepartementDto {
    private int id;
    private String nom;
    private String code;

    public DepartementDto() {
        throw new UnsupportedOperationException("Utility class");
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}