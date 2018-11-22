package Modele;

/**
 * Created by Aurélie on 19/08/2017.
 */
public enum Niveau {
    V("", "vert"),
    B("CHEMIN / MARCASSINS", "bleu"),
    R("PISTE / RENARDS", "rouge"),
    N("SENTIER / COYOTES", "noir");

    private String nom;
    private String couleur;

    Niveau(String nom, String couleur) {
        this.nom = nom;
        this.couleur = couleur;
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
    
    public static Niveau get(String nom) {
    	switch (nom) {
    	case "":
    		return V;
    	case "CHEMIN / MARCASSINS" :
    		return B;
    	case "PISTE / RENARDS" : 
    		return R;
    	case "SENTIER / COYOTES" :
    		return N;
    	}
		return null;
    }

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
}
