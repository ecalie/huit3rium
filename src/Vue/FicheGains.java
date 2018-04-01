package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controleur.Gains.ActionAnnulerGains;
import Controleur.Gains.ActionValiderGains;
import Modele.Projet;

public class FicheGains extends JInternalFrame {

	private Projet projet;
	private JTextField scoreBalise;
	private JTextField scoreQuestionBalise;
	private JTextField scoreMemo;
	private JTextField scoreQuestionMemo;
	private JTextField scoreManiabilite;
	private JTextField scoreMecanique;
	private JTextField scoreChaine;
	private JTextField scoreCables;
	private JTextField scoreClefs;
	private JTextField scoreReparation;
	private JTextField scoreOrientation;
	
	private HashMap<String, JTextField> scores;

	/**
	 * Construire une fiche gains.
	 * @param projet  Le projet
	 */
	public FicheGains(Projet projet) {
		super("Définir les points gagnés pour chaque épreuve",  true, false, false, true);

		this.projet = projet;

		// modifier la mise en page de la fenetre
		this.getContentPane().setLayout(new BorderLayout());

		// ajouter des panels
		// panel des points
		JPanel panel1 = new JPanel(new GridLayout(11,2));
		// panel des boutons
		JPanel panel2 = new JPanel(new FlowLayout());

		// collection des points par épreuve
		scores = new HashMap<>();

		// points pour les balises
		if (projet.getNbBalise() > 0) {
			// balise trouvée
			JLabel baliseTrouvee = new JLabel("Balise", JLabel.CENTER);
			baliseTrouvee.setForeground(Color.LIGHT_GRAY);
			this.scoreBalise = new JTextField(1);
			this.scoreBalise.setBackground(Color.LIGHT_GRAY);
			baliseTrouvee.setLabelFor(scoreBalise);
			panel1.add(baliseTrouvee);
			panel1.add(scoreBalise);
			scores.put("baliseTrouvee", scoreBalise);

			// question balise OK
			JLabel questionBalise = new JLabel("Question balise", JLabel.CENTER);
			questionBalise.setForeground(Color.LIGHT_GRAY);
			this.scoreQuestionBalise = new JTextField(1);
			this.scoreQuestionBalise.setBackground(Color.LIGHT_GRAY);
			questionBalise.setLabelFor(scoreQuestionBalise);
			panel1.add(questionBalise);
			panel1.add(scoreQuestionBalise);
			scores.put("baliseCorrecte", scoreQuestionBalise);

			// maniabilité
			JLabel maniabilite = new JLabel("Maniabilité", JLabel.CENTER);
			maniabilite.setForeground(Color.LIGHT_GRAY);
			this.scoreManiabilite = new JTextField(1);
			this.scoreManiabilite.setBackground(Color.LIGHT_GRAY);
			maniabilite.setLabelFor(scoreManiabilite);
			panel1.add(maniabilite);
			panel1.add(scoreManiabilite);
			scores.put("maniabilite", scoreManiabilite);
		}

		// points mémo
		if (projet.getNbMemo() > 0) {
			// mémo trouvé
			JLabel memoTrouve = new JLabel("Mémo", JLabel.CENTER);
			memoTrouve.setForeground(Color.LIGHT_GRAY);
			this.scoreMemo = new JTextField(1);
			this.scoreMemo.setBackground(Color.LIGHT_GRAY);
			memoTrouve.setLabelFor(scoreMemo);
			panel1.add(memoTrouve);
			panel1.add(scoreMemo);
			scores.put("memoTrouve", scoreMemo);

			// question mémo OK
			JLabel questionMemo = new JLabel("Question mémo", JLabel.CENTER);
			questionMemo.setForeground(Color.LIGHT_GRAY);
			this.scoreQuestionMemo = new JTextField(1);
			this.scoreQuestionMemo.setBackground(Color.LIGHT_GRAY);
			questionMemo.setLabelFor(scoreQuestionMemo);
			panel1.add(questionMemo);
			panel1.add(scoreQuestionMemo);
			scores.put("memoCorrect", scoreQuestionMemo);
		}

		// points orientation
		if (projet.getNbOrientation() > 0) {
			JLabel orientation = new JLabel("Orientation", JLabel.CENTER);
			orientation.setForeground(Color.LIGHT_GRAY);
			this.scoreOrientation = new JTextField(1);
			this.scoreOrientation.setBackground(Color.LIGHT_GRAY);
			orientation.setLabelFor(scoreOrientation);
			panel1.add(orientation);
			panel1.add(scoreOrientation);
			scores.put("orientation", scoreOrientation);
		}
				
		// question mécanique
		JLabel meca = new JLabel("Mecanique", JLabel.CENTER);
		meca.setForeground(Color.LIGHT_GRAY);
		this.scoreMecanique = new JTextField(1);
		this.scoreMecanique.setBackground(Color.LIGHT_GRAY);
		meca.setLabelFor(scoreMecanique);
		panel1.add(meca);
		panel1.add(scoreMecanique);
		scores.put("mecanique", scoreMecanique);
		
		// trouse mecanique
		JLabel chaine = new JLabel("Dérive chaîne", JLabel.CENTER);
		chaine.setForeground(Color.LIGHT_GRAY);
		this.scoreChaine = new JTextField(1);
		this.scoreChaine.setBackground(Color.LIGHT_GRAY);
		chaine.setLabelFor(scoreChaine);
		panel1.add(chaine);
		panel1.add(scoreChaine);
		scores.put("chaine", scoreChaine);

		JLabel clefs = new JLabel("Jeu de clés", JLabel.CENTER);
		clefs.setForeground(Color.LIGHT_GRAY);
		this.scoreClefs = new JTextField(1);
		this.scoreClefs.setBackground(Color.LIGHT_GRAY);
		clefs.setLabelFor(scoreClefs);
		panel1.add(clefs);
		panel1.add(scoreClefs);
		scores.put("clefs", scoreClefs);

		JLabel cables = new JLabel("Câbles", JLabel.CENTER);
		cables.setForeground(Color.LIGHT_GRAY);
		this.scoreCables = new JTextField(1);
		this.scoreCables.setBackground(Color.LIGHT_GRAY);
		cables.setLabelFor(scoreCables);
		panel1.add(cables);
		panel1.add(scoreCables);
		scores.put("cables", scoreCables);

		JLabel reparation = new JLabel("Nécéssaire de réparation", JLabel.CENTER);
		reparation.setForeground(Color.LIGHT_GRAY);
		this.scoreReparation = new JTextField(1);
		this.scoreReparation.setBackground(Color.LIGHT_GRAY);
		reparation.setLabelFor(scoreReparation);
		panel1.add(reparation);
		panel1.add(scoreReparation);
		scores.put("reparation", scoreReparation);
				
		// ajouter les boutons
		JButton valider = new JButton("Valider");
		valider.setBackground(Color.LIGHT_GRAY);
		valider.addActionListener(new ActionValiderGains(this));
		JButton annuler = new JButton("Annuler");
		annuler.setBackground(Color.LIGHT_GRAY);
		annuler.addActionListener(new ActionAnnulerGains(this));
		panel2.add(valider);
		panel2.add(annuler);

		// ajouter les panels
		this.getContentPane().add(panel1, BorderLayout.CENTER);
		this.getContentPane().add(panel2, BorderLayout.SOUTH);

		// modifier la couleur de fond
		panel1.setBackground(Color.DARK_GRAY);
		panel1.setForeground(Color.LIGHT_GRAY);
		panel2.setBackground(Color.DARK_GRAY);

		// Afficher la fenêtre
		this.pack();
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		this.setVisible(false);
	}

