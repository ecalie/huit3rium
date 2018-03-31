package Controleur.Depart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vue.FicheDepart;

public class ActionValiderDepart implements ActionListener {

	private FicheDepart jif;
	
	public ActionValiderDepart(FicheDepart jif) {
		this.jif = jif;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.jif.valider();		
	}

}
