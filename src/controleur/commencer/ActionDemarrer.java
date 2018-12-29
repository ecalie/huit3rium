package controleur.commencer;

import modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionDemarrer implements ActionListener {

    private Projet projet;

    public ActionDemarrer(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.projet.demarrer();
    }

}
