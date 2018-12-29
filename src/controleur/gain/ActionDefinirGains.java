package controleur.gain;

import modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionDefinirGains implements ActionListener {

    private Projet projet;

    public ActionDefinirGains(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.projet.afficherFicheGains();
    }

}
