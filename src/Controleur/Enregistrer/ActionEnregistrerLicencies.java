package Controleur.Enregistrer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Projet;

public class ActionEnregistrerLicencies implements ActionListener {

    private Projet projet;

    public ActionEnregistrerLicencies(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.projet.enregistrerLicencies();
    }
}
