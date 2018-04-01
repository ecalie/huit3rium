package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controleur.Score.ActionDeselectionner;
import Controleur.Score.ActionValiderScore;
import Modele.Jeune;
import Modele.Projet;

public class FicheScore extends JInternalFrame {

	private ArrayList<JList<String>> lesMemos;
	private ArrayList<JList<String>> lesBalises;
	private ArrayList<JList<String>> lesZones;
	private ArrayList<JTextArea> lesOrientation;
	private JCheckBox chaine;
	private JCheckBox cables;
	private JCheckBox clefs;
	private JCheckBox reparation;

	private Jeune licencie;
	private Projet projet;

	/**
	 * Construire une fiche score associée à un inscrit.
	 * @param projet   Le projet
	 */
	public FicheScore(Projet projet) {
		super("", false, false, false, true);
		this.projet = projet;
		this.lesMemos = new ArrayList<>();
		this.lesBalises = new ArrayList<>();
		this.lesOrientation = new ArrayList<>();
		this.lesZones = new ArrayList<>();

		this.getContentPane().setLayout(new BorderLayout());

		JPanel contenu1 = new JPanel(new GridBagLayout());
		this.getContentPane().add(contenu1, BorderLayout.CENTER);

		JPanel contenu3 = new JPanel(new FlowLayout());
		this.getContentPane().add(contenu3, BorderLayout.SOUTH);

		String[] data1 = {"O", "N", "XX"};
		String[] data2 = {"A", "B", "C", "D", "XX"};

		for (int i = 1; i <= this.projet.getNbMemo(); i++) {
			JButton btnMemo = new JButton("Mémo " + i);
		    GridBagConstraints c = new GridBagConstraints();
		    c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = i - 1;
			c.gridy = 0;
			c.gridheight = 2;
			contenu1.add(btnMemo, c);

			// Ajouter les réponses possibles
			JList<String> memo = new JList<>(data2);
			memo.setForeground(Color.BLUE);
			memo.setBackground(Color.LIGHT_GRAY);
			memo.setSelectedIndex(-1);
			this.lesMemos.add(memo);

			// modifier l'action du bouton
			btnMemo.addActionListener(new ActionDeselectionner(memo, this.projet));
			btnMemo.setFocusPainted(false);
			btnMemo.setBorderPainted(false);
			btnMemo.setContentAreaFilled(false);
			btnMemo.setBackground(Color.LIGHT_GRAY);

		    c = new GridBagConstraints();
		    c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = i - 1;
			c.gridy = 2;
			c.ipadx = 10;
			contenu1.add(memo, c);
		}

		for (int i = 1; i <= this.projet.getNbBalise(); i++) {
		    GridBagConstraints c = new GridBagConstraints();
			JButton btnBalise = new JButton("Balise " + i);
		    c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = i - 1 + this.projet.getNbMemo();
			c.gridy = 0;
			c.gridheight = 2;
			contenu1.add(btnBalise, c);

			JList<String> balise = new JList<>(data2);
			balise.setForeground(Color.BLUE);
			balise.setBackground(Color.LIGHT_GRAY);
			balise.setSelectedIndex(-1);
			this.lesBalises.add(balise);

			// modifier l'action du bouton
			btnBalise.addActionListener(new ActionDeselectionner(balise, this.projet));
			btnBalise.setFocusPainted(false);
			btnBalise.setBorderPainted(false);
			btnBalise.setContentAreaFilled(false);
			btnBalise.setBackground(Color.LIGHT_GRAY);

		    c = new GridBagConstraints();
		    c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = i - 1 + this.projet.getNbMemo();
			c.gridy = 2;
			c.ipadx = 10;
			contenu1.add(balise, c);
		}

		for (int i = 1; i <= this.projet.getNbBalise(); i++) {
		    GridBagConstraints c = new GridBagConstraints();
			JLabel balise = new JLabel("            Balise " + i);
		    c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 2*(i-1) + this.projet.getNbMemo() + this.projet.getNbBalise();
			c.gridy = 0;
			c.gridwidth = 2;
			contenu1.add(balise, c);
			
			JButton btnZone1 = new JButton("Seg.1");
		    c = new GridBagConstraints();
		    c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 2*(i-1) + this.projet.getNbMemo() + this.projet.getNbBalise();
			c.gridy = 1;
			contenu1.add(btnZone1, c);
			
			JButton btnZone2 = new JButton("Seg.2");
		    c = new GridBagConstraints();
		    c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 2*(i-1) + 1 + this.projet.getNbMemo() + this.projet.getNbBalise();
			c.gridy = 1;
			contenu1.add(btnZone2, c);

			// TODO centrer
			JList<String> zone1 = new JList<>(data1);
			zone1.setForeground(Color.BLUE);
			zone1.setBackground(Color.LIGHT_GRAY);
			zone1.setSelectedIndex(-1);
			this.lesZones.add(zone1);

			JList<String> zone2 = new JList<>(data1);
			zone2.setForeground(Color.BLUE);
			zone2.setBackground(Color.LIGHT_GRAY);
			zone2.setSelectedIndex(-1);
			this.lesZones.add(zone2);
			
			// modifier l'action du bouton
			btnZone1.addActionListener(new ActionDeselectionner(zone1, this.projet));
			btnZone1.setFocusPainted(false);
			btnZone1.setBorderPainted(false);
			btnZone1.setContentAreaFilled(false);
			btnZone1.setBackground(Color.LIGHT_GRAY);

			btnZone2.addActionListener(new ActionDeselectionner(zone2, this.projet));
			btnZone2.setFocusPainted(false);
			btnZone2.setBorderPainted(false);
			btnZone2.setContentAreaFilled(false);
			btnZone2.setBackground(Color.LIGHT_GRAY);
			
		    c = new GridBagConstraints();
		    c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 2*(i-1) + this.projet.getNbMemo() + this.projet.getNbBalise();
			c.gridy = 2;
			c.ipadx = 10;  
			contenu1.add(zone1, c);
			
		    c = new GridBagConstraints();
		    c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 2*(i-1) + 1 + this.projet.getNbMemo() + this.projet.getNbBalise();
			c.gridy = 2;
			c.ipadx = 10;  
			contenu1.add(zone2, c);
		}

	    GridBagConstraints c = new GridBagConstraints();
	    JLabel orien = new JLabel("Orientation");
	    c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = this.projet.getNbMemo() + this.projet.getNbBalise()*(1+this.projet.getNbSegment());
		c.gridy = 0;
		c.gridheight = 2;
		contenu1.add(orien, c);
		
		JPanel panel = new JPanel(new GridLayout(this.projet.getNbOrientation(), 2));
		for (int i = 1; i <= this.projet.getNbOrientation(); i++) {
			JLabel label = new JLabel("    " + i);
			panel.add(label);
			JTextArea area = new JTextArea();
			panel.add(area);
			label.setLabelFor(area);
			this.lesOrientation.add(area);
		}
		
	    c = new GridBagConstraints();
	    c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = this.projet.getNbMemo() + this.projet.getNbBalise()*(1+this.projet.getNbSegment());
		c.gridy = 2;
		c.ipadx = 10;
		contenu1.add(panel, c);

		JLabel trousse = new JLabel("         Trousse");
		 c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = this.projet.getNbMemo() + (1+this.projet.getNbSegment())*this.projet.getNbBalise() + 1;
		c.gridy = 0;
		contenu1.add(trousse, c);

		JPanel contenu2 = new JPanel(new GridLayout(4,1));
		JLabel trousse2 = new JLabel("       mécanique");
		c.gridy++;
		contenu1.add(trousse2, c);
		
		chaine = new JCheckBox("Dérive chaîne");
		contenu2.add(chaine);
		
		cables = new JCheckBox("Câbles");
		contenu2.add(cables);
		
		clefs = new JCheckBox("Jeu de clés");
		contenu2.add(clefs);
		
		reparation = new JCheckBox("Réparation");
		contenu2.add(reparation);

		c.gridy++;
		contenu1.add(contenu2, c);
		
		JButton bValider = new JButton("Valider");
		bValider.addActionListener(new ActionValiderScore(this));
		contenu3.add(bValider);

		this.projet.getFp().getDesktop().add(this);
		//this.setSize(new Dimension(50 * (this.lesMemos.size() + this.lesBalises.size() * 2), 300));
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		this.pack();
		this.hide();
	}

