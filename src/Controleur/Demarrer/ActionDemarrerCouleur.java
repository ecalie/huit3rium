package Controleur.Demarrer;

import Modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ActionDemarrerCouleur implements ActionListener {

    protected String couleur;
    protected Projet projet;

    @Override
    public void actionPerformed(ActionEvent e) {
        this.projet.affichageSelectionnes(couleur);
    }
}
