package Controleur.Ajout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vue.FicheJeune;

public class ActionValiderAjout implements ActionListener {

	private FicheJeune fiche;

	public ActionValiderAjout(FicheJeune fiche) {
		this.fiche = fiche;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.fiche.validerModif();
	}
}
