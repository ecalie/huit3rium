package controleur.demarrer;

import modele.Niveau;
import modele.Projet;

public class ActionDemarrerVert extends ActionDemarrerCouleur {
    public ActionDemarrerVert(Projet projet) {
        this.niveau = Niveau.V;
        this.projet = projet;
    }
}