	/**
	 * Mettre à jour les données de la fiche en fonction de celles du jeune
	 */
	public void invaliderModif() {
		for (int i = 0; i < this.lesMemos.size(); i++)
			invaliderReponse(this.licencie.getLesReponses().get("memo" + i), this.lesMemos.get(i));

		for (int i = 0 ; i < this.lesBalises.size(); i++)
			invaliderReponse(this.licencie.getLesReponses().get("balise" + i),this.lesBalises.get(i));

		int ind = 0;
		for (int i = 0 ; i < this.lesBalises.size(); i++)
			for (int ii = 0 ; ii < this.projet.getNbSegment() ; ii++) { 				
				invaliderZone(this.licencie.getLesReponses().get("maniabilite" + i + "_" + ii),this.lesZones.get(ind));
				ind++;
			}
		
		for (int i = 0; i < this.lesOrientation.size(); i++)
			this.lesOrientation.get(i).setText(this.licencie.getLesReponses().get("orientation" + i));
	
		// La trousse de réparation
		if(this.licencie.getLesReponses().get("clefs").equals("O"))
			this.clefs.setSelected(true);
		else
			this.clefs.setSelected(false);
		
		if(this.licencie.getLesReponses().get("cables").equals("O"))
			this.cables.setSelected(true);
		else
			this.cables.setSelected(false);
		
		if(this.licencie.getLesReponses().get("reparation").equals("O"))
			this.reparation.setSelected(true);
		else
			this.reparation.setSelected(false);
		
		if(this.licencie.getLesReponses().get("chaine").equals("O"))
			this.chaine.setSelected(true);
		else
			this.chaine.setSelected(false);
	}

