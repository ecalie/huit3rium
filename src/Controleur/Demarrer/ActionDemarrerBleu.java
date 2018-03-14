package Controleur.Demarrer;

import Modele.Niveau;
import Modele.Projet;

public class ActionDemarrerBleu extends ActionDemarrerCouleur {
    public ActionDemarrerBleu(Projet projet) {
        this.niveau = Niveau.B;
        this.projet = projet;
    }
}
