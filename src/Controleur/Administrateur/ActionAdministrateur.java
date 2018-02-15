package Controleur.Administrateur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vue.FenetrePrincipale;

public class ActionAdministrateur implements ActionListener {

    private FenetrePrincipale fp;

    public ActionAdministrateur(FenetrePrincipale fp) {
        this.fp = fp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	this.fp.ouvrirModeAdmin();

        // Afficher les numéros de tous les licenciés et les boutons administrateur
        this.fp.getGrilleLicencies().setVisible(true);
        this.fp.getAdmin().setVisible(true);
        
        // Masquer les barres de progression
        this.fp.getZoneProg().setVisible(false);

        // Metter à jour la fenêtre
        this.fp.getProjet().affichage();
        this.fp.repaint();
    }
}
