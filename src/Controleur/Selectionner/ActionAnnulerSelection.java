package Controleur.Selectionner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionAnnulerSelection implements ActionListener {

    private JInternalFrame jif;

    public ActionAnnulerSelection(JInternalFrame jif) {
        this.jif = jif;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.jif.dispose();
    }
}
