package controleur.demarrer;

import modele.Niveau;
import modele.Projet;

public class ActionDemarrerBleu extends ActionDemarrerCouleur {
    public ActionDemarrerBleu(Projet projet) {
        this.niveau = Niveau.B;
        this.projet = projet;
    }
}
