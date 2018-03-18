package Controleur.Classement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;

import Modele.Niveau;
import Modele.Projet;

public class ActionClasserCouleur implements ActionListener {

	private JCheckBox vert;
	private JCheckBox bleu;
	private JCheckBox rouge;
	private JCheckBox noir;
	private Projet projet;
	private JInternalFrame jif;
	
	public ActionClasserCouleur(JCheckBox v, JCheckBox b, JCheckBox r, JCheckBox n, Projet p, JInternalFrame jif) {
		vert = v;
		bleu = b;
		rouge = r;
		noir = n;
		projet = p;
		this.jif = jif;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (vert.isSelected())
			this.projet.classerCouleur(Niveau.V);
		if (bleu.isSelected())
			this.projet.classerCouleur(Niveau.B);
		if (rouge.isSelected())
			this.projet.classerCouleur(Niveau.R);
		if (noir.isSelected())
			this.projet.classerCouleur(Niveau.N);
		jif.dispose();
	}

}
