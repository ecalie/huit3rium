package Modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controleur.Ajout.ActionModifierFiche;
import Controleur.Score.ActionModifierScore;
import Controleur.Selectionner.ActionAnnulerSelection;
import Controleur.Selectionner.ActionValiderSelection;
import Controleur.Supprimer.ActionAnnulerSuppr;
import Controleur.Supprimer.ActionValiderSuppr;
import Vue.FenetrePrincipale;
import Vue.FicheClassement;
import Vue.FicheGains;
import Vue.FicheJeune;
import Vue.FicheParam;
import Vue.FicheReponse;
import Vue.FicheScore;

public class Projet {

	/** La liste ds licenciés. */
	private ArrayList<Jeune> lesLicencies;

	/** La fenêrte principale associée. */
	private FenetrePrincipale fp;

	/** La liste des inscrits. */
	private ArrayList<Jeune> lesInscrits;

	/** Le nom du fichier du critérium. */
	private String nomFichierCrit;

	/** Le nom du fichier des licenciés. */
	private String nomFichierLicencie;

	/** La liste des gains pour chaque épreuve. */
	private HashMap<String, Integer> gains;

	/** La liste des réponses. */
	private HashMap<String, String> reponses;

	/** Le nombre de balises. */
	private int nbBalise;

	/** Le nombre de mémo orinatation. */
	private int nbMemo;

	/** La fenêtre des points gagnés par épreuve. */
	private FicheGains ficheGains;

	/** Fiche paramètre. */
	private FicheParam ficheParametre;

	/** Fiche réponse. */
	private FicheReponse ficheReponse;

	/** Vrai tant que le projet est conforme. */
	private Boolean conforme;

	/** Vrai tant que les licenciés n'ont pas été modifiés. */
	private Boolean licenciesEnreg;

	/** Vrai tant que le critérium n'a pas été modifié. */
	private Boolean critEnreg;

	/** Nombre de jeunes revenus des mémos. */
	private ArrayList<Jeune> revenuMemo;
	private JLabel nombreArriveMemo;

	/** Nombre de jeunes revenus des balises. */
	private ArrayList<Jeune> revenuBalises;
	private JLabel nombreArriveBalise;

	/** ProgressBar des jeunes revenus. */
	private JProgressBar barreMemo;
	private JProgressBar barreBalise;

	/** Classement du critérium. */
	private ArrayList<Jeune> classement;

	/** Le nom du fichier fwLog. */
	public static FileWriter fwLog = null;

	public static final HashMap<String, Integer> alphabet = new HashMap<>();

	//////////////////
	// CONSTRUCTEUR //
	//////////////////

