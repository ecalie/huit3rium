package controleur.ajouter;

import modele.Jeune;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionModifierFiche implements ActionListener {

    private Jeune licencie;

    public ActionModifierFiche(Jeune licencie) {
        this.licencie = licencie;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.licencie.getFiche().setVisible(true);
    }
}
