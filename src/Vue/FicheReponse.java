package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controleur.Reponse.ActionAnnulerReponse;
import Controleur.Reponse.ActionValiderReponse;
import Modele.Projet;

/**
 * Created by Aurélie on 31/10/2017.
 */
public class FicheReponse extends JInternalFrame {

	private Projet projet;

	private ArrayList<JList<String>> lesMemos;
	private ArrayList<JList<String>> lesBalises;
	// TODO
	private HashMap<String,JTextArea> lesOrientations;

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
		for (int i = 1 ; i <= this.projet.getNbCircuit() ; i++) {
			JLabel orientation = new JLabel("   Orientation " + i + "   ");
			orientation.setForeground(Color.LIGHT_GRAY);
			contenu1.add(orientation);
		}

		this.lesMemos = new ArrayList<>();
		this.lesBalises = new ArrayList<>();
		this.lesOrientations = new HashMap<>();

		// Mémo
		for (int i = 1 ; i <= this.projet.getNbMemo() ; i++) {
			JList<String> leMemo = new JList<>(data2);
			leMemo.setPreferredSize(new Dimension(10, 20));
			leMemo.setForeground(Color.LIGHT_GRAY);
			leMemo.setBackground(Color.DARK_GRAY);
			contenu2.add(leMemo);
			this.lesMemos.add(leMemo);
		}	

		// balise
		for (int i = 1 ; i <= this.projet.getNbBalise() ; i++) {
			JList<String> laBalise = new JList<>(data2);
			laBalise.setPreferredSize(new Dimension(10, 20));
			laBalise.setForeground(Color.LIGHT_GRAY);
			laBalise.setBackground(Color.DARK_GRAY);
			contenu2.add(laBalise);
			this.lesBalises.add(laBalise);
		}

		// orientation
		for (int i = 0 ; i < this.projet.getNbCircuit() ; i++) {
			JPanel panel = new JPanel(new GridLayout(this.projet.getNbOrientation(), 2));
			panel.setBackground(Color.DARK_GRAY);
			for (int j = 0 ; j < this.projet.getNbOrientation() ; j++) {
				JTextArea area = new JTextArea();
				JLabel label = new JLabel("     " + j);
				label.setLabelFor(area);
				label.setBackground(Color.DARK_GRAY);
				label.setForeground(Color.LIGHT_GRAY);
				panel.add(label);
				panel.add(area);
				this.lesOrientations.put("orientation" + i + "_" + j, area);
			}
			contenu2.add(panel);
		}
			
		// Ajouter le bouton valider
		JButton bValider = new JButton("Valider");
		bValider.addActionListener(new ActionValiderReponse(this));
		contenu3.add(bValider);

		// Ajouter le bouton annuler
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionAnnulerReponse(this));
		contenu3.add(btnAnnuler);

		// Afficher la fenêtre
		this.pack();
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		this.setVisible(false);
	}

	/**
	 * Réinitialiser les sélections avec les réponses enregistrées.
	 */
	public void invaliderModif() {
		try{
			for (int i = 0 ; i < this.projet.getNbMemo() ; i++) {
				
				this.lesMemos.get(i).setSelectedIndex(Projet.alphabet.get(
						this.projet.getReponses().get("memo" + i)));
			}
			for (int i = 0 ; i < this.projet.getNbBalise() ; i++) {
				this.lesBalises.get(i).setSelectedIndex(Projet.alphabet.get(
						this.projet.getReponses().get("balise" + i)));
			}
			for (String key : this.lesOrientations.keySet())
				this.lesOrientations.get(key).setText(this.projet.getReponses().get(key));
		} catch (NullPointerException e) {
			this.hide();
		}
		this.hide();
	}


	/**
	 * Modifier les réponses aux questions.
	 * @param memo     La liste des réponses aux mémos sélectionnées 
	 * @param balise   La liste des réponses aux balises sélectionnées
	 */
	public void validerModif() {

		// vrai tant qu'il n'y a pas de problème
		Boolean continuer = true;
		
		// vérifier que les mémos ont une réponse sélectionnée
		for (int i = 0 ; i < this.projet.getNbMemo() ; i++) {
			if (lesMemos.get(i).getSelectedIndex() == -1) {
				lesMemos.get(i).setBackground(Color.RED);
				continuer = false;
			} else {
				lesMemos.get(i).setBackground(Color.DARK_GRAY);
			}
		}

		// vérifier que les balises ont une réponse enregistrée
		for (int i = 0 ; i < this.projet.getNbBalise() ; i++) {
			if (lesBalises.get(i).getSelectedIndex() == -1) {
				lesBalises.get(i).setBackground(Color.RED);
				continuer = false;
			} else {
				lesBalises.get(i).setBackground(Color.DARK_GRAY);
			}
		}

		// Vérifier que toutes les balises d'orientation sur tous les circuits ont une réponse
		for (String key : this.lesOrientations.keySet()) {
			if (this.lesOrientations.get(key).getText().equals("")) {
				this.lesOrientations.get(key).setBackground(Color.RED);
				continuer = false;
			}
			else
				this.lesOrientations.get(key).setBackground(Color.LIGHT_GRAY);
		}

		// si pas de problème
		if (continuer) {
			// Mettre à jour les réponses aux questions des mémos orientation
			for (int i = 0 ; i < this.projet.getNbMemo() ; i++) {
				this.projet.getReponses().put("memo" + i, lesMemos.get(i).getSelectedValue());
			}

			// Mettre à jour les réponses aux questions des balises
			for (int i = 0 ; i < this.projet.getNbBalise() ; i++) {
				this.projet.getReponses().put("balise" + i, lesBalises.get(i).getSelectedValue());
			}

			// Mettre à jour les réponses aux balies d'orientations
			for (String key : this.lesOrientations.keySet()) {
				this.projet.getReponses().put(key, this.lesOrientations.get(key).getText());
			}

			// Signaler une modification
			this.projet.setCritEnreg(false);
			this.hide();
		}
	}
}