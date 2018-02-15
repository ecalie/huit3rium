package Modele;

/**
 * Created by Aurélie on 19/08/2017.
 */
public enum Categorie {
    V("Vert"),
    B("Bleu"),
    R("Rouge"),
    N("Noir");

    private String couleur;

    Categorie(String couleur) {
        this.couleur = couleur;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    @Override
    public String toString() {
        return this.couleur;
    }
}
