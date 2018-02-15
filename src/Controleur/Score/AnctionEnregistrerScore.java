/**
 * Created by Aurélie on 15/10/2017.
 */
package Controleur.Score;

import Vue.FenetrePrincipale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnctionEnregistrerScore implements ActionListener {

    /**
     * La fenêtre principale
     */
    private FenetrePrincipale fp;

    public AnctionEnregistrerScore(FenetrePrincipale fenetrePrincipale) {
        this.fp = fenetrePrincipale;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.fp.getProjet().enregistrerCrit();
    }
}
