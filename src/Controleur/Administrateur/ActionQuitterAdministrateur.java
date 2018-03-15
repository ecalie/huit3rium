package Controleur.Administrateur;

import Vue.FenetrePrincipale;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionQuitterAdministrateur implements ActionListener {
	private FenetrePrincipale fp;

	public ActionQuitterAdministrateur(FenetrePrincipale fp) {
		this.fp = fp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!this.fp.getProjet().getLicencieEnreg()) {
			int resultatLicencie = javax.swing.JOptionPane.showConfirmDialog(this.fp.getDesktop(),
					"Voulez-vous enregistrer les modifications sur les licenciés ?");

			// Si oui
			if (resultatLicencie == 0) {
				// Enregistrer les modifications
				this.fp.getProjet().enregistrerInscrit();
			} 

			// Si modification du crit
			if (!this.fp.getProjet().getCritEnreg()) {
				int resultatCrit = javax.swing.JOptionPane.showConfirmDialog(this.fp.getDesktop(),
						"Voulez-vous enregistrer les modifications sur le critérium ?");

				// Si oui
				if (resultatCrit == 0) {
					// Enregistrer le critérium
					this.fp.getProjet().enregistrerResultats();
				}

				// Si oui ou non 
				if (resultatCrit != 2) {
					// fermer le mode admin
					this.fp.fermerModeAdmin();
				}
				
			// Sinon si oui ou non
			} else if (resultatLicencie != 2) {
				// fermer le mode admin
				this.fp.fermerModeAdmin();
			}
			
		// Si modification que du critérium
		} else if (!this.fp.getProjet().getCritEnreg()) {
			int resultatCrit = javax.swing.JOptionPane.showConfirmDialog(this.fp.getDesktop(),
					"Voulez-vous enregistrer les modifications sur le critérium ?");

			// Si oui
			if (resultatCrit == 0) {
				// Enregistrer le critérium
				this.fp.getProjet().enregistrerResultats();
			}

			// Si oui ou non 
			if (resultatCrit != 2) {
				// fermer le mode admin
				this.fp.getDesktop().setBackground(Color.WHITE);
				this.fp.getGrilleLicencies().setVisible(false);
				this.fp.getAdmin().setVisible(false);

				this.fp.getBtnsCrit().setVisible(true);
				this.fp.getBtnsDem().setVisible(true);
			}
			
		} else {
			// fermer le mode admin
			this.fp.getDesktop().setBackground(Color.WHITE);
			this.fp.getGrilleLicencies().setVisible(false);
			this.fp.getAdmin().setVisible(false);

			this.fp.getBtnsCrit().setVisible(true);
			this.fp.getBtnsDem().setVisible(true);
		}
	}
}