package Controleur.Gains;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JTextField;

import Modele.Projet;
import Vue.FicheGains;

public class ActionValiderGains implements ActionListener{

	private FicheGains jif;
	
	public ActionValiderGains(FicheGains jif) {
		this.jif = jif;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.jif.validerGains();
	}
	

}
