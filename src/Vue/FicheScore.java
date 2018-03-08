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

import Controleur.Score.ActionValiderScore;
import Modele.Jeune;
import Modele.Projet;

public class FicheScore extends JInternalFrame {

	private ArrayList<JList<String>> lesMemos;
	private ArrayList<JList<String>> lesBalises;
	private ArrayList<JList<String>> lesZones;

	private Jeune licencie;
	private FenetrePrincipale fp;

	public FicheScore(FenetrePrincipale fp) {
		super("", true, false, false, true);
		this.fp = fp;
		this.lesMemos = new ArrayList<>();
		this.lesBalises = new ArrayList<>();
		this.lesZones = new ArrayList<>();

		this.getContentPane().setLayout(new BorderLayout());

		int nbCol = this.fp.getProjet().getNbBalise() + this.fp.getProjet().getNbMemo();
		JPanel contenu1 = new JPanel(new GridLayout(1, nbCol));
		this.getContentPane().add(contenu1, BorderLayout.NORTH);

		JPanel contenu2 = new JPanel(new GridLayout(1, nbCol));
		this.getContentPane().add(contenu2, BorderLayout.CENTER);

		JPanel contenu3 = new JPanel(new FlowLayout());
		this.getContentPane().add(contenu3, BorderLayout.SOUTH);

		String[] data1 = {"        O", "        N", "        XX"};
		String[] data2 = {"        A", "        B", "        C", "        D", "        XX"};

		for (int i = 1; i <= this.fp.getProjet().getNbMemo(); i++) {
			JLabel btnMemo = new JLabel("Mémo " + i);
			contenu1.add(btnMemo);

			// Ajouter les réponses possibles
			JList<String> memo = new JList<>(data2);
			memo.setForeground(Color.BLUE);
			memo.setBackground(Color.LIGHT_GRAY);
			memo.setSelectedIndex(4);
			this.lesMemos.add(memo);

			// modifier l'action du bouton
			btnMemo.setBackground(Color.LIGHT_GRAY);
			
			contenu2.add(memo);
		}

		for (int i = 1; i <= this.fp.getProjet().getNbBalise(); i++) {
			JLabel btnBalise = new JLabel("Balise " + i);
			contenu1.add(btnBalise);

			JList<String> balise = new JList<>(data2);
			balise.setForeground(Color.BLUE);
			balise.setBackground(Color.LIGHT_GRAY);
			balise.setSelectedIndex(4);
			this.lesBalises.add(balise);

			// modifier l'action du bouton
			btnBalise.setBackground(Color.LIGHT_GRAY);
			
			contenu2.add(balise);
		}

		for (int i = 1; i <= this.fp.getProjet().getNbBalise(); i++) {
			JLabel btnZone = new JLabel("Zone " + i);
			contenu1.add(btnZone);

			JList<String> zone = new JList<>(data1);
			zone.setPreferredSize(new Dimension(10, 20));
			zone.setForeground(Color.BLUE);
			zone.setBackground(Color.LIGHT_GRAY);
			zone.setSelectedIndex(2);
			this.lesZones.add(zone);

			// modifier l'action du bouton
			btnZone.setBackground(Color.LIGHT_GRAY);

			contenu2.add(zone);
		}

		JButton bValider = new JButton("Valider");
		bValider.addActionListener(new ActionValiderScore(this));
		contenu3.add(bValider);

		this.fp.getDesktop().add(this);
		//this.setSize(new Dimension(50 * (this.lesMemos.size() + this.lesBalises.size() * 2), 300));
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		this.pack();
		this.hide();
	}

	/**
	 * Mettre à jour les données de la fiche en fonction de celles du jeune
	 */
	public void invaliderModif() {
		for (int i = 0; i < this.lesMemos.size(); i++) {
			invaliderReponse(this.licencie.getLesReponses().get("memo" + i), 
					this.lesMemos.get(i));
		}

		for (int i = 0 ; i < this.lesBalises.size(); i++) {
			invaliderReponse(this.licencie.getLesReponses().get("balise" + i),
					this.lesBalises.get(i));
		}

		for (int i = 0 ; i < this.lesZones.size(); i++) {
			invaliderZone(this.licencie.getLesReponses().get("maniabilite" + i),
					this.lesZones.get(i));
		}
	}

	public void invaliderReponse(String reponse, JList<String> list) {
		if (!reponse.equals("XX")) {
			list.setSelectedIndex(Projet.alphabet.get(reponse));
		}
	}

