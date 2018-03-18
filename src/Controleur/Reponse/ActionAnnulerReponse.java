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
public class ActionAnnulerReponse implements ActionListener {

    private FicheReponse ficheReponse;

    public ActionAnnulerReponse(FicheReponse ficheReponse) {
        this.ficheReponse = ficheReponse;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.ficheReponse.invaliderModif();
    }
}
