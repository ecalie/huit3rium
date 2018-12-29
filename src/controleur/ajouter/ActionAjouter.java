package controleur.ajouter;

import vue.FenetrePrincipale;
import vue.FicheJeune;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionAjouter implements ActionListener {
    private FenetrePrincipale fp;

    public ActionAjouter(FenetrePrincipale fp) {
        this.fp = fp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FicheJeune fiche = new FicheJeune(this.fp.getProjet());
        this.fp.getDesktop().add(fiche);
        fiche.setVisible(true);
    }
}
