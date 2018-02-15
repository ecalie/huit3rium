package Controleur.Parametres;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Projet;

/**
 * Created by Aurélie.
 */
public class ActionModifParam implements ActionListener {
    private Projet projet;

    public ActionModifParam(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	this.projet.afficherFicheParametre();
    }
}