	////////////////////////
	///GETTERS ET SETTERS///
	////////////////////////

	public JTextField getScoreBalise() {
		return scoreBalise;
	}

	public void setScoreBalise(JTextField scoreBalise) {
		this.scoreBalise = scoreBalise;
	}

	public JTextField getScoreManiabilite() {
		return scoreManiabilite;
	}

	public void setScoreManiabilite(JTextField scoreManiabilite) {
		this.scoreManiabilite = scoreManiabilite;
	}

	public JTextField getScoreMemo() {
		return scoreMemo;
	}

	public void setScoreMemo(JTextField scoreMemo) {
		this.scoreMemo = scoreMemo;
	}

	public JTextField getScoreQuestionMemo() {
		return scoreQuestionMemo;
	}

	public void setScoreQuestionMemo(JTextField scoreQuestionMemo) {
		this.scoreQuestionMemo = scoreQuestionMemo;
	}

	public JTextField getScoreQuestionBalise() {
		return scoreQuestionBalise;
	}

	public void setScoreQuestionBalise(JTextField scoreQuestionBalise) {
		this.scoreQuestionBalise = scoreQuestionBalise;
	}

	/////////////////////
	///AUTRES METHODES///
	/////////////////////

	/**
	 * Valider les points à gagner.
	 * @param gains  L'ensemble des points à gagner
	 */
	public void validerGains() {
		// vrai s'il y a une erreur dans le formulaire
		Boolean stop = false;
		// On vérifie que le formulaire est bien rempli
		for (String score : scores.keySet()) {
			// Si une case est vide
			if (scores.get(score).getText().equals("")) {
				scores.get(score).setBackground(Color.RED);
				stop = true;
			} else {
				// on vérifie que c'est un entier
				try {
					Integer.parseInt(scores.get(score).getText());
					scores.get(score).setBackground(Color.LIGHT_GRAY);
				} catch(NumberFormatException e) {
					scores.get(score).setBackground(Color.RED);
					stop = true;
				}
			}
		}
		
		// Si pas de problème, en enregistre
		if (!stop) {
			for (String score : scores.keySet()) {
				int g = Integer.parseInt(scores.get(score).getText());
				this.projet.getGains().put(score, g);
			}
			this.hide();

			// Signaler une modification
			this.projet.setCritEnreg(false);
		}

	}

	/**
	 * Annuler les modifications sur les points.
	 * @param gains L'ensemble des points à gagner par épreuve
	 */
	public void invaliderModif() {
		for (String score : scores.keySet())
			scores.get(score).setText("" + this.projet.getGains().get(score));

		this.hide();
	}
}
