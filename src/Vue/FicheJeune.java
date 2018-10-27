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
import Modele.MauvaisFormatDateException;
import Modele.MauvaisFormatNumeroException;
import Modele.Niveau;
import Modele.Projet;

public class FicheJeune extends JInternalFrame {

    private JLabel labelErreur;
    private JTextField nom2;
    private JTextField prenom2;
    private JComboBox<String> club2;
    private JComboBox<Character> sexe2;
    private JTextField naissance2;
    private JTextField licence2;
    private JTextField numero2;
    private JComboBox<String> niveau2;
    private Jeune licencie;
    private Projet projet;

    public FicheJeune(Projet projet) {
        super("Ajout d'un licencié",  true, false, false, true);

        this.projet = projet;
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

        JLabel sexe1 = new JLabel("Sexe", JLabel.CENTER);
        this.sexe2 = new JComboBox<Character>();
        this.sexe2.addItem(' ');
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
        valider.addActionListener(new ActionValiderAjout(this));
        JButton annuler = new JButton("Annuler");
        annuler.addActionListener(new ActionAnnulerAjout(this));
        
        valider.setBackground(Color.DARK_GRAY);
        valider.setForeground(Color.LIGHT_GRAY);
        annuler.setBackground(Color.DARK_GRAY);
        annuler.setForeground(Color.LIGHT_GRAY);
        panel3.add(valider);
        panel3.add(annuler);

        this.labelErreur = new JLabel("");
        labelErreur.setForeground(Color.RED);
        this.add(labelErreur, BorderLayout.NORTH);
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

    public JComboBox<Character> getSexe2() {
        return sexe2;
    }
    
    public JTextField getNumero2() {
        return numero2;
    }

    public JComboBox<String> getniveau2() {
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
        this.niveau2.setSelectedItem(this.licencie.getNiveau().getNom());
        this.sexe2.setSelectedItem(this.licencie.getSexe());
        this.licence2.setText("" + this.licencie.getNumLicence());
        this.naissance2.setText("" + this.licencie.getNaissance());
    }

    /**
     * Mettre à jour le jeune asocié en fonction de la fiche
     **/
    public void validerModif() {
    	this.labelErreur.setText("");
        try {
            if (verif()) {
                if (licencie == null)
                    this.licencie = new Jeune(this, new FicheScore(this.projet));

                this.licencie.setNom(this.getNom2().getText());
                this.licencie.setPrenom(this.getPrenom2().getText());
                this.licencie.setClub(Projet.toClub(this.getClub2().getSelectedItem().toString()));
                this.licencie.setSexe(this.getSexe2().getSelectedItem().toString().charAt(0));
                this.licencie.setNaissance(Projet.convertir(this.naissance2.getText()));
                try {
                	this.licencie.setNumLicence(Integer.parseInt(this.licence2.getText()));
                } catch (NumberFormatException e) {
                	this.licence2.setBackground(Color.RED);
                    this.labelErreur.setText("Le numéro de lience doit être un nombre");
                    this.validate();      
                    return;
                }
                
                try {
                	this.licencie.setNumero(Integer.parseInt(this.numero2.getText()));
                } catch (NumberFormatException e) {
                	this.numero2.setBackground(Color.RED);
                    this.labelErreur.setText("Le numéro doit être un nombre (sans la lettre du club)");
                    this.validate(); 
                    return;
                }
                
                this.licencie.setNiveau(Niveau.get(this.getniveau2().getSelectedItem().toString()));

                this.licencie.getFiche2().setTitle(this.licencie.toString());
                this.licencie.getFiche2().setLicencie(this.licencie);
                this.setTitle(this.licencie.getClub().toString() + this.licencie.getNumero());
                this.projet.ajouterInscrit(this.licencie);
                this.hide();
                this.projet.affichage();
            }
        } catch(MauvaisFormatDateException e) {
            this.labelErreur.setText(e.getMessage());
            this.naissance2.setBackground(Color.RED);
            this.validate();
        } catch (MauvaisFormatNumeroException ee) {
            this.labelErreur.setText(ee.getMessage());
            this.numero2.setBackground(Color.RED);
            this.validate();        	
        }
    }
    
    /**
     * Vérifier si la fiche est bien remplie. Colorer en rouge les cases mal remplies.
     * @return Vrai si elle bien remplie, faux sinon
     */
    private boolean verif() {
    	Boolean stop = false;
		if (this.nom2.getText().equals("")) {
			this.nom2.setBackground(Color.RED);
			stop = true;
		} else {
			this.nom2.setBackground(Color.WHITE);
		}
		if (this.prenom2.getText().equals("")) {
			this.prenom2.setBackground(Color.RED);
			stop = true;
		} else {
			this.prenom2.setBackground(Color.WHITE);
		}
		if (this.naissance2.getText().equals("")) {
			this.naissance2.setBackground(Color.RED);
			stop = true;
		} else {
			this.naissance2.setBackground(Color.WHITE);
		}
		if (this.licence2.getText().equals("")) {
			this.licence2.setBackground(Color.RED);
			stop = true;
		} else {
			this.licence2.setBackground(Color.WHITE);
		}
		if (this.numero2.getText().equals("")) {
			this.numero2.setBackground(Color.RED);
			stop = true;
		} else {
			this.numero2.setBackground(Color.WHITE);
		}
		if (this.club2.getSelectedIndex() == 0) {
			this.club2.setBackground(Color.RED);
			stop = true;
		} else {
			this.club2.setBackground(Color.WHITE);
		}
		if (this.niveau2.getSelectedIndex() == 0) {
			this.niveau2.setBackground(Color.RED);
			stop = true;
		} else {
			this.niveau2.setBackground(Color.WHITE);
		}
		if (this.sexe2.getSelectedIndex() == 0) {
			this.sexe2.setBackground(Color.RED);
			stop = true;
		} else {
			this.sexe2.setBackground(Color.WHITE);
		}
		return !stop;
    }
}
