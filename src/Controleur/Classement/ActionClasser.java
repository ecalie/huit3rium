package Controleur.Classement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Projet;

public class ActionClasser implements ActionListener {

	private Projet projet;
	
	public ActionClasser(Projet projet) {
		this.projet = projet;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.projet.classement();
	}

}