	public void invaliderZone(String resultat, JList<String> list) {
		if (resultat.equals("O")) {
			list.setSelectedIndex(0);
		} else if (resultat.equals("N")) {
			list.setSelectedIndex(1);
		}
	}

	/**
	 * Mettre à jour les données du jeune en fonction de celles de la fiche
	 */
	public void validerModif() {
		// vérifier que la fiche est bien rempli
		if (this.verif()) {
			HashMap<String, String> reponses = new HashMap<>();
			for (int i = 0; i < this.lesMemos.size(); i++) {
				reponses.put("memo" + i, this.lesMemos.get(i).getSelectedValue());
			}

			for (int i = 0; i < this.lesBalises.size(); i++) {
				reponses.put("balise" + i, this.lesBalises.get(i).getSelectedValue());
				reponses.put("maniabilite" + i, this.lesZones.get(i).getSelectedValue());
			}

			Boolean finiMemo = true;
			// Mettre à jour le nombre des jeunes sur chaque circuit
			for (int i = 0; i < this.lesMemos.size(); i++) {
				if (this.lesMemos.get(i).getSelectedIndex() == 4) {
					finiMemo = false;
					break;
				}
			}

			if (finiMemo) {
				this.fp.getProjet().arriveMemo(this.licencie);
			}

			Boolean baliseFinie = true;
			for (int i = 0; i < this.lesBalises.size(); i++) {
				if (this.lesBalises.get(i).getSelectedIndex() == 4) {
					baliseFinie = false;
					break;
				}
			}

			if (baliseFinie) {
				this.fp.getProjet().arriveBalise(this.licencie);
			}

			this.licencie.setLesReponses(reponses);
			this.fp.getProjet().setCritEnreg(false);

			this.hide();
			this.fp.repaint();
		}
	}

	/**
	 * Modifier le licencié associé à la fiche.
	 * @param licencie Le nouveau licencié associé à la fiche
	 */
	public void setLicencie(Jeune licencie) {
		this.licencie = licencie;
	}

	/**
	 * Vérifier que la fiche est bien remplie, sinon colorer les colonnes 
	 * qui devraient être remplies.
	 * @return Vrai si la fichie est bien replie, Faux sinon
	 */
	public Boolean verif() {
		Boolean ok = true;
		// regarger si un mémo est rempli
		Boolean memo = false;
		for (int i = 0 ; i < this.fp.getProjet().getNbMemo() ; i++) {
			if (lesMemos.get(i).getSelectedIndex() != 4) {
				memo = true;
				break;
			}
		}

		// Si au moins un mémo est rempli, les autres doivent l'être
		if (memo) {
			// vérifier que les mémos ont une réponse sélectionnée
			for (int i = 0 ; i < this.fp.getProjet().getNbMemo() ; i++) {
				if (lesMemos.get(i).getSelectedIndex() == 4) {
					lesMemos.get(i).setBackground(Color.RED);
					ok = false;
				} else {
					lesMemos.get(i).setBackground(Color.LIGHT_GRAY);
				}
			}
		}

		// regarder si une balise ou une maniabilité est remplie
		Boolean balise = false;
		for (int i = 0 ; i < this.fp.getProjet().getNbBalise() ; i++) {
			if (lesBalises.get(i).getSelectedIndex() != 4 || lesZones.get(i).getSelectedIndex() != 2) {
				balise = true;
				break;
			}
		}

		// Si au moins une zone ou une balise est remplie, les autres doivent l'être aussi
		if (balise) {
			// vérifier que les balises ont une réponse enregistrée
			for (int i = 0 ; i < this.fp.getProjet().getNbBalise() ; i++) {
				if (lesBalises.get(i).getSelectedIndex() == 4) {
					lesBalises.get(i).setBackground(Color.RED);
					ok = false;
				} else {
					lesBalises.get(i).setBackground(Color.LIGHT_GRAY);
				}
			}

			// vérifier que les maniabilité sont renseignées
			for (int i = 0 ; i < this.fp.getProjet().getNbBalise() ; i++) {
				if (lesZones.get(i).getSelectedIndex() == 2) {
					lesZones.get(i).setBackground(Color.RED);
					ok = false;
				} else {
					lesZones.get(i).setBackground(Color.LIGHT_GRAY);
				}
			}
		}

		return ok;
	}
}
