package controleur.charger;

import modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionChargerFichierInscription implements ActionListener {

    private Projet projet;

    public ActionChargerFichierInscription(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.projet.chargerFichierInscription();
    }

}
