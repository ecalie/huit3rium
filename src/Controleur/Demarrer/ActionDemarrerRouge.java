package Controleur.Demarrer;

import Modele.Projet;

public class ActionDemarrerRouge extends ActionDemarrerCouleur {
    public ActionDemarrerRouge(Projet projet) {
        this.couleur = "Rouge";
        this.projet = projet;
    }
}
