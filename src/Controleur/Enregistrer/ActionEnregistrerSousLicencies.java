package Controleur.Enregistrer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Projet;

public class ActionEnregistrerSousLicencies implements ActionListener {

	Projet projet;
	
	public ActionEnregistrerSousLicencies(Projet projet) {
		this.projet = projet;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.projet.enregistrerSousLicencies();
	}

}
