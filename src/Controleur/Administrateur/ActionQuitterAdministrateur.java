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
		// Si modification
		if (!this.fp.getProjet().getCritEnreg()) {
			int resultatLicencie = javax.swing.JOptionPane.showConfirmDialog(this.fp.getDesktop(),
					"Voulez-vous enregistrer les modifications sur les licenciés ?");

			// Si oui
			if (resultatLicencie == 0) {
				// Enregistrer les modifications
				this.fp.getProjet().enregistrer();
			}
			// Sinon si oui ou non
			else if (resultatLicencie != 2) {
				// fermer le mode admin
				this.fp.fermerModeAdmin();
			}
		} else {
			// fermer le mode admin
			this.fp.getDesktop().setBackground(Color.WHITE);
			this.fp.getGrilleLicencies().setVisible(false);
			this.fp.getAdmin().setVisible(false);

			this.fp.getBtnsCrit().setVisible(true);
		}
	}
}