package controleur.classement;

import modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionAfficherClassement implements ActionListener {

    private Projet projet;

    public ActionAfficherClassement(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.projet.afficherQuelClassement();
    }

}
