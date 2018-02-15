package Modele;

/**
 * Created by Aurélie on 16/08/2017.
 */
public enum Club {
    VVF("Vélo Vert Flayoscais"),
    UCPL("Union Cycliste et Pédestre Londaise"),
    CSMS("Club Sportif Municipal Seynois"),
    CCA("Cyclo Club Arcois"),
    VCNPSB("Velo Club Nans les pins la Sainte Baume"),
    VRC("Vélo Randonneur Cantonal"),
    ECCA("Esterel Club Cycliste Adrets"),
    CCL("Cyclo Club Lucois"),
    LVC("La Valette Cyclotourisme");

    private String nom = "";

    Club(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
