package Vue;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Modele.Jeune;
import Modele.Niveau;
import Modele.Projet;

public class FicheClassement extends JInternalFrame {

	private Projet projet;
	
	/**
	 * Construire une fiche de classement.
	 * @param titre  Le titre de la fenêtre
	 */
	public FicheClassement(Projet projet) {
		super("", true, true, false, true);
		this.projet = projet;
		this.projet.getFp().getDesktop().add(this);
		this.pack();
		this.hide();
	}

	/**
	 * Afficher la fenêtre interne.
	 * @param nbInscrit    Le nombre d'inscrits
	 * @param lesInscrits  L'ensembles des jeunes à afficher
	 * @param couleur      La catégorie à afficher
	 */
	public void afficherCouleur(ArrayList<Jeune> lesInscrits, Niveau niveau) {
		this.setTitle("Classement des " + niveau.getNom());
		
		this.getContentPane().setLayout(new GridBagLayout());

		int i = 0;
		int k = 0;
		int nbPoints = 0;
		
		for (Jeune j : lesInscrits) {
			if (j.getNiveau() == niveau) {
				i++;
				if (nbPoints != j.getPoints())
					k = i;
				nbPoints = j.getPoints();

				// initialiser les labels
				JLabel classement = new JLabel("" + k);
				JLabel nom = new JLabel(j.getNom());
				JLabel prenom = new JLabel(j.getPrenom());
				JLabel club = new JLabel(j.getClub().getNom());
				JLabel points = new JLabel("" + j.getPoints());

				// ajouter les labels
			    GridBagConstraints c = new GridBagConstraints();
			    c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 0;
				c.gridy = i;
				this.add(classement, c);

			    c = new GridBagConstraints();
			    c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 1;
				c.gridy = i;
				this.add(new JLabel("     "), c);

			    c = new GridBagConstraints();
			    c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 2;
				c.gridy = i;
				c.gridwidth = 2;
				this.add(nom, c);

			    c = new GridBagConstraints();
			    c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 4;
				c.gridy = i;
				this.add(new JLabel("     "), c);

			    c = new GridBagConstraints();
			    c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 5;
				c.gridy = i;
				c.gridwidth = 2;
				this.add(prenom, c);

			    c = new GridBagConstraints();
			    c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 7;
				c.gridy = i;
				this.add(new JLabel("     "), c);

			    c = new GridBagConstraints();
			    c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 8;
				c.gridy = i;
				c.gridwidth = 6;
				this.add(club, c);

			    c = new GridBagConstraints();
			    c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 14;
				c.gridy = i;
				this.add(new JLabel("   "), c);

			    c = new GridBagConstraints();
			    c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 15;
				c.gridy = i;
				this.add(points, c);

				// modifier la couleur du fond
				this.setBackground(Color.DARK_GRAY);
				this.setForeground(Color.LIGHT_GRAY);
			}
		}
		this.pack();
		this.show();
	}
	
	@Override 
	public void dispose() {
		for (FicheClassement fc : this.projet.getfc())
			if (fc != null)
				fc.fermer();
	}
	
	public void fermer() {
		super.dispose();
	}
}
