package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controleur.Ajout.ActionAnnulerAjout;
import Controleur.Ajout.ActionValiderAjout;
import Modele.Club;
import Modele.Jeune;
import Modele.Niveau;
import Modele.Projet;

public class FicheJeune extends JInternalFrame {

    private JTextField nom2;
    private JTextField prenom2;
    private JComboBox<String> club2;
    private JComboBox<Character> sexe2;
    private JTextField naissance2;
    private JTextField licence2;
    private JTextField numero2;
    private JComboBox<String> niveau2;
    private Jeune licencie;

    public FicheJeune(Projet projet) {
        super("Ajout d'un licencié",  true, false, false, true);

        this.getContentPane().setLayout(new BorderLayout());

        JPanel panel1 = new JPanel(new GridLayout(8,1));
        JPanel panel2 = new JPanel(new GridLayout(8,1));
        JPanel panel3 = new JPanel(new FlowLayout());

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

        JLabel naissance1 = new JLabel("Date de naissance", JLabel.CENTER);
        this.naissance2 = new JTextField(10);
        naissance1.setLabelFor(this.naissance2);
        panel1.add(naissance1);
        panel2.add(this.naissance2);

        JLabel sexe1 = new JLabel("Prénom", JLabel.CENTER);
        this.sexe2 = new JComboBox<Character>();
        this.sexe2.addItem('F');
        this.sexe2.addItem('G');
        sexe1.setLabelFor(this.sexe2);
        panel1.add(sexe1);
        panel2.add(this.sexe2);

        JLabel numero1 = new JLabel("Numéro", JLabel.CENTER);
        this.numero2 = new JTextField(10);
        numero1.setLabelFor(this.numero2);
        panel1.add(numero1);
        panel2.add(this.numero2);

        JLabel licence1 = new JLabel("N° licence", JLabel.CENTER);
        this.licence2 = new JTextField(10);
        licence1.setLabelFor(this.licence2);
        panel1.add(licence1);
        panel2.add(this.licence2);

        JLabel club1 = new JLabel("Club", JLabel.CENTER);
        this.club2 = new JComboBox<String>();
        club2.addItem("Choisir le nom");
        for (Club club : Club.values()) {
            this.club2.addItem(club.getNom());
        }
        club1.setLabelFor(this.club2);
        panel1.add(club1);
        panel2.add(this.club2);

        JLabel cate1 = new JLabel("Catégorie", JLabel.CENTER);
        this.niveau2 = new JComboBox<String>();
        niveau2.addItem("Choisir la catégorie");
        for (Niveau niveau : Niveau.values()) {
            this.niveau2.addItem(niveau.getNom());
        }
        cate1.setLabelFor(this.niveau2);
        panel1.add(cate1);
        panel2.add(this.niveau2);

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
        return niveau2;
    }

    /**
     * Mettre à jour la fiche en fonction du jeune associé
     **/
    public void invaliderModif() {
        this.nom2.setText(this.licencie.getNom());
        this.prenom2.setText(this.licencie.getPrenom());
        this.numero2.setText("" + this.licencie.getNumero());
        this.club2.setSelectedItem(this.licencie.getClub().getNom());
        this.niveau2.setSelectedItem(this.licencie.getNiveau());
        this.sexe2.setSelectedItem(this.licencie.getSexe());
        this.licence2.setText("" + this.licencie.getNumLicence());
        this.naissance2.setText("" + this.licencie.getNaissance());
    }

    /**
     * Mettre à jour le jeune asocié en fonction de la fiche
     **/
    public void validerModif() {
        this.licencie.setNom(this.getPrenom2().getText());
        this.licencie.setClub(recupClub(this.getClub2().getSelectedItem().toString()));
        this.licencie.setNumero(Integer.parseInt(this.getNumero2().getText()));
        this.licencie.setNiveau(this.getCate2().getSelectedItem().toString());

        this.setTitle(this.licencie.getClub().toString() + this.licencie.getNumero());
    }
    
    
    private Club recupClub(String nom) {
		switch (nom) {
		case "A" : 
			return Club.A;
		case "B" : 
			return Club.B;
		case "C" : 
			return Club.C;
		case "D" : 
			return Club.D;
		case "E" : 
			return Club.E;
		case "F" : 
			return Club.F;
		case "G" : 
			return Club.G;
		case "H" : 
			return Club.H;
		case "I" : 
			return Club.I;
		case "J" : 
			return Club.J;
		case "K" : 
			return Club.K;
		case "L" : 
			return Club.L;
		default : 
			return Club.A;
		}
	}

}
