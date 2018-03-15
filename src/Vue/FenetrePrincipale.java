package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import Controleur.Administrateur.ActionAdministrateur;
import Controleur.Administrateur.ActionModifierFichierLicencies;
import Controleur.Administrateur.ActionQuitterAdministrateur;
import Controleur.Ajout.ActionAjouter;
import Controleur.Charger.ActionChargerFichierInscription;
import Controleur.Charger.ActionChargerInscrits;
import Controleur.Classement.ActionClasser;
import Controleur.Classement.ActionExporterClassement;
import Controleur.Demarrer.ActionDemarrerBleu;
import Controleur.Demarrer.ActionDemarrerNoir;
import Controleur.Demarrer.ActionDemarrerRouge;
import Controleur.Demarrer.ActionDemarrerVert;
import Controleur.Enregistrer.ActionEnregistrerCrit;
import Controleur.Enregistrer.ActionEnregistrerLicencies;
import Controleur.Enregistrer.ActionEnregistrerSous;
import Controleur.Enregistrer.ActionEnregistrerSousLicencies;
import Controleur.Gains.ActionDefinirGains;
import Controleur.Parametres.ActionModifParam;
import Controleur.Reponse.ActionDefinirReponses;
import Controleur.Score.AnctionEnregistrerScore;
import Controleur.Selectionner.ActionSelectionner;
import Controleur.Supprimer.ActionSupprimer;
import Modele.Projet;

public class FenetrePrincipale extends JFrame {

	/** L'espace des fenêtres internes. */
	private JDesktopPane desktop;

	/** Le projet associé à la fenêtre. */
	private Projet projet;

	/** Les grilles des licenciés et inscrits. */
	private JPanel grilleLicencies;
	private JPanel grilleInscrits;

	/** Les boutons admin. */
	private JPanel admin;

	/** La zone des boutons démarrer / admin. */
	private JPanel btnsDem;

	/** La zone des boutons critérium. */
	private JPanel btnsCrit;
	
	/** La zone des barres de progression. */
	private JPanel zoneProg;

	//////////////////
	///CONSTRUCTEUR///
	//////////////////

