package Controleur.Demarrer;

import Modele.Niveau;
import Modele.Projet;

public class ActionDemarrerVert extends ActionDemarrerCouleur {
    public ActionDemarrerVert(Projet projet) {
        this.niveau = Niveau.V;
        this.projet = projet;
    }
}
