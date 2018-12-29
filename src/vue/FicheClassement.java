package vue;

import modele.Jeune;
import modele.Niveau;
import modele.Projet;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FicheClassement extends JInternalFrame {

    private Projet projet;

    /**
     * Construire une fiche de classement.
     *
     * @param projet Le projetlié à la fenêtre
     */
    public FicheClassement(Projet projet) {
        super("", true, true, false, true);
        this.projet = projet;
        this.projet.getFp().getDesktop().add(this);
        this.pack();
        this.hide();
    }

    /**
     * Afficher la fenêtre interne.
     *
     * @param classement L'ensembles des jeunes à afficher
     * @param niveau      La catégorie à afficher
     */
    public void afficherCouleur(List<Jeune> classement, Niveau niveau) {
        this.setTitle("Classement des " + niveau.getNom());

        this.getContentPane().setLayout(new GridBagLayout());
        int i = 0;
        int k = 0;
        int nbPoints = 0;

        for (Jeune j : classement) {
            if (j.getNiveau() == niveau) {
                i++;
                if (nbPoints != j.getPoints())
                    k = i;

                nbPoints = j.getPoints();

                // initialiser les labels
                JLabel place = new JLabel("" + k);
                JLabel nom = new JLabel(j.getNom());
                JLabel prenom = new JLabel(j.getPrenom());
                JLabel club = new JLabel(j.getClub().getNom());
                JLabel points = new JLabel("" + j.getPoints());
                place.setForeground(Color.WHITE);
                nom.setForeground(Color.WHITE);
                prenom.setForeground(Color.WHITE);
                club.setForeground(Color.WHITE);
                points.setForeground(Color.WHITE);

                // ajouter les labels
                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = i;
                this.add(place, c);

                c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 1;
                c.gridy = i;
                this.add(new JLabel("     "), c);

                c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 2;
                c.gridy = i;
                c.gridwidth = 2;
                this.add(nom, c);

                c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 4;
                c.gridy = i;
                this.add(new JLabel("     "), c);

                c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 5;
                c.gridy = i;
                c.gridwidth = 2;
                this.add(prenom, c);

                c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 7;
                c.gridy = i;
                this.add(new JLabel("     "), c);

                c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 8;
                c.gridy = i;
                c.gridwidth = 6;
                this.add(club, c);

                c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 14;
                c.gridy = i;
                this.add(new JLabel("   "), c);

                c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 15;
                c.gridy = i;
                this.add(points, c);

                // modifier la couleur du fond
                this.setBackground(niveau.getColor());
                this.setForeground(new Color(255, 255, 255));
            }
        }
        this.pack();
        this.show();
    }

    @Override
    public void dispose() {
        for (FicheClassement fc : this.projet.getfc())
            if (fc != null)
                fc.fermer();
    }

    public void fermer() {
        super.dispose();
    }


}
