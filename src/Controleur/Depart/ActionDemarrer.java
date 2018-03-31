package Controleur.Depart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Projet;

public class ActionDemarrer implements ActionListener {

	private Projet projet;
	
	public ActionDemarrer(Projet projet) {
		this.projet = projet;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.projet.demarrer();
	}

}