	/**
	 * Construire un projet
	 * @param fp  La fenêtre principale 
	 */
	public Projet(FenetrePrincipale fp) {
		try {
			File file = new File("log.txt");
			file.createNewFile();
			Projet.fwLog = new FileWriter(file);
		} catch (IOException e) {}
		this.fp = fp;
		this.nbBalise = 4;
		this.nbMemo = 2;
		this.reponses = new HashMap<>();
		this.lesLicencies = new ArrayList<>();
		this.lesInscrits = new ArrayList<>();
		this.gains = new HashMap<>();
		this.ficheGains = new FicheGains(this);
		this.ficheParametre = new FicheParam(this);
		this.ficheReponse = new FicheReponse(this);
		this.conforme = true;
		this.licenciesEnreg = true;
		this.critEnreg = true;
		this.barreMemo = new JProgressBar();
		this.barreBalise = new JProgressBar();
		this.revenuMemo = new ArrayList<>();
		this.revenuBalises = new ArrayList<>();
		this.nombreArriveBalise = new JLabel("");
		this.nombreArriveMemo = new JLabel("");

		this.fp.getDesktop().add(this.ficheGains);
		this.fp.getDesktop().add(this.ficheParametre);
		this.fp.getDesktop().add(this.ficheReponse);

		// Gérer le sud de la fenêtre : les bare de progression
		JPanel panel = this.fp.getZoneProg();

		panel.add(new JLabel("Balises : "));
		panel.add(this.nombreArriveBalise);
		panel.add(this.barreBalise);

		panel.add(new JLabel("Mémo orientation : "));
		panel.add(this.nombreArriveMemo);
		panel.add(this.barreMemo);

		alphabet.put("A", 0);
		alphabet.put("B", 1);
		alphabet.put("C", 2);
		alphabet.put("D", 3);
		alphabet.put("E", 4);
		alphabet.put("F", 5);
		alphabet.put("G", 6);
		alphabet.put("H", 7);
		alphabet.put("I", 8);
		alphabet.put("J", 9);
		alphabet.put("K", 10);
		alphabet.put("L", 11);
		alphabet.put("M", 12);
		alphabet.put("N", 13);
		alphabet.put("O", 14);

		File param = new File("./fichierConfiguration.txt");
		if (param.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(param));
				String line = br.readLine();
				this.nomFichierLicencie = line;
				br.close();
			} catch (Exception e) {
				try {
					Projet.fwLog = new FileWriter(new File("log.txt"), true);
					Projet.fwLog.write(e + "");
					Projet.fwLog.close();
				} catch (IOException e1) {}
			}
		}
		if (!param.exists() || !(new File(this.nomFichierLicencie).exists())) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("fichier excel", "xls"));

			// Dossier Courant
			chooser.setCurrentDirectory(new File("C:" + File.separator + "Users" + File.separator));

			// Si validation du fichier
			if (chooser.showDialog(chooser, "Ouvrir") == 0) {
				File f = chooser.getSelectedFile();
				String nom = f.getAbsolutePath();

				if (!nom.contains("."))
					this.nomFichierLicencie = nom + ".xls";
				else if (nom.endsWith(".xls"))
					this.nomFichierLicencie = nom;
				else {
					this.fp.dispose();
					System.exit(0);
				}
			} else {
				this.fp.dispose();
				System.exit(0);
			}

			File config = new File("./fichierConfiguration.txt");
			try {
				config.createNewFile();
				FileWriter fw = new FileWriter(config);
				fw.write(this.nomFichierLicencie);
				fw.close();
			} catch (IOException e) {
				try {
					Projet.fwLog = new FileWriter(new File("log.txt"), true);
					Projet.fwLog.write(e + "");
					Projet.fwLog.close();
				} catch (IOException e1) {}
			}
		}

		try {
			File fichierLicencies = new File(this.nomFichierLicencie);
			FileReader fr = new FileReader(fichierLicencies);

			BufferedReader br = new BufferedReader(fr);
			String line;
			br.readLine();
			// Récupérer les licenciés
			while ((line = br.readLine()) != null) {
				String[] carac = line.split("\t");
				FicheJeune fj = new FicheJeune(this);
				Jeune j = new Jeune(carac[0], carac[1], carac[3], carac[2],
						carac[4], fj, null);
				fj.setTitle(j.toString());
				fj.setLicencie(j);
				fj.invaliderModif();

				this.ajouterLicencie(j);

				this.fp.getDesktop().add(fj);
				fj.hide();
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			javax.swing.JOptionPane.showMessageDialog(this.getFp(),
					"Erreur dans l'ouverture du fichier des licenciés.");
			try {
				Projet.fwLog = new FileWriter(new File("log.txt"), true);
				Projet.fwLog.write(e + "");
				Projet.fwLog.close();
			} catch (IOException e1) {}
		}
		this.licenciesEnreg = true;
	}

	////////////////////////
	// GETTERS ET SETTERS //
	////////////////////////

	/**
	 * Récupérer le nombre de balises.
	 * @return  Le nombre de balises
	 */
	public int getNbBalise() {
		return this.nbBalise;
	}

	/**
	 * Modifier le nombre de balises.
	 * @param balise   Le nouveau nombre de balises
	 */
	public void setNbBalise(int balise) {
		this.nbBalise = balise;
	}

	/**
	 * Récupérer le nombre de mémos.
	 * @return Le nombre de mémos
	 */
	public int getNbMemo() {
		return this.nbMemo;
	}

	/**
	 * Modifier le nombre de mémos.
	 * @param mémo   Le nouveau nombre de mémo
	 */
	public void setNbMemo(int mémo) {
		this.nbMemo = mémo;
	}

	/**
	 * Récupérer la liste des licencies.
	 * @return La liste des licenciés
	 **/
	public ArrayList<Jeune> getLesLicencies() {
		return lesLicencies;
	}

	/**
	 * Récupérer la fenêtre principale.
	 * @return La fenêtre principale
	 **/
	public FenetrePrincipale getFp() {
		return fp;
	}

	/**
	 * Récupérer la liste des inscrits.
	 * @return La liste des inscrits
	 **/
	public ArrayList<Jeune> getLesInscrits() {
		return lesInscrits;
	}

	/**
	 * Récupérer la liste des points à gagner.
	 * @return La liste des points pour chaque épreuve
	 */
	public HashMap<String, Integer> getGains() {
		return gains;
	}

	/**
	 * Récupérer la liste des réponses.
	 * @return La liste des réponses pour chaque question
	 */
	public HashMap<String, String> getReponses() {
		return reponses;
	}

	/** 
	 * Récupérer la fiche des points.
	 * @return La fiche ds gains
	 */
	public FicheGains getFicheGains() {
		return ficheGains;
	}

	/**
	 * Récupérer la fiche des paramètres.
	 * @return La fiche des paramètres
	 */
	public FicheParam getFicheParametre() {
		return ficheParametre;
	}

	/**
	 * Récupérer la fiche des réponses.
	 * @return La fiche des réponses
	 */
	public FicheReponse getFicheReponse() {
		return ficheReponse;
	}

	/**
	 * Modifier les gains.
	 * @param gains Les nouveaux gains
	 */
	public void setGains(HashMap<String, Integer> gains) {
		this.gains = gains;
	}

	/**
	 * Modifier le bouléen conforme.
	 */
	public void setConforme(){
		this.conforme = !this.conforme;
	}

	/**
	 * Récupérer le bouléen conforme.
	 * @return Le bouléen conforme
	 */
	public Boolean getConforme() {
		return conforme;
	}

	/**
	 * Modifier le bouléen d'enregistrement des licenciés.
	 * @param enreg Le bouléen d'enregsitrement des licenciés
	 */
	public void setLicencieEnreg(Boolean enreg) {
		this.licenciesEnreg = enreg;
	}

	/**
	 * Récupérer le bouléen d'enregistrement des licenciés
	 * @return Le bouléen d'enregistrement des licenciés
	 */
	public Boolean getLicencieEnreg() {
		return this.licenciesEnreg;
	}

	/**
	 * Modifier le bouléen d'enregistrement du crit.
	 * @param enreg Le nouveau bouléen d'enregistrement du crit
	 */
	public void setCritEnreg(Boolean enreg) {
		this.critEnreg = enreg;
	}

	/**
	 * Récupérer le bouléen d'enregistrement du crit
	 * @return Le bouléen d'enregistrement du crit
	 */
	public Boolean getCritEnreg() {
		return this.critEnreg;
	}

	/**
	 * Modifier le nom du fichier des licenciés
	 * @param nomFichierLicencie Le nouveau nom du fichier des licenciés
	 */
	public void setNomFichierLicencie(String nomFichierLicencie) {
		this.nomFichierLicencie = nomFichierLicencie;
	}

	/**
	 * Récupérer le nom du fichier d'enregistrement des licenciés.
	 * @return Le nom du fichier d'enregistrement des licenciés
	 */
	public String getNomFichierLicencie() {
		return this.nomFichierLicencie;
	}

	/////////////////////////////////////////
	///AFFICHER LES FENETRES DE PARAMETRES///
	/////////////////////////////////////////

	/**
	 * Afficher la fiche pour définir les points gagnés.
	 */
	public void afficherFicheGains() {
		this.ficheGains.show();
	}

	/**
	 * Afficher la fiche des paramètres.
	 */
	public void afficherFicheParametre(){
		this.ficheParametre.show();
		this.ficheParametre.setNbBalises("" + this.nbBalise);
		this.ficheParametre.setNbMemos("" + this.nbMemo);
	}

	/**
	 * Afficher les fenêtres de sélection des inscrits.
	 */
	public void afficherFenetresSelection() {
		String couleur = "";

		// Pour chaque couleur
		for (int i = 0; i < 4; i++) {
			JPanel grille = new JPanel((new FlowLayout()));
			ArrayList<JCheckBox> list = new ArrayList<>();

			// Initialiser la couleur
			switch (i) {
			case 0:
				couleur = "Vert";
				break;
			case 1:
				couleur = "Bleu";
				break;
			case 2:
				couleur = "Rouge";
				break;
			case 3:
				couleur = "Noir";
				break;
			}

			// Créer la fenêtre interne
			JInternalFrame jif = new JInternalFrame(
					"Sélectionner les participants " + couleur, true, false, false);

			// Afficher tous les numéros de la couleur
			for (Jeune licencie : this.lesLicencies) {
				if (licencie.getCate().equals(couleur)) {
					JCheckBox btn = new JCheckBox(licencie.toString());
					// Cocher les cases des incrits
					if (lesInscrits.contains(licencie)) {
						btn.setSelected(true);
					}

					list.add(btn);
					grille.add(btn);
				}
			}

			// Ajouter les boutons
			JPanel btns = new JPanel(new FlowLayout());
			JButton btnValider = new JButton("Valider");
			btnValider.addActionListener(new ActionValiderSelection(this, list, jif));

			JButton btnAnnuler = new JButton("Annuler");
			btnAnnuler.addActionListener(new ActionAnnulerSelection(jif));

			btns.add(btnValider);
			btns.add(btnAnnuler);

			// Mettre en forme la fenêtre
			jif.getContentPane().setLayout(new BorderLayout());
			jif.getContentPane().add(grille, BorderLayout.CENTER);

			jif.getContentPane().add(btns, BorderLayout.SOUTH);

			// Afficher la fenêtre
			jif.setVisible(true);
			jif.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			jif.pack();

			this.fp.getDesktop().add(jif);
		}
	}

	/**
	 * Afficher la fenêtre de suppression des licenciés.
	 */
	public void afficherFenetreSuppression() {
		if (this.lesLicencies.size() > 0) {
			// Créer la fenêtre interne
			JInternalFrame jif = new JInternalFrame("Supprimer des licenciés");
			this.fp.getDesktop().add(jif);
			jif.getContentPane().setLayout(new BorderLayout());

			JPanel panel1 = new JPanel(new FlowLayout());
			JPanel panel2 = new JPanel(new FlowLayout());

			// Afficher les numéros de tous les licenciés
			ArrayList<JCheckBox> list = new ArrayList<>();
			for (Jeune licencie : lesLicencies) {
				JCheckBox btn = new JCheckBox(licencie.toString());
				panel1.add(btn);
				list.add(btn);
			}

			// Ajouter les boutons
			JButton btnValider = new JButton("Valdier");
			btnValider.addActionListener(new ActionValiderSuppr(list, this, jif));

			JButton btnAnnuler = new JButton("Annuler");
			btnAnnuler.addActionListener(new ActionAnnulerSuppr(jif));

			panel2.add(btnValider);
			panel2.add(btnAnnuler);

			// Mise en forme de la fenêtre
			jif.getContentPane().add(panel1, BorderLayout.CENTER);
			jif.getContentPane().add(panel2, BorderLayout.SOUTH);

			// Afficher la fenêtre
			jif.pack();
			jif.setVisible(true);
			jif.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		} else {
			JOptionPane.showMessageDialog(this.fp,
					"Aucun licencié n'est inscrit.",
					"Erreur",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Afficher la fiche des réponses attendues.
	 */
	public void afficherFenetreReponses() {
		if (!this.conforme) {
			this.ficheReponse = new FicheReponse(this);
			this.getFp().getDesktop().add(this.ficheReponse);
		}
		this.ficheReponse.setVisible(true);
	}

	//////////////////////////////
	///METTRE A JOUR LA FENETRE///
	//////////////////////////////

	/**
	 * Afficher la grille des licenciés
	 **/
	public void affichage() {
		/* Vider la grille des licenciés */
		this.fp.getGrilleLicencies().removeAll();

		/* afficher la grille des licenciés */
		for (Jeune licencie : this.lesLicencies) {
			if (licencie.getCate().equals("Vert")) {
				JButton btn = new JButton(licencie.toString());
				btn.addActionListener(new ActionModifierFiche(licencie));
				btn.setBackground(new Color(14, 106, 0));
				btn.setForeground(new Color(255, 255, 255));
				this.fp.getGrilleLicencies().add(btn);
			}
		}

		for (Jeune licencie : this.lesLicencies) {
			if (licencie.getCate().equals("Bleu")) {
				JButton btn = new JButton(licencie.toString());
				btn.addActionListener(new ActionModifierFiche(licencie));
				btn.setBackground(new Color(0, 0, 110));
				btn.setForeground(new Color(255, 255, 255));
				this.fp.getGrilleLicencies().add(btn);
			}
		}

		for (Jeune licencie : this.lesLicencies) {
			if (licencie.getCate().equals("Rouge")) {
				JButton btn = new JButton(licencie.toString());
				btn.addActionListener(new ActionModifierFiche(licencie));
				btn.setBackground(new Color(175, 0, 0));
				btn.setForeground(new Color(255, 255, 255));
				this.fp.getGrilleLicencies().add(btn);
			}
		}

		for (Jeune licencie : this.lesLicencies) {
			if (licencie.getCate().equals("Noir")){
				JButton btn = new JButton(licencie.toString());
				btn.addActionListener(new ActionModifierFiche(licencie));
				btn.setBackground(new Color(0, 0, 0));
				btn.setForeground(new Color(255, 255, 255));
				this.fp.getGrilleLicencies().add(btn);
			}
		}
	}

	/**
	 * Afficher la grille des sélectionnés
	 * @param La couleur séletionnée à afficher
	 **/
	public void affichageSelectionnes(String couleur) {
		if (this.nomFichierCrit == null) {
			javax.swing.JOptionPane.showMessageDialog(this.fp,
					"Impossible d'afficher les sélectionés car il n'y a pas de critérium chargé.");
			return;
		}
		// Afficher la grille et le bouton d'enregistrement
		this.getFp().getBtnsCrit().setVisible(true);
		this.getFp().getGrilleInscrits().setVisible(false);

		this.fp.getGrilleLicencies().setVisible(false);

		/* Vider la grille dse sélectionnés */
		this.fp.getGrilleInscrits().removeAll();

		/* afficher la grille des sélectionnés */
		for (Jeune licencie : this.getLesInscrits()) {
			if (licencie.getCate().equals(couleur)) {
				JButton btn = new JButton(licencie.toString());
				btn.addActionListener(new ActionModifierScore(licencie, this));

				switch(couleur) {
				case "Vert" : 
					btn.setBackground(Color.GREEN);
					break;
				case "Bleu" : 
					btn.setBackground(Color.BLUE);
					break;
				case "Rouge" : 
					btn.setBackground(Color.RED);
					break;
				case "Noir" : 
					btn.setBackground(Color.BLACK);
					btn.setForeground(Color.WHITE);
					break;
				}

				this.getFp().getGrilleInscrits().add(btn);
			}
		}
		this.getFp().getGrilleInscrits().setVisible(true);

		// Afficher les barres de progression
		this.fp.getZoneProg().setVisible(true);
		this.majBarreBalise(couleur);
		this.majBarreMemo(couleur);

		this.getFp().repaint();
	}

	///////////////////////////////////////
	///GESTION DES LICENCIES ET INSCRITS///
	///////////////////////////////////////

	/**
	 * Ajouter un licencie à la liste triée.
	 * @param Le licencié que l'on ajoute 
	 **/
	public void ajouterLicencie(Jeune licencie) {
		// Si c'est le permier, pas besoin de trier
		if (this.lesLicencies.size() == 0) {
			this.lesLicencies.add(licencie);
		} else {
			int i;
			for (i = 0; i < this.lesLicencies.size(); i++) {
				// Chercher l'emplacement
				if (licencie.inferieur(this.lesLicencies.get(i))) {
					// Faire une place
					this.decaler(this.lesLicencies, i);
					// Ajouter
					this.lesLicencies.set(i, licencie);
					break;
				}
			}
			// Ou l'ajouter à la fin
			if (i == this.lesLicencies.size()) {
				this.lesLicencies.add(licencie);
			}
		}
		this.licenciesEnreg = false;
	}

	/**
	 * Ajouter un sélectioné à la liste des inscrits.
	 * @param Le jeune à ajouter à la liste
	 **/
	public void ajouterInscrits(Jeune licencie) {
		if (!this.lesInscrits.contains(licencie))
			this.lesInscrits.add(licencie);
	}

	/**
	 * Ajouter un sélectionné par son numéro.
	 * @param  Le numéro du jeune à ajouter
	 **/
	public void ajouterInscrits(String numero) {
		if (!this.lesInscrits.contains(this.recupJeune(numero)))
			this.lesInscrits.add(this.recupJeune(numero));
	}

	/**
	 * Supprimer un licencié.
	 * @param numero   Le numéro du jeune à supprimer
	 **/
	public void supprimerLicencie(String numero) {
		for (Jeune licencie : this.lesLicencies) {
			if (licencie.getNumero().equals(numero)) {
				this.lesLicencies.remove(licencie);
				break;
			}
		}
	}

	/**
	 * Récupérer un jeune grâce à son numéro.
	 * @param numero   Le numéro du jeune à récupérer
	 * @return   Le jeune correspondant au numéro
	 **/
	public Jeune recupJeune(String numero) {
		Jeune res = null;
		for (Jeune licencie : this.lesLicencies) {
			if (licencie.toString().equals(numero)) {
				res = licencie;
				break;
			}
		}
		
		if (res == null) {
			try {
				Projet.fwLog = new FileWriter(new File("log.txt"), true);
				Projet.fwLog.write("Impossible de trouver le jeune dont le numéro est : " + numero);
				Projet.fwLog.close();
			} catch (IOException e) {}
		}
		return res;
	}

	/**
	 * Valider la suppression des licenciés.
	 * @param list   La liste des boutons 
	 * @param jif    La fiche de suppression d'une couleur donnée
	 */
	public void validerSuppression(ArrayList<JCheckBox> list, JInternalFrame jif) {
		for (JCheckBox checkBox : list) {
			if (checkBox.isSelected()) {
				this.supprimerLicencie(checkBox.getText());
				this.licenciesEnreg = false;
			}
		}
		jif.dispose();
		this.affichage();
	}

	/**
	 * Enregistrer la sélection des inscrits.
	 * @param list  La liste des boutons
	 * @param jif   La fiche de sélection d'une couleur donnée
	 */
	public void validerSelection(ArrayList<JCheckBox> list, JInternalFrame jif) {
		for (JCheckBox btn : list) {
			if (btn.isSelected()) {
				// Signaler une modification
				this.critEnreg = false;
				// Ajouter les inscrits à la liste
				this.ajouterInscrits(btn.getText());
				// Créer une ficher score
				FicheScore fs = new FicheScore(this.fp);
				// Associer la fiche au jeune
				Jeune j = this.recupJeune(btn.getText());
				j.setFiche2(fs);
				fs.setLicencie(j);
			}
		}
		jif.dispose();
	}

	////////////////////
	///ENREGSITREMENT///
	////////////////////

	/** 
	 * Enregistrer l'ensemble des licenciées dans un fichier excel.
	 */
	public void enregistrerLicencies() {
		try {
			File fichierLicencies = new File(this.nomFichierLicencie);
			// Enregistrement des résutats
			fichierLicencies.createNewFile();
			FileWriter fw = new FileWriter(fichierLicencies);

			fw.write("Nom\tPrenom\tNumero\tClub\tCate\n");

			for (Jeune j : this.getLesLicencies()) {
				fw.write(j.toStringEnreg());
			}

			fw.close();
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(this.getFp(),
					"Erreur dans d'enregistrement des licenciés, " +
					"fermer le fichier d'il est ouvert et recommencer");
			try {
				Projet.fwLog = new FileWriter(new File("log.txt"), true);
				Projet.fwLog.write(e + "");
				Projet.fwLog.close();
			} catch (IOException e1) {}
			return;
		}
		this.licenciesEnreg = true;
		
		int ecart = 0;
		if (this.fp.getAdmin().isVisible())
			ecart = 250;
		else 
			ecart = 10;
		Confirmation charg = new Confirmation("Enregistrement réussi", this.fp, ecart);
		Thread thread = new Thread(charg);
		thread.start();
	}

	/** 
	 * Enregistrer l'ensemble des licenciées dans un fichier excel dans un nouveau fichier.
	 */
	public void enregistrerSousLicencies() {
		JFileChooser chooser = new JFileChooser();

		// Dossier Courant
		chooser.setCurrentDirectory(new File("." + File.separator));

		if (chooser.showDialog(chooser, "Ouvrir") == 0) {
			// Récupérer le fichier d'enregistrement
			File fichier = chooser.getSelectedFile();

			String nom = fichier.getAbsolutePath();

			if (!nom.contains(".")) {
				this.nomFichierLicencie = nom + ".xls";
				this.enregistrerLicencies();
			} else if (nom.endsWith(".xls")){
				this.nomFichierLicencie = nom;
				this.enregistrerLicencies();
			} else {
				javax.swing.JOptionPane.showMessageDialog(this.fp, 
						"Veuillez sélectionner un fichier excel (extension .xls).", 
						"Erreur",
						JOptionPane.ERROR_MESSAGE
						);
				this.enregistrerSousLicencies();
			}
		}

		File config = new File("./fichierConfiguration.txt");
		try {
			config.createNewFile();
			FileWriter fw = new FileWriter(config);
			fw.write(this.nomFichierLicencie);
			fw.close();
		} catch (IOException e) {
			try {
				Projet.fwLog = new FileWriter(new File("log.txt"), true);
				Projet.fwLog.write(e + "");
				Projet.fwLog.close();
			} catch (IOException e1) {}
		}
	}

	/**
	 * Enregistrer les inscrits; les scores et les paramètres dans deux fichiers.
	 */
	public void enregistrerSousCrit() {
		JFileChooser chooser = new JFileChooser();

		// Dossier Courant
		chooser.setCurrentDirectory(new File("." + File.separator));

		if (chooser.showDialog(chooser, "Enregistrer le critérium") == 0) {
			// Récupérer le fichier d'enregistrement
			File fichierSelectionnes = chooser.getSelectedFile();

			String nom = fichierSelectionnes.getAbsolutePath();

			if (!nom.contains(".")) {
				this.nomFichierCrit = nom;
				this.enregistrerCrit();
			}
			else if (nom.endsWith("xls")){
				this.nomFichierCrit = nom.substring(0, nom.length() - 4);
				this.enregistrerCrit();
			} else {
				javax.swing.JOptionPane.showMessageDialog(this.getFp(), 
						"Veuillez sélectionner un fichier excel (extension .xls).", 
						"Erreur",
						JOptionPane.ERROR_MESSAGE
						);
				this.enregistrerSousCrit();
			}
		}
	}

	/**
	 * Enregistrer le critérium avec le nom de fichier en attribut.
	 */
	public void enregistrerCrit() {
		if (this.nomFichierCrit == null) {
			this.enregistrerSousCrit();
		} else {
			File fichier = new File(this.nomFichierCrit + ".xls");
			try {
				fichier.delete();
				fichier.createNewFile();
				FileWriter fw = new FileWriter(fichier);
				// Ecrire la ligne d'en-tête
				fw.write("numero");
				for (int i = 1 ; i <= this.nbMemo ; i++)
					fw.write("\tmemo " + i);
				for (int i = 1 ; i <= this.nbBalise ; i++)
					fw.write("\tbalise " + i);
				for (int i = 1 ; i <= this.nbBalise ; i++)
					fw.write("\tzone maniabilité "	+ i);
				fw.write("\n");

				// Pour tous les inscrits
				for (Jeune j : this.getLesInscrits()) {

					// ecrire le numéro
					fw.write(String.valueOf(j.toString()) + "\t");

					// ecrire les réponses aux mémos 
					for (int i = 0 ; i < this.nbMemo ; i++) {
						if (j.getLesReponses().get("memo" + i) != null){
							fw.write(j.getLesReponses().get("memo" + i));
						} else {
							fw.write("XX");
						}
						fw.write("\t");
					}

					// Ecrire les réponses aux balises
					for (int i = 0 ; i < this.nbBalise ; i++) {
						if (j.getLesReponses().get("balise" + i) != null) {
							fw.write(j.getLesReponses().get("balise" + i));
						} else {
							fw.write("XX");
						}
						fw.write("\t");
					}

					// Ecrire les résultats de maniabilité
					for (int i = 0 ; i < this.nbBalise ; i++) {
						if (j.getLesReponses().get("maniabilite" + i) != null) {
							fw.write(j.getLesReponses().get("maniabilite" + i));
						} else {
							fw.write("XX");
						}
						fw.write("\t");
					}
					fw.write("\n");
				}

				fw.close();

			} catch (Exception e) {
				javax.swing.JOptionPane.showMessageDialog(this.fp,
						"Erreur dans l'enregistrement des scores.");
				try {
					Projet.fwLog = new FileWriter(new File("log.txt"), true);
					Projet.fwLog.write(e + "");
					Projet.fwLog.close();
				} catch (IOException e1) {}
				return;
			}

			// Ecriture des paramètres
			File fichierParam = new File(this.nomFichierCrit + ".txt");
			try {
				FileWriter fw = new FileWriter(fichierParam);

				// Ecrire le nombre de balise
				fw.write("nombre de balise : ");
				fw.write("" + this.nbBalise);
				fw.write("\n \n");

				// Ecrire le nombre de mémo
				fw.write("nombre de mémo : ");
				fw.write("" + this.nbMemo);
				fw.write("\n \n");

				// Ecrire les points à gagner
				fw.write("nombre de points gagnés : ");
				fw.write("\n");

				// Ecrire les points par balise trouvée
				fw.write("\tBalise trouvée : ");
				fw.write("" + gains.get("baliseTrouvee"));
				fw.write("\n");

				// Ecrire les points par réponse correcte aux balises
				fw.write("\tRéponse balise correcte : ");
				fw.write("" + gains.get("baliseCorrecte"));
				fw.write("\n");

				// Ecrire les points par mémo trouvé 
				fw.write("\tMémo trouvé : ");
				fw.write("" + gains.get("memoTrouve"));
				fw.write("\n");

				// Ecrire les points par réponse correcte aux mémos
				fw.write("\tRéponse mémo correcte : ");
				fw.write("" + gains.get("memoCorrect"));
				fw.write("\n");

				// Ecrire les points par zone de maniabilité
				fw.write("\tZone de maniabilité réussie : ");
				fw.write("" + gains.get("maniabilite"));
				fw.write("\n \n");

				// Ecrire les réponses attendues pour chaque question
				fw.write("Réponses aux questions : \n");

				// Ecrire les réponses aux mémos
				for (int i = 0 ; i < nbMemo ; i++) {
					fw.write("\tMemo " + i + " : ");
					fw.write("" + this.reponses.get("memo" + i));
					fw.write("\n");
				}

				// Ecrire les réponses aux balises
				for (int i = 0 ; i < nbBalise ; i++) {
					fw.write("\tBalise " + i + " : ");
					fw.write("" + this.reponses.get("balise" + i));
					fw.write("\n");
				}

				fw.close();

				this.critEnreg = true;
			} catch (IOException e) {
				javax.swing.JOptionPane.showMessageDialog(this.fp,
						"Erreur dans l'enregistrement des paramètres");
				try {
					Projet.fwLog = new FileWriter(new File("log.txt"), true);
					Projet.fwLog.write(e + "");
					Projet.fwLog.close();
				} catch (IOException e1) {}
				return;
			}		
		}

		int ecart = 0;
		if (this.fp.getAdmin().isVisible())
			ecart = 250;
		else 
			ecart = 10;
		Confirmation charg = new Confirmation("Enregistrement réussi", this.fp, ecart);
		Thread thread = new Thread(charg);
		thread.start();
	}

	////////////////
	///CLASSEMENT///
	////////////////

	/**
	 * Faire le classement des jeunes et l'afficher.
	 */
	public void classement() {
		if (this.nomFichierCrit == null) {
			javax.swing.JOptionPane.showMessageDialog(this.fp,
					"Impossible de calculer le classement car il n'y a pas de critérium chargé.");
			return;
		}
		// Calculer le score de chaque inscrit
		for (Jeune j : this.lesInscrits) {
			j.modifierPoints(this.reponses, this.nbBalise, this.nbMemo,
					this.gains, this.fp);
		}

		// Trier les isncrits par score décroissant
		classement = new ArrayList<>();
		for (Jeune j : this.lesInscrits) {
			int place = 0;
			for (int i = 0 ; i < classement.size() ; i++) {
				Jeune jj = classement.get(i);
				if (j.getPoints() < jj.getPoints()) {
					place++;
				} else {
					break;
				}
			}
			if (classement.size() == place) {
				classement.add(j);
			} else {
				this.decaler(classement, place);
				classement.set(place, j);
			}
		}

		// Afficher les classements de chaque catégories et le classement général
		FicheClassement fcVert = new FicheClassement();
		fcVert.afficherCouleur(classement, "Vert"); 

		FicheClassement fcBleu = new FicheClassement();
		fcBleu.afficherCouleur(classement, "Bleu");

		FicheClassement fcRouge = new FicheClassement();
		fcRouge.afficherCouleur(classement, "Rouge");

		FicheClassement fcNoir = new FicheClassement();
		fcNoir.afficherCouleur(classement, "Noir");

		this.fp.getDesktop().add(fcNoir);
		this.fp.getDesktop().add(fcRouge);
		this.fp.getDesktop().add(fcBleu);
		this.fp.getDesktop().add(fcVert);
	}

	/**
	 * Exporter le classement d'un critérium.
	 */
	public void exporterClassement() {
		if (this.nomFichierCrit == null) {
			javax.swing.JOptionPane.showMessageDialog(this.fp,
					"Impossible d'exporter un classement car il n'y a pas de critérium chargé.");
		} else {
			exporterClassement("Vert");
			exporterClassement("Bleu");
			exporterClassement("Rouge");
			exporterClassement("Noir");
		}
	}

	/**
	 * Remplir un fichier de classement pour une couleur donnée.
	 * @param couleur La couleur de la catégorie à écrire dans le fihcier
	 */
	public void exporterClassement(String couleur) {
		// Créer le fichier
		String[] dossiers = this.nomFichierCrit.split("\\\\");
		String path = this.nomFichierCrit.substring(0, this.nomFichierCrit.length() - dossiers[dossiers.length - 1].length());
		String nomFichier = dossiers[dossiers.length - 1];
		File f = new File(path + "Classement_" + couleur + "_" + nomFichier + ".xls");
		try {
			f.createNewFile();

			// Créer un objet FileWriter pour écrire dans le fichier
			FileWriter fw = new FileWriter(f);

			int i = 0;
			int k = 0;
			int nbPoints = 0;
			fw.write("classement\tnom\tprenom\tclub\tpoints\n");
			// Remplir le fichier avec les jeunes de la couleur passée en paramèters
			for (Jeune j : classement) {
				if (j.getCate().equals(couleur)) {
					i++;
					if (nbPoints != j.getPoints())
						k = i;
					nbPoints = j.getPoints();
					fw.write(k + "\t" + j.getNom() + "\t" + j.getPrenom()
					+ "\t" + j.getClub() + "\t" + j.getPoints() + "\n");
				}
			}
			fw.close();
		} catch (IOException e) {
			try {
				Projet.fwLog = new FileWriter(new File("log.txt"), true);
				Projet.fwLog.write(e + "");
				Projet.fwLog.close();
			} catch (IOException e1) {}
		}
	}

	////////////////
	///CHARGEMENT///
	////////////////

	/**
	 * Charger les inscrits à un critérium.
	 * @param nomFichierInscrtis   Le nom du fichier duquel on charge les inscrits
	 **/
	public void chargerCrit(String nomFichierInscrtis) {
		// Récupérer le fichier
		File fichierInscrits = new File(nomFichierInscrtis);

		this.nomFichierCrit = fichierInscrits.getAbsolutePath();
		int taille = this.nomFichierCrit.length();
		this.nomFichierCrit = this.nomFichierCrit.substring(0, taille - 4);

		// Modifier le nom de la fenêtre
		int tailleNom = fichierInscrits.getName().length() - 4;
		this.fp.setTitle(fichierInscrits.getName().substring(0,  tailleNom));

		// Mettre à jour les paramètres du critérium
		File fichierParam = new File(this.nomFichierCrit + ".txt");
		if (fichierParam.exists()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(fichierParam));

				// Récupérer le nombre de balises
				String line = br.readLine();
				String[] mots = line.split(" ");
				this.nbBalise = Integer.parseInt(mots[mots.length - 1]);

				// Récupérer le nombre de mémo
				br.readLine();
				line = br.readLine();
				mots = line.split(" ");
				this.nbMemo = Integer.parseInt(mots[mots.length - 1]);

				// Récupérer les points pour une balise trouvée
				br.readLine();
				br.readLine();
				line = br.readLine();
				mots = line.split(" ");
				this.gains.put("baliseTrouvee", Integer.parseInt(mots[mots.length - 1]));

				// Récupérer les points pour une réponse correcte à une balise
				line = br.readLine();
				mots = line.split(" ");
				this.gains.put("baliseCorrecte", Integer.parseInt(mots[mots.length - 1]));

				// Récupérer les points pour un mémo trouvé
				line = br.readLine();
				mots = line.split(" ");
				this.gains.put("memoTrouve", Integer.parseInt(mots[mots.length - 1]));

				// Récupérer les points pour une question correcte à un mémo
				line = br.readLine();
				mots = line.split(" ");
				this.gains.put("memoCorrect", Integer.parseInt(mots[mots.length - 1]));

				// Récupérer les points pour une zone de manabilité réussie
				line = br.readLine();
				mots = line.split(" ");
				this.gains.put("maniabilite", Integer.parseInt(mots[mots.length - 1]));

				// Récupérer les réponses attendues
				br.readLine();
				br.readLine();
				// Récupérer les réponses aux mémos orientations
				for (int i = 0 ; i < nbMemo ; i++) {
					line = br.readLine();
					mots = line.split(" ");
					this.reponses.put("memo" + i, mots[mots.length - 1]);
				}				

				// Récupérer les réponses aux balises
				for (int i = 0 ; i < nbBalise ; i++) {
					line = br.readLine();
					mots = line.split(" ");
					this.reponses.put("balise" + i, mots[mots.length - 1]);
				}	

				// Créer des fiches avec les bons nombre de mémo / balises
				this.ficheReponse = new FicheReponse(this);
				this.ficheGains = new FicheGains(this);

				this.fp.getDesktop().add(this.ficheReponse);
				this.fp.getDesktop().add(this.ficheGains);

				// Remplir les fiches de points et de réponses
				this.ficheReponse.invaliderModif();

				this.ficheGains.invaliderModif();
			} catch (IOException | NumberFormatException e) {
				try {
					Projet.fwLog = new FileWriter(new File("log.txt"), true);
					Projet.fwLog.write(e + "");
					Projet.fwLog.close();
				} catch (IOException e1) {}
			}
		}

		if (fichierInscrits.exists()) {
			try {
				FileReader fr = new FileReader(fichierInscrits);

				BufferedReader br = new BufferedReader(fr);
				br.readLine();
				String line;

				// Lire chaque ligne et créer un jeune
				while ((line = br.readLine()) != null) {
					// Créer une fiche score
					FicheScore fs = new FicheScore(this.getFp());

					// Récupérer les données enrgistrées
					String[] donnes = line.split("\t");

					// Récupérer le jeune lu
					Jeune j = this.recupJeune(donnes[0]);
					
					if (j == null) {
						javax.swing.JOptionPane.showMessageDialog(this.getFp(),
								"Le fichier sélectionné n'est pas valide.");
						return;
					}

					// Mettre à jour les attributs du jeune
					j.setScore(donnes, this.getNbBalise(), this.getNbMemo());
					j.setFiche2(fs);
					fs.setTitle(j.toString());
					fs.setLicencie(j);
					fs.invaliderModif();

					// Ajouter le jeune à la liste des isncrits
					this.ajouterInscrits(j);
				}

				// Fermer le br
				br.close();
				
				// Mettre à jour le nombre des jeunes sur chaque circuit
				for (Jeune j : this.lesInscrits) {
					Boolean finiMemo = true;
					Boolean baliseFinie = true;

					for (int i = 0; i < this.nbMemo ; i++) {
						if (j.getLesReponses().get("memo" + i).equals("XX")) {
							finiMemo = false;
							break;
						}
					}

					if (finiMemo) {
						this.fp.getProjet().arriveMemo(j);
					}

					for (int i = 0; i < this.nbBalise ; i++) {
						if (j.getLesReponses().get("balise" + i).equals("XX")) {
							baliseFinie = false;
							break;
						}
					}

					if (baliseFinie) {
						this.fp.getProjet().arriveBalise(j);
					}
				}
				br.close();
				fr.close();
			} catch (IOException e) {
				javax.swing.JOptionPane.showMessageDialog(this.getFp(),
						"Erreur dans la lecture du fichier d'enregistrement");
				try {
					Projet.fwLog = new FileWriter(new File("log.txt"), true);
					Projet.fwLog.write(e + "");
					Projet.fwLog.close();
				} catch (IOException e1) {}
			}
		}

		this.barreMemo.setMaximum(this.lesInscrits.size());
		this.barreBalise.setMaximum(this.lesInscrits.size());
		

		int ecart = 0;
		if (this.fp.getAdmin().isVisible()) 
			ecart = 250;
		else 
			ecart = 10;
		Confirmation charg = new Confirmation("Chargement réussi", this.fp, ecart);
		Thread thread = new Thread(charg);
		thread.start();
	}

	/////////////////////////////////////
	///GERER LES BARRES DE PROGRESSION///
	/////////////////////////////////////

	/** 
	 * Signaler qu'un jeune est revenu des mémos.
	 * @param j Le jeune qui est revenu des mémos
	 */
	public void arriveMemo(Jeune j) {
		if (!this.revenuMemo.contains(j))
			this.revenuMemo.add(j);
		majBarreMemo(j.getCate());
	}

	/**
	 * Mettre à jour la barre de progression des mémos.
	 * @param couleur La couleur de la barre à mettre à jour
	 */
	public void majBarreMemo(String couleur) {
		// Calculer le nombre de jeunes dans cette catégorie
		int nb = 0;
		for (Jeune jj : revenuMemo)
			if (jj.getCate().equals(couleur))
				nb++;

		// Calculer le nombre total de jeunes inscrits dans cette catégorie
		int total = 0;
		for (Jeune jj : lesInscrits)
			if (jj.getCate().equals(couleur))
				total++;

		// Mettre à jour la barre de progression et le label
		this.barreMemo.setValue(nb);
		this.nombreArriveMemo.setText(nb  + "/" + total);
		this.barreMemo.setMaximum(total);
		this.fp.repaint();
	}

	/**
	 * Signaler qu'un jeune est revenu des balises.
	 * @param j Le jeune qui est revenu des balises
	 */
	public void arriveBalise(Jeune j) {
		if (!this.revenuBalises.contains(j))
			this.revenuBalises.add(j);
		majBarreBalise(j.getCate());
	}

	/**
	 * Mettre à jour la barre de progression.
	 * @param couleur La couleur de la barre de progression
	 */
	public void majBarreBalise(String couleur) {
		// Calculer le nombre de jeunes dans cette catégorie
		int nb = 0;
		for (Jeune jj : revenuBalises)
			if (jj.getCate().equals(couleur))
				nb++;

		// Calculer le nombre total de jeunes inscrits dans cette catégorie
		int total = 0;
		for (Jeune jj : lesInscrits)
			if (jj.getCate().equals(couleur))
				total++;

		// Mettre à jour la barre de progression et le label
		this.barreBalise.setValue(nb);
		this.nombreArriveBalise.setText(nb  + "/" + total);
		this.barreBalise.setMaximum(total);
		this.fp.repaint();
	}

	//////////////////////
	///METHODES PRIVEES///
	//////////////////////

	/**
	 * Décaler les éléments d'une liste.
	 * @param liste    La liste
	 * @param indice   L'indice du premier élément à décaler
	 */
	private void decaler(ArrayList<Jeune> liste, int indice) {
		liste.add(liste.get(liste.size() - 1));
		for (int i = liste.size() - 2; i > indice; i--) {
			liste.set(i, liste.get(i - 1));
		}
	}
}

