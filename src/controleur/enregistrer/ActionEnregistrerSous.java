package controleur.enregistrer;

import modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionEnregistrerSous implements ActionListener {

    Projet projet;

    public ActionEnregistrerSous(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.projet.enregistrerSous();
    }

}
