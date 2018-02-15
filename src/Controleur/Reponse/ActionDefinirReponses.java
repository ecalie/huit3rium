package Controleur.Reponse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Projet;

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
