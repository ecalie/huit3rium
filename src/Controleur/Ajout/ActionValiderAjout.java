package Controleur.Ajout;

import Modele.Jeune;
import Modele.Projet;
import Vue.FicheJeune;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionValiderAjout implements ActionListener {

	private Projet projet;
	private FicheJeune fiche;

	public ActionValiderAjout(Projet projet, FicheJeune fiche) {
		this.projet = projet;
		this.fiche = fiche;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Boolean stop = false;
		if (this.fiche.getNom2().getText().equals("")) {
			this.fiche.getNom2().setBackground(Color.RED);
			stop = true;
		} else {
			this.fiche.getNom2().setBackground(Color.WHITE);
		}
		if (this.fiche.getPrenom2().getText().equals("")) {
			this.fiche.getPrenom2().setBackground(Color.RED);
			stop = true;
		} else {
			this.fiche.getPrenom2().setBackground(Color.WHITE);
		}
		if (this.fiche.getNumero2().getText().equals("")) {
			this.fiche.getNumero2().setBackground(Color.RED);
			stop = true;
		} else {
			this.fiche.getNumero2().setBackground(Color.WHITE);
		}
		if (this.fiche.getClub2().getSelectedIndex() == 0) {
			this.fiche.getClub2().setBackground(Color.RED);
			stop = true;
		} else {
			this.fiche.getClub2().setBackground(Color.WHITE);
		}
		if (this.fiche.getCate2().getSelectedIndex() == 0) {
			this.fiche.getCate2().setBackground(Color.RED);
			stop = true;
		} else {
			this.fiche.getCate2().setBackground(Color.WHITE);
		}

		if (!stop) {
			// Création d'un jeune correspondant à  la fiche
			Jeune licencie = new Jeune(this.fiche, null);

			// Si création 
			if (this.fiche.getTitle().equals("Ajout d'un licencié")) {
				// Ajout du licencié et mise à jour des données 
				this.projet.ajouterInscrit(licencie);
				this.fiche.setTitle(licencie.toString());
				this.fiche.setLicencie(licencie);
			} else {
				// sinon mise à jour de la fiche
				this.fiche.validerModif();
				this.projet.setLicencieEnreg(false);
			}
			// Mettre à jour la fenêtre principale et masquer la fiche
			this.projet.affichage();
			this.fiche.hide();
			this.projet.getFp().repaint();
		}
	}
}
