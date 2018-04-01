package Controleur.Administrateur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vue.FenetrePrincipale;

public class ActionQuitterAdministrateur implements ActionListener {
	private FenetrePrincipale fp;

	public ActionQuitterAdministrateur(FenetrePrincipale fp) {
		this.fp = fp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.fp.fermerModeAdmin();
	}
}