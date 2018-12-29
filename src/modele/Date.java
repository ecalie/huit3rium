package modele;

public class Date {

    private int annee;
    private int mois;
    private int jour;

    /**
     * Construire une date à partir du mois, de l'année et du jour.
     *
     * @param a : l'année
     * @param m : le mois
     * @param j : le jour
     */
    Date(int a, int m, int j) {
        this.annee = a;
        this.mois = m;
        this.jour = j;
    }

    public int diff(Date d) {
        return d.annee - this.annee;
    }

    @Override
    public String toString() {
        return jour + "/" + mois + "/" + annee;
    }

}
