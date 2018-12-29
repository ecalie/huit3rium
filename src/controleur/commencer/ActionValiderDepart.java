package controleur.commencer;

import vue.FicheDepart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
