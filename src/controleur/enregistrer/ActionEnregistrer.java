package controleur.enregistrer;

import modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
