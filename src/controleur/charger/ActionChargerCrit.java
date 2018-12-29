package controleur.charger;

import modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionChargerCrit implements ActionListener {
    private Projet projet;

    public ActionChargerCrit(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.projet.chargerCrit();
    }
}

