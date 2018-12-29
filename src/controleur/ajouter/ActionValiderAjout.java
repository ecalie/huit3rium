package controleur.ajouter;

import vue.FicheJeune;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionValiderAjout implements ActionListener {

    private FicheJeune fiche;

    public ActionValiderAjout(FicheJeune fiche) {
        this.fiche = fiche;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.fiche.validerModif();
    }
}
