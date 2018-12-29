package controleur.bonus;

import modele.Jeune;
import modele.Niveau;
import modele.Projet;
import vue.FicheBonus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ActionDepartager implements ActionListener {

    private SortedSet<Jeune> classement;
    private List<Jeune> egalites;
    private List<JComboBox<String>> places;
    private int index;
    private Projet projet;
    private Niveau niveau;
    private FicheBonus ficheBonus;

    public ActionDepartager(Projet projet, SortedSet<Jeune> classement, List<Jeune> egalites, List<JComboBox<String>> places,
                            int index, Niveau niveau, FicheBonus ficheBonus) {
        this.classement = classement;
        this.egalites = egalites;
        this.places = places;
        this.index = index;
        this.projet = projet;
        this.ficheBonus = ficheBonus;
        this.niveau = niveau;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        List<Jeune> classement = new ArrayList<>(this.classement);

        // vérifier que tous les champs sont remplis et différents
        boolean ok = true;
        HashMap<String, JComboBox<String>> valeurs = new HashMap<>();
        for (JComboBox<String> c : places)
            if (c.getSelectedIndex() == 0) {
                c.setBackground(Color.RED);
                ok = false;
            } else if (valeurs.keySet().contains(c.getSelectedItem())) {
                c.setBackground(Color.RED);
                valeurs.get(c.getSelectedItem()).setBackground(Color.RED);
                ok = false;
            } else {
                valeurs.put((String) c.getSelectedItem(), c);
                c.setBackground(Color.LIGHT_GRAY);
            }


        if (ok) {
            // récupérer le classement des jeunes
            for (int i = 1; i <= egalites.size(); i++)
                for (int j = 0; j < places.size(); j++)
                    if (places.get(j).getSelectedItem().equals("" + i))
                        classement.set(index + i - 1, egalites.get(j));

            SortedSet<Jeune> tmp = new TreeSet<>((Jeune j1, Jeune j2) ->
                    ((j2.getPoints() - j1.getPoints()) == 0) ? 1 : j2.getPoints() - j1.getPoints());

            tmp.addAll(classement);

            this.projet.getClassement().put(niveau, tmp);
            this.projet.afficherClassement(niveau, classement);
            this.ficheBonus.hide();
        }
    }
}
