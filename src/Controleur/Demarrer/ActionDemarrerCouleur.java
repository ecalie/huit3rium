package Controleur.Demarrer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Niveau;
import Modele.Projet;

public abstract class ActionDemarrerCouleur implements ActionListener {

    protected Niveau niveau;
    protected Projet projet;

    @Override
    public void actionPerformed(ActionEvent e) {
        this.projet.affichageSelectionnes(niveau);
    }
}
