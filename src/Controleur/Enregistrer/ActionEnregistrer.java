package Controleur.Enregistrer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Projet;

public class ActionEnregistrer implements ActionListener {

    private Projet projet;

    public ActionEnregistrer(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.projet.enregistrer();
    }
}
