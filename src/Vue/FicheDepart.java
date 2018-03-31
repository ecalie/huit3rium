package Vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controleur.Depart.ActionValiderDepart;
import Modele.Jeune;
import Modele.Projet;

public class FicheDepart extends JInternalFrame {

	private ArrayList<Jeune> ordre;
	private Projet projet;
	
	public FicheDepart(ArrayList<Jeune> o, Projet projet) {
		super(o.get(0).toString(), false, false, false, false);
		
		this.ordre = o;
		this.projet = projet;
		
		this.getContentPane().setLayout(new BorderLayout());
		
		// La zone de saisie du parcours d'orientation
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		
		JLabel saisie = new JLabel("Numéro du parcours d'orientation");
		JTextField saisie2 = new JTextField(5);
		saisie.setLabelFor(saisie2);
		
		panel1.add(saisie);
		panel1.add(saisie2);
		
		// La zone du bouton valider
		JPanel panel2 = new JPanel();
		
		JButton btn = new JButton("Valider");
		btn.addActionListener(new ActionValiderDepart(this));
		
		panel2.add(btn);
		
		this.add(panel1, BorderLayout.CENTER);
		this.add(panel2, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
		this.projet.getFp().getDesktop().add(this);
	}
	
	public void valider() {
		// Supprimer le jeune de la liste des prochains départs
		this.ordre.remove(0);
		
		// Afficher la liste mise à jour
		this.projet.getFp().afficherOrdre(this.ordre);
		
		if (this.ordre.size() > 0) {
			// Ouvrir une fiche du prochain départ
			new FicheDepart(this.ordre, this.projet);
		}
		
		// Fermer la fenêtre
		this.dispose();
	}
}
