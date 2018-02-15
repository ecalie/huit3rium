package Controleur.Gains;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Projet;

public class ActionDefinirGains implements ActionListener {

	private Projet projet;
	
	public ActionDefinirGains(Projet projet) {
		this.projet = projet;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.projet.afficherFicheGains();
	}

}
