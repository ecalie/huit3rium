package controleur.reponses;

import vue.FicheReponse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Aurélie on 31/10/2017.
 */
public class ActionValiderReponse implements ActionListener {

    private FicheReponse ficheReponse;

    public ActionValiderReponse(FicheReponse ficheReponse) {
        this.ficheReponse = ficheReponse;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.ficheReponse.validerModif();
    }
}
