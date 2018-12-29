package controleur.demarrer;

import modele.Niveau;
import modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ActionDemarrerCouleur implements ActionListener {

    protected Niveau niveau;
    protected Projet projet;

    @Override
    public void actionPerformed(ActionEvent e) {
        this.projet.affichageSelectionnes(niveau);
    }
}
