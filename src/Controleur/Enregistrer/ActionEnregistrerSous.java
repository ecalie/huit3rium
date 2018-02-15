package Controleur.Enregistrer;

import Modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionEnregistrerSous implements ActionListener {

    private Projet projet;

    public ActionEnregistrerSous(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.projet.enregistrerSousCrit();
    }
}