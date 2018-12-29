package controleur.suppr;

import modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
