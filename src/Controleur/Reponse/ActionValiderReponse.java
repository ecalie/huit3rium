package Controleur.Reponse;

import Modele.Projet;
import Vue.FicheReponse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JList;

/**
 * Created by Aurélie on 31/10/2017.
 */
public class ActionValiderReponse implements ActionListener {

    private ArrayList<JList<String>> memo;
    private ArrayList<JList<String>> balise;

    private FicheReponse ficheReponse;

    public ActionValiderReponse(Projet projet, ArrayList<JList<String>> memo, 
    		ArrayList<JList<String>> balise, FicheReponse ficheReponse) {
        this.balise = balise;
        this.memo = memo;
        this.ficheReponse = ficheReponse;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.ficheReponse.validerModif(memo, balise);
    }
}
