package Controleur.Ajout; 
/**
 * Created by AurÃ©lie on 19/08/2017.
 */

import Vue.FicheJeune;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionAnnulerAjout implements ActionListener {

    private FicheJeune fiche;

    public ActionAnnulerAjout(FicheJeune fiche) {
        this.fiche = fiche;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!this.fiche.getTitle().equals("Ajout d'un licencié")) {
            this.fiche.invaliderModif();
            this.fiche.hide();
        } else {
            this.fiche.dispose();
        }
    }
}
