package Controleur.Demarrer;

import Modele.Niveau;
import Modele.Projet;

public class ActionDemarrerNoir extends ActionDemarrerCouleur {
    public ActionDemarrerNoir(Projet projet) {
        this.niveau = Niveau.N;
        this.projet = projet;
    }
}
