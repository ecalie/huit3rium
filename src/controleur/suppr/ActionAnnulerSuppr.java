package controleur.suppr;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionAnnulerSuppr implements ActionListener {

    private JInternalFrame jif;

    public ActionAnnulerSuppr(JInternalFrame jif) {
        this.jif = jif;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.jif.dispose();
    }
}
