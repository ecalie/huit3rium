package Controleur.Parametres;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Vue.FicheParam;

/**
 * Created by Aurélie on 18/11/2017.
 */
public class ActionValiderParam implements ActionListener {

    private FicheParam fiche;

    public ActionValiderParam(FicheParam fiche) {
        this.fiche = fiche;
    }

    public void actionPerformed(ActionEvent e) {
    	this.fiche.validerModifier();
    }
}