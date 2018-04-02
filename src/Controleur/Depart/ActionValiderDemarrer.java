package Controleur.Depart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JRadioButton;

import Modele.Niveau;
import Modele.Projet;

public class ActionValiderDemarrer implements ActionListener {

	private JRadioButton vert;
	private JRadioButton bleu;
	private JRadioButton rouge;
	private JRadioButton noir;
	private Projet projet;
	private JInternalFrame jif;
	
	public ActionValiderDemarrer(JRadioButton v, JRadioButton b, JRadioButton r,JRadioButton n, Projet p, JInternalFrame jif) {
		vert = v;
		bleu = b;
		rouge = r;
		noir = n;
		projet = p;
		this.jif = jif;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.vert.isSelected())
			this.projet.demarrer(Niveau.V);
		if (this.bleu.isSelected())
			this.projet.demarrer(Niveau.B);
		if (this.rouge.isSelected())
			this.projet.demarrer(Niveau.R);
		if (this.noir.isSelected())
			this.projet.demarrer(Niveau.N);
		jif.dispose();
	}

}
