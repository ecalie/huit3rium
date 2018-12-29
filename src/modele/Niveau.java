package modele;

import java.awt.*;

/**
 * Created by Aurélie on 19/08/2017.
 */
public enum Niveau {
    V("", "vert", new Color(14, 106, 0)),
    B("CHEMIN / MARCASSINS", "bleu", new Color(0, 0, 110)),
    R("PISTE / RENARDS", "rouge", new Color(175, 0, 0)),
    N("SENTIER / COYOTES", "noir", new Color(0, 0, 0));

    private String nom;
    private String couleur;
    private Color color;

    Niveau(String nom, String couleur, Color color) {
        this.color = color;
        this.nom = nom;
        this.couleur = couleur;
    }

    public static Niveau get(String nom) {
        switch (nom) {
            case "":
                return V;
            case "CHEMIN / MARCASSINS":
                return B;
            case "PISTE / RENARDS":
                return R;
            case "SENTIER / COYOTES":
                return N;
        }
        return null;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String couleur) {
        this.nom = couleur;
    }

    public Color getColor() {
        return color;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
