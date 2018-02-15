package Controleur.Selectionner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;

import Modele.Projet;

public class ActionValiderSelection implements ActionListener {

    private Projet projet;
    private ArrayList<JCheckBox> list;
    private JInternalFrame jif;

    public ActionValiderSelection(Projet projet, ArrayList<JCheckBox> list, JInternalFrame jif) {
        this.projet = projet;
        this.list = list;
        this.jif = jif;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.projet.validerSelection(list, jif);
    }
}