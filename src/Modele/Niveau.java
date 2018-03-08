package Modele;

/**
 * Created by Aurélie on 19/08/2017.
 */
public enum Niveau {
    V(""),
    B("CHEMIN / MARCASSINS"),
    R("PISTE / RENARDS"),
    N("SENTIER / COYOTES");

    private String nom;

    Niveau(String couleur) {
        this.nom = couleur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String couleur) {
        this.nom = couleur;
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