	/**
	 * Mettre à jour une réponse à une balise.
	 * @param reponse   La réponse réelle
	 * @param list      La zone de la réponse sur la fiche
	 */
	public void invaliderReponse(String reponse, JList<String> list) {
		if (reponse.equals("XX"))
			list.setSelectedIndex(list.getMaxSelectionIndex());
		else if (reponse.equals(""))
			list.setSelectedIndex(-1);
		else
			list.setSelectedIndex(Projet.alphabet.get(reponse));
	}

	/**
	 * Mettre à jour une zone de maniabilité sur la fiche.
	 * @param resultat  La donnée réelle
	 * @param list      La zone à mettre à jour
	 */
	public void invaliderZone(String resultat, JList<String> list) {
		if (resultat.equals("O"))
			list.setSelectedIndex(0);
		else if (resultat.equals("N"))
			list.setSelectedIndex(1);
		else if (resultat.equals("XX"))
			list.setSelectedIndex(2);
		else
			list.setSelectedIndex(-1);
	}

	/**
	 * Mettre à jour les données du jeune en fonction de celles de la fiche.
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
				reponses.put("maniabilite" + i + "_1", this.lesZones.get(2*i).getSelectedValue());
				reponses.put("maniabilite" + i + "_2", this.lesZones.get(2*i+1).getSelectedValue());
			}

			for (int i = 1; i <= this.lesOrientation.size(); i++) {
				reponses.put("orientation" + i, this.lesOrientation.get(i-1).getText());
			}
			
			if (this.chaine.isSelected())
				reponses.put("chaine", "O");
			else
				reponses.put("chaine", "N");
			if (this.clefs.isSelected())
				reponses.put("clés", "O");
			else
				reponses.put("clés", "N");
			if (this.reparation.isSelected())
				reponses.put("reparation", "O");
			else
				reponses.put("reparation", "N");
			if (this.cables.isSelected())
				reponses.put("cables", "O");
			else
				reponses.put("cables", "N");
			

			// Regarder si tous les mémos sont remplis
			Boolean finiMemo = true;
			for (int i = 0; i < this.lesMemos.size(); i++) {
				if (this.lesMemos.get(i).getSelectedIndex() == -1) {
					finiMemo = false;
					break;
				}
			}

			if (finiMemo) {
				this.projet.arriveMemo(this.licencie);
			}

			// Regarder si toutes les balises sont remplies
			Boolean baliseFinie = true;
			for (int i = 0; i < this.lesBalises.size(); i++) {
				if (this.lesBalises.get(i).getSelectedIndex() == -1) {
					baliseFinie = false;
					break;
				}
			}

			if (baliseFinie)
				this.projet.arriveBalise(this.licencie);

			// Signaler que le jeune est revenu des deux parcours
			if (baliseFinie && finiMemo)
				this.licencie.setFini(true);
			else
				this.licencie.setFini(false);
			
			this.licencie.setLesReponses(reponses);
			this.projet.setCritEnreg(false);

			this.hide();
			this.projet.getFp().repaint();
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
		for (int i = 0 ; i < this.projet.getNbMemo() ; i++) {
			if (lesMemos.get(i).getSelectedIndex() != -1) {
				memo = true;
				break;
			}
		}

		// Si au moins un mémo est rempli, les autres doivent l'être
		if (memo) {
			// vérifier que les mémos ont une réponse sélectionnée
			for (int i = 0 ; i < this.projet.getNbMemo() ; i++) {
				if (lesMemos.get(i).getSelectedIndex() == -1) {
					lesMemos.get(i).setBackground(Color.RED);
					ok = false;
				} else {
					lesMemos.get(i).setBackground(Color.LIGHT_GRAY);
				}
			}
		} else {
			for (int i = 0 ; i < this.projet.getNbMemo() ; i++)
				lesMemos.get(i).setBackground(Color.LIGHT_GRAY);
		}

		// regarder si une balise ou une maniabilité est remplie
		Boolean balise = false;
		for (int i = 0 ; i < this.projet.getNbBalise() ; i++) {
			if (lesBalises.get(i).getSelectedIndex() != -1 || lesZones.get(i).getSelectedIndex() != -1) {
				balise = true;
				break;
			}
		}

		// Si au moins une zone ou une balise est remplie, les autres doivent l'être aussi
		if (balise) {
			// vérifier que les balises ont une réponse enregistrée
			for (int i = 0 ; i < this.projet.getNbBalise() ; i++) {
				if (lesBalises.get(i).getSelectedIndex() == -1) {
					lesBalises.get(i).setBackground(Color.RED);
					ok = false;
				} else {
					lesBalises.get(i).setBackground(Color.LIGHT_GRAY);
				}
			}

			// vérifier que les maniabilité sont renseignées
			for (int i = 0 ; i < this.projet.getNbBalise() ; i++) {
				if (lesZones.get(i).getSelectedIndex() == -1) {
					lesZones.get(i).setBackground(Color.RED);
					ok = false;
				} else {
					lesZones.get(i).setBackground(Color.LIGHT_GRAY);
				}
			}
		} else {
			for (int i = 0 ; i < this.projet.getNbMemo() ; i++) {
				lesMemos.get(i).setBackground(Color.LIGHT_GRAY);
				lesZones.get(i).setBackground(Color.LIGHT_GRAY);
			}
		}


		if (balise) {		
			// Si une balise est trouvée, la zone l'est aussi et inversement
			for (int i = 0 ; i < this.projet.getNbBalise(); i++) {
				if (lesBalises.get(i).getSelectedIndex() == 4 &&
						lesZones.get(i).getSelectedIndex() != 2) {
					lesZones.get(i).setBackground(Color.RED);
					lesBalises.get(i).setBackground(Color.RED);
					return false;
				}

				if (lesBalises.get(i).getSelectedIndex() != 4 &&
						lesZones.get(i).getSelectedIndex() == 2) {
					lesZones.get(i).setBackground(Color.RED);
					lesBalises.get(i).setBackground(Color.RED);
					return false;
				}
			}
		}

		return ok;
	}

	/**
	 * Vérifier la cohérence des balises trouvées et des zones de maniabilité éffectuées.
	 * @return Vrai si ok, faux s'il y a une incohérence
	 */
	public boolean verifScore() {
		boolean ok = true;
		for (int i = 0 ; i < this.projet.getNbMemo() ; i++) {
			if (lesMemos.get(i).getSelectedIndex() == -1) {
				ok = false;
				lesMemos.get(i).setBackground(Color.RED);
				this.setVisible(true);
			} else
				lesMemos.get(i).setBackground(Color.LIGHT_GRAY);
		}

		for (int i = 0 ; i < this.projet.getNbBalise() ; i++) {
			if (lesBalises.get(i).getSelectedIndex() == -1) {
				ok = false;
				lesBalises.get(i).setBackground(Color.RED);
				this.setVisible(true);
			} else
				lesBalises.get(i).setBackground(Color.LIGHT_GRAY);

			if (lesZones.get(i).getSelectedIndex() == -1) {
				ok = false;
				this.setVisible(true);
				lesZones.get(i).setBackground(Color.RED);
			} else
				lesZones.get(i).setBackground(Color.LIGHT_GRAY);
		}
		return ok;
	}

}
