package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
		JPanel panel1 = new JPanel(new GridLayout(5,2));
		// panel des boutons
		JPanel panel2 = new JPanel(new FlowLayout());

		// collection des points par épreuve
		HashMap<String, JTextField> scores = new HashMap<>();

		// points pour les balises
		if (projet.getNbBalise() > 0) {
			// balise trouvée
			JLabel baliseTrouvee = new JLabel("Score d'une balise trouvée", JLabel.CENTER);
			baliseTrouvee.setForeground(Color.LIGHT_GRAY);
			this.scoreBalise = new JTextField(1);
			this.scoreBalise.setBackground(Color.LIGHT_GRAY);
			baliseTrouvee.setLabelFor(scoreBalise);
			panel1.add(baliseTrouvee);
			panel1.add(scoreBalise);
			scores.put("baliseTrouvee", scoreBalise);

			// question balise OK
			JLabel questionBalise = new JLabel("Score d'une balise correcte", JLabel.CENTER);
			questionBalise.setForeground(Color.LIGHT_GRAY);
			this.scoreQuestionBalise = new JTextField(1);
			this.scoreQuestionBalise.setBackground(Color.LIGHT_GRAY);
			questionBalise.setLabelFor(scoreQuestionBalise);
			panel1.add(questionBalise);
			panel1.add(scoreQuestionBalise);
			scores.put("baliseCorrecte", scoreQuestionBalise);

			// maniabilité
			JLabel maniabilite = new JLabel("Score d'une maniabilité réussie", JLabel.CENTER);
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
			JLabel memoTrouve = new JLabel("Score d'un mémo trouvé", JLabel.CENTER);
			memoTrouve.setForeground(Color.LIGHT_GRAY);
			this.scoreMemo = new JTextField(1);
			this.scoreMemo.setBackground(Color.LIGHT_GRAY);
			memoTrouve.setLabelFor(scoreMemo);
			panel1.add(memoTrouve);
			panel1.add(scoreMemo);
			scores.put("memoTrouve", scoreMemo);

			// question mémo OK
			JLabel questionMemo = new JLabel("Score d'un mémo correct", JLabel.CENTER);
			questionMemo.setForeground(Color.LIGHT_GRAY);
			this.scoreQuestionMemo = new JTextField(1);
			this.scoreQuestionMemo.setBackground(Color.LIGHT_GRAY);
			questionMemo.setLabelFor(scoreQuestionMemo);
			panel1.add(questionMemo);
			panel1.add(scoreQuestionMemo);
			scores.put("memoCorrect", scoreQuestionMemo);
		}

		// ajouter les boutons
		JButton valider = new JButton("Valider");
		valider.setBackground(Color.LIGHT_GRAY);
		valider.addActionListener(new ActionValiderGains(projet, scores, this));
		JButton annuler = new JButton("Annuler");
		annuler.setBackground(Color.LIGHT_GRAY);
		annuler.addActionListener(new ActionAnnulerGains(projet, this));
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
	 * @param jif    La fiche des gains
	 */
	public void validerGains(HashMap<String, JTextField> gains) {
		// vrai s'il y a une erreur dans le formulaire
		Boolean stop = false;
		// On vérifie que le formulaire est bien rempli
		for (String score : gains.keySet()) {
			// Si une case est vide
			if (gains.get(score).getText().equals("")) {
				gains.get(score).setBackground(Color.RED);
				stop = true;
			} else {
				// on vérifie que c'est un entier
				try {
					Integer.parseInt(gains.get(score).getText());
					gains.get(score).setBackground(Color.LIGHT_GRAY);
				} catch(NumberFormatException e) {
					gains.get(score).setBackground(Color.RED);
					stop = true;

					try {
						Projet.fwLog = new FileWriter(new File("log.txt"), true);
						Projet.fwLog.write(e + "");
						Projet.fwLog.close();
					} catch (IOException e1) {}
					
				}
			}
		}
		
		// Si pas de problème, en enregistre
		if (!stop) {
			for (String score : gains.keySet()) {
				int g = Integer.parseInt(gains.get(score).getText());
				this.projet.getGains().put(score, g);
			}
			this.hide();

			// Signaler une modification
			this.projet.setCritEnreg(false);
		}

	}


	/**
	 * Annuler les modifications sur les points.
	 * @param jif  La fiche des gains
	 */
	public void annulerGains() {
		this.getScoreBalise().setText("" + this.projet.getGains().get("baliseTrouvee"));
		this.getScoreQuestionBalise().setToolTipText("" + this.projet.getGains().get("baliseCorrecte"));
		this.getScoreManiabilite().setText("" + this.projet.getGains().get("maniabilite"));
		this.getScoreMemo().setText("" + this.projet.getGains().get("memoTrouve"));
		this.getScoreQuestionMemo().setText("" + this.projet.getGains().get("memoCorrect"));
		this.hide();
	}

	public void invaliderModif() {
		this.scoreBalise.setText("" + this.projet.getGains().get("baliseTrouvee"));
		this.scoreQuestionBalise.setText("" + this.projet.getGains().get("baliseCorrecte"));
		this.scoreManiabilite.setText("" + this.projet.getGains().get("maniabilite"));
		this.scoreMemo.setText("" + this.projet.getGains().get("memoTrouve"));
		this.scoreQuestionMemo.setText("" + this.projet.getGains().get("memoCorrect"));
	}

}
