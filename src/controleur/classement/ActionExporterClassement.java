package controleur.classement;

import modele.Projet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionExporterClassement implements ActionListener {

    private Projet projet;

    public ActionExporterClassement(Projet projet) {
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.projet.exporterQuelClassement();
    }

}
