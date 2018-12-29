package controleur.classement;

import modele.Niveau;
import modele.Projet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionAfficherCouleur implements ActionListener {

    private JCheckBox vert;
    private JCheckBox bleu;
    private JCheckBox rouge;
    private JCheckBox noir;
    private Projet projet;
    private JInternalFrame jif;

    public ActionAfficherCouleur(JCheckBox v, JCheckBox b, JCheckBox r, JCheckBox n, Projet p, JInternalFrame jif) {
        vert = v;
        bleu = b;
        rouge = r;
        noir = n;
        projet = p;
        this.jif = jif;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (vert.isSelected())
            this.projet.classer(Niveau.V);
        if (bleu.isSelected())
            this.projet.classer(Niveau.B);
        if (rouge.isSelected())
            this.projet.classer(Niveau.R);
        if (noir.isSelected())
            this.projet.classer(Niveau.N);
        jif.dispose();
    }

}
