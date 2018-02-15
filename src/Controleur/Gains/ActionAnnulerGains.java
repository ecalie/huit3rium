package Controleur.Gains;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Projet;
import Vue.FicheGains;

public class ActionAnnulerGains implements ActionListener{

	private FicheGains ficheDefinitionScore;
	
	public ActionAnnulerGains(Projet projet, FicheGains jif) {
		this.ficheDefinitionScore = jif;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.ficheDefinitionScore.annulerGains();
	}
	
	

}
