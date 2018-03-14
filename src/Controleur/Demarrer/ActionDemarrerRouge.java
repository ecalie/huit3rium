package Controleur.Demarrer;

import Modele.Niveau;
import Modele.Projet;

public class ActionDemarrerRouge extends ActionDemarrerCouleur {
    public ActionDemarrerRouge(Projet projet) {
        this.niveau = Niveau.R;
        this.projet = projet;
    }
}
