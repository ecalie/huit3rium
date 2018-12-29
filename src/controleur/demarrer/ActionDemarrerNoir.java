package controleur.demarrer;

import modele.Niveau;
import modele.Projet;

public class ActionDemarrerNoir extends ActionDemarrerCouleur {
    public ActionDemarrerNoir(Projet projet) {
        this.niveau = Niveau.N;
        this.projet = projet;
    }
}
