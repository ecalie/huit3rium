package controleur.scores;

import modele.Projet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionDeselectionner implements ActionListener {

    private JList<String> list;
    private Projet projet;

    public ActionDeselectionner(JList<String> list, Projet projet) {
        this.list = list;
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (list.getSelectedIndex() != -1) {
            list.clearSelection();
            this.projet.setCritEnreg(false);
        }
    }

}
