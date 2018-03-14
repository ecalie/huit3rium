package Vue;

import Modele.Projet;

import javax.swing.*;

import Controleur.Reponse.ActionValiderReponse;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Aurélie on 31/10/2017.
 */
public class FicheReponse extends JInternalFrame {

	private Projet projet;

	private ArrayList<JList<String>> lesMemos;
	private ArrayList<JList<String>> lesBalises;

	public FicheReponse(Projet projet) {
		// Créer la fenetre
		super("Enregister les réponses", true, false, false, true);

		this.projet = projet;

		// Calcule le nombre de colonnes
		int nbCol = this.projet.getNbBalise() + this.projet.getNbMemo();

		// Mettre en forme la fenêtre
		this.getContentPane().setLayout(new BorderLayout());

		// la fenêtre
		JPanel contenu = new JPanel(new BorderLayout());
		// l'en-tête
		JPanel contenu1 = new JPanel(new GridLayout(1, nbCol));
		contenu.add(contenu1, BorderLayout.NORTH);
		// les réponses
		JPanel contenu2 = new JPanel(new GridLayout(1, nbCol));
		contenu.add(contenu2, BorderLayout.CENTER);

		this.getContentPane().add(contenu, BorderLayout.CENTER);

		// le bouton valider
		JPanel contenu3 = new JPanel(new FlowLayout());
		this.getContentPane().add(contenu3, BorderLayout.SOUTH);

		// Modifier les couleurs de fonds
		contenu1.setBackground(Color.DARK_GRAY);
		contenu2.setBackground(Color.DARK_GRAY);
		contenu3.setBackground(Color.DARK_GRAY);

		String[] data2 = {"A", "B", "C", "D"};

		// Initialiser les en-têtes
		for (int i = 1 ; i <= this.projet.getNbMemo() ; i++) {
			JLabel memo = new JLabel("Memo " + i);
			memo.setForeground(Color.LIGHT_GRAY);
			contenu1.add(memo);
		}
		for (int i = 1 ; i <= this.projet.getNbBalise() ; i++) {
			JLabel balise = new JLabel("Balise " + i);
			balise.setForeground(Color.LIGHT_GRAY);
			contenu1.add(balise);
		}

		this.lesMemos = new ArrayList<>();
		this.lesBalises = new ArrayList<>();

		// Initialiser les choix de réponses
		for (int i = 1 ; i <= this.projet.getNbMemo() ; i++) {
			JList<String> leMemo = new JList<>(data2);
			leMemo.setPreferredSize(new Dimension(10, 20));
			leMemo.setForeground(Color.LIGHT_GRAY);
			leMemo.setBackground(Color.DARK_GRAY);
			contenu2.add(leMemo);
			this.lesMemos.add(leMemo);
		}	

		for (int i = 1 ; i <= this.projet.getNbBalise() ; i++) {
			JList<String> laBalise = new JList<>(data2);
			laBalise.setPreferredSize(new Dimension(10, 20));
			laBalise.setForeground(Color.LIGHT_GRAY);
			laBalise.setBackground(Color.DARK_GRAY);
			contenu2.add(laBalise);
			this.lesBalises.add(laBalise);
		}

		// Ajouter le bouton validers
		JButton bValider = new JButton("Valider");
		bValider.addActionListener(new ActionValiderReponse(
				this.projet, this.lesMemos, this.lesBalises, this));
		contenu3.add(bValider);

		// Afficher la fenêtre
		this.setSize(new Dimension(
				65 * (this.projet.getNbBalise() + this.projet.getNbMemo()), 200));
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		this.setVisible(false);
	}

	/**
	 * Réinitialiser les sélections avec les réponses enregistrées.
	 */
	public void invaliderModif() {
		for (int i = 0 ; i < this.projet.getNbMemo() ; i++) {
			this.lesMemos.get(i).setSelectedIndex(Projet.alphabet.get(
					this.projet.getReponses().get("memo" + i)));
		}
		for (int i = 0 ; i < this.projet.getNbBalise() ; i++) {
			this.lesBalises.get(i).setSelectedIndex(Projet.alphabet.get(
					this.projet.getReponses().get("balise" + i)));
		}
	}


	/**
	 * Modifier les réponses aux questions.
	 * @param memo     La liste des réponses aux mémos sélectionnées 
	 * @param balise   La liste des réponses aux balises sélectionnées
	 */
	public void validerModif(ArrayList<JList<String>> memo, 
			ArrayList<JList<String>> balise) {

		// vrai tant qu'il n'y a pas de problème
		Boolean continuer = true;
		
		// vérifier que les mémos ont une réponse sélectionnée
		for (int i = 0 ; i < this.projet.getNbMemo() ; i++) {
			if (memo.get(i).getSelectedIndex() == -1) {
				memo.get(i).setBackground(Color.RED);
				continuer = false;
			} else {
				memo.get(i).setBackground(Color.DARK_GRAY);
			}
		}

		// vérifier que les balises ont une réponse enregistrée
		for (int i = 0 ; i < this.projet.getNbBalise() ; i++) {
			if (balise.get(i).getSelectedIndex() == -1) {
				balise.get(i).setBackground(Color.RED);
				continuer = false;
			} else {
				balise.get(i).setBackground(Color.DARK_GRAY);
			}
		}

		// si pas de problème
		if (continuer) {
			// Mettre à jour les réponses aux questions des mémos orientation
			for (int i = 0 ; i < this.projet.getNbMemo() ; i++) {
				this.projet.getReponses().put("memo" + i, memo.get(i).getSelectedValue());
			}

			// Mettre à jour les réponses aux questions des balises
			for (int i = 0 ; i < this.projet.getNbBalise() ; i++) {
				this.projet.getReponses().put("balise" + i, balise.get(i).getSelectedValue());
			}

			// Signaler une modification
			this.projet.setCritEnreg(false);
			this.hide();
		}
	}
}