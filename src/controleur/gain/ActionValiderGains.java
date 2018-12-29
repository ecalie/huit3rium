package controleur.gain;

import vue.FicheGains;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionValiderGains implements ActionListener {

    private FicheGains jif;

    public ActionValiderGains(FicheGains jif) {
        this.jif = jif;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.jif.validerGains();
    }


}
