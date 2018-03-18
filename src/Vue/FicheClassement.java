package Vue;

import java.awt.Color;
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

		int nb = 0;
		for (Jeune j : lesInscrits)
			if (j.getNiveau() == niveau)
				nb++;	

		this.getContentPane().setLayout(new GridLayout(nb, 1));

		int i = 0;
		int k = 0;
		int nbPoints = 0;
		for (Jeune j : lesInscrits) {
			if (j.getNiveau() == niveau) {
				JPanel panel = new JPanel(new GridLayout(1,5));
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
				panel.add(classement);
				panel.add(nom);
				panel.add(prenom);
				panel.add(club);
				panel.add(points);

				// ajouter le panel
				this.add(panel);

				// modifier les couleurs
				if (k == 1)
					panel.setBackground(new Color(255, 215, 0));
				else if (k == 2)
					panel.setBackground(new Color(192, 192, 192));
				else if (k == 3)
					panel.setBackground(new Color(97, 78, 2));
				else {
					// modifier la couleur du fond
					panel.setBackground(Color.DARK_GRAY);
					
					// modifier la couleur du texte
					classement.setForeground(Color.LIGHT_GRAY);
					nom.setForeground(Color.LIGHT_GRAY);
					prenom.setForeground(Color.LIGHT_GRAY);
					club.setForeground(Color.LIGHT_GRAY);
					points.setForeground(Color.LIGHT_GRAY);
				}
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
