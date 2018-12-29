package controleur.scores;

import vue.FicheScore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionValiderScore implements ActionListener {

    private FicheScore fs;

    public ActionValiderScore(FicheScore fs) {
        this.fs = fs;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.fs.validerModif();
    }
}