	/**
	 * Construire une fenêtre principale
	 **/
	public FenetrePrincipale() {
		super("Huit3rium");
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {}

		/* Mise en forme de la fenêtre */
		this.setLayout(new BorderLayout());

		/* L'espace des fenêtres internes */
		this.desktop = new JDesktopPane() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.GRAY);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};

		this.add(this.desktop, BorderLayout.CENTER);

		// La zone des barres de progression
		this.zoneProg = new JPanel(new FlowLayout());
		this.add(this.zoneProg, BorderLayout.SOUTH);
		this.zoneProg.setVisible(false);
		
		/* Initialiser un projet associé à la fenêtre */
		this.projet = new Projet(this);

		/* Afficher les boutons */
		this.btns();

		/* Les boutons administrateur */
		this.boutonsAdmin();

		/* La grille des licenciés */
		this.grilleLicencies = new JPanel(new GridLayout(20, 3));
		this.grilleInscrits = new JPanel(new GridLayout(20, 3));

		JPanel lesGrilles = new JPanel(new BorderLayout());
		lesGrilles.add(this.grilleLicencies, BorderLayout.WEST);
		lesGrilles.add(this.grilleInscrits, BorderLayout.EAST);
		this.add(lesGrilles, BorderLayout.WEST);
		
		/* Taille minimale de la fenêtre */
		this.setMinimumSize(new Dimension(500, 300));

		/* Afficher la fenetre */
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	////////////////////////
	///GETTERS ET SETTERS///
	////////////////////////

	/**
	 * Récupérer le projet asocié à la fenêtre principale.
	 * @return  Le projet
	 **/
	public Projet getProjet() {
		return projet;
	}

	/**
	 * Récupérer l'espace des fenêtres internes.
	 * @return  L'espace des fenêtres internes
	 **/
	public JDesktopPane getDesktop() {
		return desktop;
	}

	/**
	 * Récupérer la grille des licenciés.
	 * @param   La grille des kicenciés
	 **/
	public JPanel getGrilleLicencies() {
		return grilleLicencies;
	}

	/**
	 * Récupérer la zone des boutons administrateurs.
	 * @return La zone des boutons administrateurs
	 */
	public JPanel getAdmin() {
		return admin;
	}

	/**
	 * Récupérer la grille des inscrits.
	 * @return La grille des inscrits
	 */
	public JPanel getGrilleInscrits() {
		return grilleInscrits;
	}

	/**
	 * Récupérer la zone des boutons.
	 * @return : la zone des boutons
	 */
	public JPanel getBtnsDem() {
		return this.btnsDem;
	}

	/**
	 * Récupérer la zone du bouton d'enregistrements des scores.
	 * @return : la zone du bouton d'enregistrements des scores
	 */
	public JPanel getBtnsCrit() {
		return this.btnsCrit;
	}

	/** 
	 * Récupérer la zone des barres de progression. 
	 */
	public JPanel getZoneProg() {
		return this.zoneProg;
	}
	
	//////////////////////
	///METHODES PRIVEES///
	//////////////////////

	/**
	 * Ajouter la barre de menus.
	 **/
	private void btns() {
		// Créer les boutons 
		JButton admin = new JButton("Mode administrateur");
		admin.addActionListener(new ActionAdministrateur(this));

		JButton vert = new JButton("Vert");
		vert.addActionListener(new ActionDemarrerVert(this.projet));
		JButton bleu = new JButton("Bleu");
		bleu.addActionListener(new ActionDemarrerBleu(this.projet));
		JButton rouge = new JButton("Rouge");
		rouge.addActionListener(new ActionDemarrerRouge(this.projet));
		JButton noir = new JButton("Noir");
		noir.addActionListener(new ActionDemarrerNoir(this.projet));

		JButton score = new JButton("Calculer le classement");
		score.addActionListener(new ActionClasser(this.projet));

		JButton charger = new JButton("Charger un critérium");
		charger.addActionListener(new ActionChargerInscrits(this.projet));

		// Modifier les couleurs des boutons
		vert.setBackground(new Color(160, 160, 0));
		bleu.setBackground(new Color(160, 160, 0));
		rouge.setBackground(new Color(160, 160, 0));
		noir.setBackground(new Color(160, 160, 0));
		charger.setBackground(new Color(160, 160, 0));
		score.setBackground(new Color(160, 160, 0));
		admin.setBackground(new Color(160, 160, 0));
		
		// Ajouter les boutons 
		this.btnsDem = new JPanel();
		this.btnsDem.setLayout(new FlowLayout());
		this.btnsDem.add(vert);
		this.btnsDem.add(bleu);
		this.btnsDem.add(rouge);
		this.btnsDem.add(noir);
		this.btnsDem.add(score);
		this.btnsDem.add(charger);
		this.btnsDem.add(admin);

		// Créer le bouton d'enregistrement
		JButton sauverCrit = new JButton("Enregistrer");
		sauverCrit.addActionListener(new AnctionEnregistrerScore(this));
		sauverCrit.setBackground(new Color(160, 160, 0));
		
		// Créer le bouton et sa zone
		this.btnsCrit = new JPanel();
		this.btnsCrit.add(sauverCrit);
		this.btnsCrit.setVisible(false);

		// Ajouter les deux zones de boutons
		JPanel boutons = new JPanel(new BorderLayout());
		boutons.add(btnsDem, BorderLayout.WEST);
		boutons.add(btnsCrit, BorderLayout.EAST);
				
		this.getContentPane().add(boutons, BorderLayout.NORTH);
	}

	/**
	 * Ajouter les boutons administrateur.
	 **/
	private void boutonsAdmin() {
		/* Le panel des boutons administrateur */
		this.admin = new JPanel(new GridLayout(22, 1));

		JLabel separation = new JLabel("- - -");
		separation.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel separation2 = new JLabel("- - -");
		separation2.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel separation3 = new JLabel("- - -");
		separation3.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel separation4 = new JLabel("- - -");
		separation4.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel separation5 = new JLabel("- - -");
		separation5.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel separation6 = new JLabel("- - -");
		separation6.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel separation7 = new JLabel("- - -");
		separation7.setHorizontalAlignment(SwingConstants.CENTER);

		/* Boutons admin */
		JButton btnQuitter = new JButton("Quitter le mode administrateur");
		btnQuitter.addActionListener(new ActionQuitterAdministrateur(this));
		btnQuitter.setBackground(Color.DARK_GRAY);
		btnQuitter.setForeground(Color.WHITE);

		JButton btnAjouter = new JButton("Ajouter un licencié");
		btnAjouter.addActionListener(new ActionAjouter(this));
		btnAjouter.setBackground(Color.DARK_GRAY);
		btnAjouter.setForeground(Color.WHITE);

		JButton btnSupprimer = new JButton("Supprimer un licencié");
		btnSupprimer.addActionListener(new ActionSupprimer(this.projet));
		btnSupprimer.setBackground(Color.DARK_GRAY);
		btnSupprimer.setForeground(Color.WHITE);

		JButton btnEnregistrerLicencies = new JButton("Enregistrer les licenciés");
		btnEnregistrerLicencies.addActionListener(new ActionEnregistrerLicencies(this.projet));
		btnEnregistrerLicencies.setBackground(Color.DARK_GRAY);
		btnEnregistrerLicencies.setForeground(Color.WHITE);

		JButton btnEnregistrerSousLicencies = new JButton("Enregistrer Sous");
		btnEnregistrerSousLicencies.addActionListener(new ActionEnregistrerSousLicencies(this.projet));
		btnEnregistrerSousLicencies.setBackground(Color.DARK_GRAY);
		btnEnregistrerSousLicencies.setForeground(Color.WHITE);

		JButton btnSelection = new JButton("Sélectionner les inscrits");
		btnSelection.addActionListener(new ActionSelectionner(this.projet));
		btnSelection.setBackground(Color.DARK_GRAY);
		btnSelection.setForeground(Color.WHITE);

		JButton btnEnregistrerSelectionnes = new JButton("Enregistrer le critérium");
		btnEnregistrerSelectionnes.addActionListener(new ActionEnregistrerCrit(this.projet));
		btnEnregistrerSelectionnes.setBackground(Color.DARK_GRAY);
		btnEnregistrerSelectionnes.setForeground(Color.WHITE);

		JButton btnEnregistrerSous = new JButton("Enregistrer Sous");
		btnEnregistrerSous.addActionListener(new ActionEnregistrerSous(this.projet));
		btnEnregistrerSous.setBackground(Color.DARK_GRAY);
		btnEnregistrerSous.setForeground(Color.WHITE);

		JButton btnChargerCrit = new JButton("Charger un critérium");
		btnChargerCrit.addActionListener(new ActionChargerInscrits(this.projet));
		btnChargerCrit.setBackground(Color.DARK_GRAY);
		btnChargerCrit.setForeground(Color.WHITE);

		JButton btnSolution = new JButton("Enregistrer les réponses");
		btnSolution.addActionListener(new ActionDefinirReponses(this.projet));
		btnSolution.setBackground(Color.DARK_GRAY);
		btnSolution.setForeground(Color.WHITE);

		JButton btnGains = new JButton("Enregistrer les gains");
		btnGains.addActionListener(new ActionDefinirGains(this.projet));
		btnGains.setBackground(Color.DARK_GRAY);
		btnGains.setForeground(Color.WHITE);

		JButton btnModifNb = new JButton("Modifier les paramètres");
		btnModifNb.addActionListener(new ActionModifParam(this.projet));
		btnModifNb.setBackground(Color.DARK_GRAY);
		btnModifNb.setForeground(Color.WHITE);

		JButton btnModifConfig = new JButton("Modifier le fichier des licenciés");
		btnModifConfig.addActionListener(new ActionModifierFichierLicencies(this.projet));
		btnModifConfig.setBackground(Color.DARK_GRAY);
		btnModifConfig.setForeground(Color.WHITE);
		
		JButton exporterClassement = new JButton("Exporter le classement");
		exporterClassement.addActionListener(new ActionExporterClassement(this.projet));
		exporterClassement.setBackground(Color.DARK_GRAY);
		exporterClassement.setForeground(Color.WHITE);
		
		JButton btnChargerInscritClub = new JButton("Charger un fichier d'inscription");
		btnChargerInscritClub.addActionListener(new ActionChargerFichierInscription(this.projet));
		btnChargerInscritClub.setBackground(Color.DARK_GRAY);
		btnChargerInscritClub.setForeground(Color.WHITE);
		
		/* Ajouter les boutons */
		this.admin.add(btnQuitter);

		this.admin.add(separation);

		this.admin.add(btnAjouter);
		this.admin.add(btnSupprimer);

		this.admin.add(separation7);
		
		this.admin.add(btnChargerInscritClub);
		
		this.admin.add(separation5);
		
		this.admin.add(btnEnregistrerLicencies);
		this.admin.add(btnEnregistrerSousLicencies);

		this.admin.add(separation2);

		this.admin.add(btnSelection);
		this.admin.add(btnSolution);
		this.admin.add(btnGains);
		this.admin.add(btnModifNb);

		this.admin.add(separation3);

		this.admin.add(btnChargerCrit);
		this.admin.add(btnEnregistrerSelectionnes);
		this.admin.add(btnEnregistrerSous);
		
		this.admin.add(separation4);
		
		this.admin.add(btnModifConfig);
		
		this.admin.add(separation6);
		
		this.admin.add(exporterClassement);

		this.add(this.admin, BorderLayout.EAST);

		/* Masquer les boutons */
		this.admin.setVisible(false);
	}


	////////////////////////
	///METHODES PUBLIQUES///
	////////////////////////

	public void fermerModeAdmin() {
		this.grilleLicencies.setVisible(false);
		this.admin.setVisible(false);

		this.btnsCrit.setVisible(true);
		this.btnsDem.setVisible(true);
	}

	public void ouvrirModeAdmin() {
		// Rendre inivisible les boutons et les numéros des inscrits
		this.btnsDem.setVisible(false);
		this.btnsCrit.setVisible(false);
		this.grilleInscrits.setVisible(false);
	}

	@Override
	public void dispose() {
		if (!this.projet.getCritEnreg()) {
			int resultatCrit = javax.swing.JOptionPane.showConfirmDialog(this.getDesktop(),
					"Voulez-vous enregistrer les modifications sur le critérium ?");

			// Si oui
			if (resultatCrit == 0) {
				// Enregistrer le critérium
				this.projet.enregistrerResultats();
			}

			// Si oui ou non 
			if (resultatCrit != 2) {
				// fermer la fenêtre
				super.dispose();
			}
		} else {
			super.dispose();
		}
	}
}




