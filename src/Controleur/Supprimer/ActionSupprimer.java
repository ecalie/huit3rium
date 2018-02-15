package Controleur.Supprimer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Projet;

public class ActionSupprimer implements ActionListener {

    private Projet projet;

    public ActionSupprimer(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       this.projet.afficherFenetreSuppression();
    }
}
