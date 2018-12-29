package controleur.demarrer;

import modele.Niveau;
import modele.Projet;

public class ActionDemarrerRouge extends ActionDemarrerCouleur {
    public ActionDemarrerRouge(Projet projet) {
        this.niveau = Niveau.R;
        this.projet = projet;
    }
}
