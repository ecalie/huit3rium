package Vue;

import Controleur.Ajout.ActionAnnulerAjout;
import Controleur.Ajout.ActionValiderAjout;
import Modele.Categorie;
import Modele.Club;
import Modele.Jeune;
import Modele.Projet;

import javax.swing.*;
import java.awt.*;

public class FicheJeune extends JInternalFrame {

    private JTextField nom2;
    private JTextField prenom2;
    private JComboBox<String> club2;
    private JTextField numero2;
    private JComboBox<String> cate2;
    private Jeune licencie;

    public FicheJeune(Projet projet) {
        super("Ajout d'un licencié",  true, false, false, true);

        this.getContentPane().setLayout(new BorderLayout());

        JPanel panel1 = new JPanel(new GridLayout(5, 1));
        JPanel panel2 = new JPanel(new GridLayout(5, 1));
        JPanel panel3 = new JPanel(new GridLayout(1, 2));

        JLabel nom1 = new JLabel("Nom", JLabel.CENTER);
        this.nom2 = new JTextField(10);
        nom1.setLabelFor(this.nom2);
        panel1.add(nom1);
        panel2.add(this.nom2);

        JLabel prenom1 = new JLabel("Prénom", JLabel.CENTER);
        this.prenom2 = new JTextField(10);
        prenom1.setLabelFor(this.prenom2);
        panel1.add(prenom1);
        panel2.add(this.prenom2);

        JLabel numero1 = new JLabel("Numéro", JLabel.CENTER);
        this.numero2 = new JTextField(10);
        numero1.setLabelFor(this.numero2);
        panel1.add(numero1);
        panel2.add(this.numero2);

        JLabel club1 = new JLabel("Club", JLabel.CENTER);
        this.club2 = new JComboBox<String>();
        club2.addItem("Choisir le club");
        for (Club club : Club.values()) {
            this.club2.addItem(club.getNom());
        }
        club1.setLabelFor(this.club2);
        panel1.add(club1);
        panel2.add(this.club2);

        JLabel cate1 = new JLabel("Catégorie", JLabel.CENTER);
        this.cate2 = new JComboBox<String>();
        cate2.addItem("Choisir la catégorie");
        for (Categorie cate : Categorie.values()) {
            this.cate2.addItem(cate.getCouleur());
        }
        cate1.setLabelFor(this.cate2);
        panel1.add(cate1);
        panel2.add(this.cate2);

        JButton valider = new JButton("Valider");
        valider.addActionListener(new ActionValiderAjout(projet, this));
        JButton annuler = new JButton("Annuler");
        annuler.addActionListener(new ActionAnnulerAjout(this));
        valider.setBackground(Color.DARK_GRAY);
        valider.setForeground(Color.LIGHT_GRAY);
        annuler.setBackground(Color.DARK_GRAY);
        annuler.setForeground(Color.LIGHT_GRAY);
        panel3.add(valider);
        panel3.add(annuler);

        this.getContentPane().add(panel1, BorderLayout.WEST);
        this.getContentPane().add(panel2, BorderLayout.EAST);
        this.getContentPane().add(panel3, BorderLayout.SOUTH);

        /* Afficher la fenêtre */
        this.pack();
        this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    }

    public Jeune getLicencie() {
        return licencie;
    }

    public void setLicencie(Jeune licencie) {
        this.licencie = licencie;
    }

    public JTextField getNom2() {
        return nom2;
    }

    public JTextField getPrenom2() {
        return prenom2;
    }

    public JComboBox<String> getClub2() {
        return club2;
    }

    public JTextField getNumero2() {
        return numero2;
    }

    public JComboBox<String> getCate2() {
        return cate2;
    }

    /**
     * Mettre à jour la fiche en fonction du jeune associé
     **/
    public void invaliderModif() {
        this.nom2.setText(this.licencie.getNom());
        this.prenom2.setText(this.licencie.getPrenom());
        this.numero2.setText(this.licencie.getNumero());
        this.club2.setSelectedItem(this.licencie.getClub());
        this.cate2.setSelectedItem(this.licencie.getCate());
    }

    /**
     * Mettre à jour le jeune asocié en fonction de la fiche
     **/
    public void validerModif() {
        this.licencie.setNom(this.getPrenom2().getText());
        this.licencie.setClub(this.getClub2().getSelectedItem().toString());
        this.licencie.setNumero(this.getNumero2().getText());
        this.licencie.setCate(this.getCate2().getSelectedItem().toString());

        this.setTitle(this.licencie.getNumero());
    }
}
