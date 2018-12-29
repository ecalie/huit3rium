package controleur.param;

import modele.Jeune;
import modele.Niveau;
import modele.Projet;
import vue.FicheParam;
import vue.FicheReponse;
import vue.FicheScore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Aurélie on 18/11/2017.
 */
public class ActionValiderParam implements ActionListener {

    private FicheParam fiche;
    private Projet projet;

    public ActionValiderParam(FicheParam fiche, Projet projet) {
        this.fiche = fiche;
        this.projet = projet;
    }

    public void actionPerformed(ActionEvent e) {
        // enregistrer les modifications
        this.fiche.validerModifier();

        // mettre à jour la fiche réponse
        for (Niveau niv : Niveau.values()) {
            FicheReponse fr = new FicheReponse(this.projet, niv);
            this.projet.getFp().getDesktop().add(fr);
            this.projet.getFicheReponse().put(niv, fr);
        }

        // mettre à jour la liste des réponses du projet
        for (Niveau niv : Niveau.values()) {
            this.projet.getReponses().get(niv).clear();
            for (int i = 0; i < this.projet.getNbMemo(); i++) {
                this.projet.getReponses().get(niv).put("memo" + i, "");
            }
            for (int i = 0; i < this.projet.getNbBalise(); i++) {
                this.projet.getReponses().get(niv).put("balise" + i, "");
            }
            for (int i = 0; i < this.projet.getNbOrientation(); i++) {
                for (int j = 0; j < this.projet.getNbCircuit(); j++) {
                    this.projet.getReponses().get(niv).put("orientation" + i + "_" + j, "");
                }
            }
        }

        // mettre à jour les fiches jeunes
        for (Jeune j : this.projet.getLesInscrits()) {
            j.setFiche2(new FicheScore(this.projet));
        }
    }
}