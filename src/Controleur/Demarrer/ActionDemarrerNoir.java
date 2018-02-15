package Controleur.Demarrer;

import Modele.Projet;

public class ActionDemarrerNoir extends ActionDemarrerCouleur {
    public ActionDemarrerNoir(Projet projet) {
        this.couleur = "Noir";
        this.projet = projet;
    }
}
