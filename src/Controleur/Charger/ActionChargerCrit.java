package Controleur.Charger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Modele.Projet;

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

