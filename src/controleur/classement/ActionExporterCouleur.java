package controleur.classement;

import modele.Niveau;
import modele.Projet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionExporterCouleur implements ActionListener {

    private JCheckBox vert;
    private JCheckBox bleu;
    private JCheckBox rouge;
    private JCheckBox noir;
    private Projet projet;
    private JInternalFrame jif;

    public ActionExporterCouleur(JCheckBox v, JCheckBox b, JCheckBox r, JCheckBox n, Projet p, JInternalFrame jif) {
        vert = v;
        bleu = b;
        rouge = r;
        noir = n;
        projet = p;
        this.jif = jif;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (this.vert.isSelected())
            this.projet.exporterClassement(Niveau.V);
        if (this.bleu.isSelected())
            this.projet.exporterClassement(Niveau.B);
        if (this.rouge.isSelected())
            this.projet.exporterClassement(Niveau.R);
        if (this.noir.isSelected())
            this.projet.exporterClassement(Niveau.N);
        jif.dispose();
    }

}
