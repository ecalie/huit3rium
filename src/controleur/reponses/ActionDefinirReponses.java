package controleur.reponses;

import modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionDefinirReponses implements ActionListener {

    private Projet projet;

    public ActionDefinirReponses(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.projet.afficherFenetreReponses();
    }
}
