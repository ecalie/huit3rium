package controleur.admin;

import vue.FenetrePrincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionQuitterAdministrateur implements ActionListener {
    private FenetrePrincipale fp;

    public ActionQuitterAdministrateur(FenetrePrincipale fp) {
        this.fp = fp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.fp.fermerModeAdmin();
    }
}