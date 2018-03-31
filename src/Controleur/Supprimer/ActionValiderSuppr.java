package Controleur.Supprimer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;

import Modele.Projet;

public class ActionValiderSuppr implements ActionListener {

    private JInternalFrame jif;
    private ArrayList<JCheckBox> list;
    private Projet projet;

    public ActionValiderSuppr(ArrayList<JCheckBox> list, Projet projet, JInternalFrame jif) {
        this.list = list;
        this.jif = jif;
        this.projet = projet;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       this.projet.validerSuppression(list, jif);
    }
}
