package Controleur.Selectionner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Projet;

/**
 * Created by Aurélie on 15/10/2017.
 */

public class ActionSelectionner implements ActionListener {

    protected Projet projet;

    public ActionSelectionner(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	this.projet.afficherFenetresSelection();
    }
}
