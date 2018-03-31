package Controleur.Gains;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vue.FicheGains;

public class ActionAnnulerGains implements ActionListener{

	private FicheGains jif;
	
	public ActionAnnulerGains( FicheGains jif) {
		this.jif = jif;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.jif.invaliderModif();
	}
}
