package vue;

import controleur.bonus.ActionDepartager;
import modele.Jeune;
import modele.Niveau;
import modele.Projet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class FicheBonus extends JInternalFrame {

    private List<Jeune> jeunes;
    private List<JComboBox<String>> classement;

    public FicheBonus(Projet projet, int score, SortedSet<Jeune> inscrits, int place, Niveau niveau) {
        super("Question bonus pour la place " + place, true, false, false, false);

        int index = 0;
        boolean trouve = false;
        this.jeunes = new ArrayList<>();
        for (Jeune j : inscrits) {
            if (j.getPoints() == score) {
                jeunes.add(j);
                trouve = true;
            }
            if (!trouve)
                index++;

        }

        this.getContentPane().setLayout(new BorderLayout());
        JPanel centre = new JPanel(new GridLayout(jeunes.size(), 2));
        JPanel south = new JPanel();
        this.getContentPane().add(centre, BorderLayout.CENTER);
        this.getContentPane().add(south, BorderLayout.SOUTH);

        this.classement = new ArrayList<>();
        String[] places = new String[jeunes.size() + 1];
        for (int i = 0; i <= jeunes.size(); i++)
            if (i == 0)
                places[i] = "";
            else
                places[i] = "" + i;

        for (Jeune j : jeunes) {
            JLabel nom = new JLabel(j.getPrenom() + " " + j.getNom());
            centre.add(nom);
            JComboBox<String> combo = new JComboBox<>(places);
            centre.add(combo);
            classement.add(combo);
        }

        JButton btn = new JButton("Valider");
        btn.addActionListener(new ActionDepartager(projet, inscrits, jeunes, classement, index, niveau, this));
        south.add(btn);

        this.pack();
        projet.getFp().getDesktop().add(this);
        this.show();
    }

}
