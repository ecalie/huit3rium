package Controleur.Score;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Jeune;
import Modele.Projet;
import Vue.FicheScore;

public class ActionModifierScore implements ActionListener {

    private Jeune licencie;
    private Projet projet;

    public ActionModifierScore(Jeune licencie, Projet projet) {
        this.licencie = licencie;
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (!this.projet.getConforme()) {
    		FicheScore fiche = new FicheScore(this.projet);
    		this.licencie.setFiche2(fiche);
    		fiche.setLicencie(this.licencie);
    		fiche.setTitle(this.licencie.toString());
    		this.projet.setConforme();
    	}
        this.licencie.getFiche2().setVisible(true);
    }
}
