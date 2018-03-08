package Modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

		// Gérer le sud de la fenêtre : les barres de progression
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

		File param = new File(".fichierConfiguration.txt");
		if (param.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(param));
				String line = br.readLine();
				this.nomFichierLicencie = line;
				br.close();
			} catch (Exception e) {}
		}
		if (!param.exists() || !(new File(this.nomFichierLicencie).exists())) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("fichier excel", "xls", "xlsx", "xlsm"));

			// Dossier Courant
			chooser.setCurrentDirectory(new File("C:" + File.separator + "Users" + File.separator));

			// Si validation du fichier
			if (chooser.showDialog(chooser, "Ouvrir") == 0) {
				File f = chooser.getSelectedFile();
				String nom = f.getAbsolutePath();

				if (!nom.contains("."))
					this.nomFichierLicencie = nom + ".xls";
				else if (nom.endsWith(".xlsm"))
					this.nomFichierLicencie = nom;
				else {
					this.fp.dispose();
					System.exit(0);
				}
			} else {
				this.fp.dispose();
				System.exit(0);
			}

			File config = new File(".fichierConfiguration.txt");
			try {
				config.createNewFile();
				FileWriter fw = new FileWriter(config);
				fw.write(this.nomFichierLicencie);
				fw.close();
			} catch (IOException e) {}
		}

		Workbook wb ;
		
		try {
			// Créer le fichier et la feuille de calcul
			wb = WorkbookFactory.create(new File("08035.xlsm"));

			Sheet sheet = wb.getSheet("Inscriptions");

			int index = 4;
			Row row = sheet.getRow(index++);

			while (row.getCell(0).getStringCellValue() != "") {

				String lettre = row.getCell(0).getStringCellValue();
				int numero = (int) row.getCell(1).getNumericCellValue();
				int equipe = (int) row.getCell(3).getNumericCellValue();
				String nom = row.getCell(4).getStringCellValue();
				String prenom = row.getCell(5).getStringCellValue();
				int licence = (int) row.getCell(6).getNumericCellValue();
				int nomClub = (int) row.getCell(7).getNumericCellValue();
				String club = row.getCell(8).getStringCellValue();
				int naissance = (int) row.getCell(9).getNumericCellValue();
				int age = (int) row.getCell(10).getNumericCellValue();
				String categorie = row.getCell(11).getStringCellValue();
				String niveau = row.getCell(12).getStringCellValue();
				char sexe = row.getCell(13).getStringCellValue().charAt(0);
				row = sheet.getRow(index++);

				FicheJeune fj = new FicheJeune(this);

				Jeune j = new Jeune(nom, prenom, recupClub(lettre), numero,
						convertir(naissance), licence, niveau, sexe, fj, null);
				fj.setTitle(j.toString());
				fj.setLicencie(j);
				fj.invaliderModif();
				
				// Initialiser les réponses 
				j.initialiserReponses(this.nbBalise, this.nbMemo);
				
				this.ajouterLicencie(j);

				this.fp.getDesktop().add(fj);
				fj.hide();
			}

		} catch (Exception e) {
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(this.getFp(),
					"Erreur dans l'ouverture du fichier des licenciés.");
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
				couleur = "";
				break;
			case 1:
				couleur = "CHEMIN / MARCASSINS";
				break;
			case 2:
				couleur = "PISTE / RENARDS";
				break;
			case 3:
				couleur = "SENTIER / COYOTES";
				break;
			}

			// Créer la fenêtre interne
			JInternalFrame jif = new JInternalFrame(
					"Sélectionner les participants " + couleur, true, false, false);

			// Afficher tous les numéros de la couleur
			for (Jeune licencie : this.lesLicencies) {
				if (licencie.getNiveau().equals(couleur)) {
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
			if (licencie.getNiveau().equals("")) {
				JButton btn = new JButton(licencie.toString());
				btn.addActionListener(new ActionModifierFiche(licencie));
				btn.setBackground(new Color(14, 106, 0));
				btn.setForeground(new Color(255, 255, 255));
				this.fp.getGrilleLicencies().add(btn);
			}
		}

		for (Jeune licencie : this.lesLicencies) {
			if (licencie.getNiveau().equals("CHEMIN / MARCASSINS")) {
				JButton btn = new JButton(licencie.toString());
				btn.addActionListener(new ActionModifierFiche(licencie));
				btn.setBackground(new Color(0, 0, 110));
				btn.setForeground(new Color(255, 255, 255));
				this.fp.getGrilleLicencies().add(btn);
			}
		}

		for (Jeune licencie : this.lesLicencies) {
			if (licencie.getNiveau().equals("PISTE / RENARDS")) {
				JButton btn = new JButton(licencie.toString());
				btn.addActionListener(new ActionModifierFiche(licencie));
				btn.setBackground(new Color(175, 0, 0));
				btn.setForeground(new Color(255, 255, 255));
				this.fp.getGrilleLicencies().add(btn);
			}
		}

		for (Jeune licencie : this.lesLicencies) {
			if (licencie.getNiveau().equals("SENTIER / COYOTES")){
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
		ArrayList<Jeune> inscrits = new ArrayList<Jeune>();
		for (Jeune licencie : this.getLesInscrits()) {
			if (licencie.getNiveau().toString().equals(couleur)) {
				inscrits.add(licencie);
			}
		}

		if (inscrits.isEmpty()) {
			javax.swing.JOptionPane.showMessageDialog(this.fp,
					"Aucun inscrit dans cette catégorie.");
			return;
		}

		// Afficher la grille et le bouton d'enregistrement
		this.getFp().getBtnsCrit().setVisible(true);
		this.getFp().getGrilleInscrits().setVisible(false);

		this.fp.getGrilleLicencies().setVisible(false);

		/* Vider la grille dse sélectionnés */
		this.fp.getGrilleInscrits().removeAll();

		/* afficher la grille des sélectionnés */
		for (Jeune licencie : inscrits) {
			JButton btn = new JButton(licencie.toString());
			btn.addActionListener(new ActionModifierScore(licencie, this));

			switch(couleur) {
			case "" : 
				btn.setBackground(Color.GREEN);
				break;
			case "CHEMIN / MARCASSINS" : 
				btn.setBackground(Color.BLUE);
				break;
			case "PISTE / RENARDS" : 
				btn.setBackground(Color.RED);
				break;
			case "SENTIER / COYOTES" : 
				btn.setBackground(Color.BLACK);
				btn.setForeground(Color.WHITE);
				break;
			}

			this.getFp().getGrilleInscrits().add(btn);
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
		if (!this.lesInscrits.contains(this.recupJeune(numero))) {
			this.lesInscrits.add(this.recupJeune(numero));
		}
	}

	/**
	 * Supprimer un licencié.
	 * @param numero   Le numéro du jeune à supprimer
	 **/
	public void supprimerLicencie(int numero) {
		for (Jeune licencie : this.lesLicencies) {
			if (licencie.getNumero() == numero) {
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
				this.supprimerLicencie(Integer.parseInt(checkBox.getText()));
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
		// Création du wokrbook
		XSSFWorkbook wb = new XSSFWorkbook();

		// Création de la feuille
		Sheet sheet = wb.createSheet("Feuil1");

		// Ecrire les en-têtes
		Row row = sheet.createRow(0);

		row.createCell(0);
		row.getCell(0).setCellValue("Lettre");
		row.createCell(1);
		row.getCell(1).setCellValue("N°");
		row.createCell(2);
		row.getCell(2).setCellValue("/");
		row.createCell(3);
		row.getCell(3).setCellValue("N° Equipe");
		row.createCell(4);
		row.getCell(4).setCellValue("Nom");
		row.createCell(5);
		row.getCell(5).setCellValue("Prénom");
		row.createCell(6);
		row.getCell(6).setCellValue("N° Licence");
		row.createCell(7);
		row.getCell(7).setCellValue("N° Club");
		row.createCell(8);
		row.getCell(8).setCellValue("Nom Club");
		row.createCell(9);
		row.getCell(9).setCellValue("Date Naissance");
		row.createCell(10);
		row.getCell(10).setCellValue("Age");
		row.createCell(11);
		row.getCell(11).setCellValue("Catégorie");
		row.createCell(12);
		row.getCell(12).setCellValue("Niveau");
		row.createCell(13);
		row.getCell(13).setCellValue("Sexe");

		// Pour itérer sur les lignes
		int ligne = 1;
		int colonne = 0;
		// Ecrire pour chaque jeune, une ligne dans le fichier
		for (Jeune j : this.getLesLicencies()) {

			// Réinitialiser le numéro de la colonne
			colonne = 0;

			// Créer la ligne
			row = sheet.createRow(ligne++);

			// Ecrire la lettre du club
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue(j.getClub().toString());

			// Ecrire le numéro du jeune
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue(j.getNumero());

			row.createCell(colonne);
			row.getCell(colonne++).setCellValue("/");

			// Ecrire le numéro de l'équipe 
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue("");

			// Ecrire le nom
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue(j.getNom());

			// Ecrire le prénom 
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue(j.getPrenom());

			// Ecrire le numéro de licence
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue(j.getNumLicence());

			// Ecrire le numéro du club
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue(j.getClub().getNum());

			// Ecrire le nom du club
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue(j.getClub().getNom());

			// Ecrire la date de naissance
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue(j.getNaissance().toString());

			// Ecrire l'age 
			row.createCell(colonne);
			int age = j.age();
			row.getCell(colonne++).setCellValue(age);

			// Ecrire la catégorie
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue(j.cate());

			// Ecrire le niveau
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue(j.getNiveau());

			// Ecrire le sexe
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue("" + j.getSexe());

			// Afficher une pastille
			row.createCell(colonne);
			//	row.getCell(colonne++).setCellValue();

		}

		try {	
			FileOutputStream fos = new FileOutputStream(this.nomFichierLicencie);
			wb.write(fos);

			fos.close();

			wb.close();
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(this.getFp(),
					"Erreur dans d'enregistrement des licenciés, " +
					"fermer le fichier s'il est ouvert et recommencer");
		}
		this.licenciesEnreg = true;

		int ecart = 0;
		if (this.fp.getAdmin().isVisible())
			ecart = 250;
		else 
			ecart = 10;
		Confirmation charg = new Confirmation("Enregistrement réussi" , this.fp, ecart);
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
			} else if (nom.endsWith(".xls") || nom.endsWith(".xlsx")){
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
		} catch (IOException e) {}
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
				this.nomFichierCrit = nom + ".xlsx";
				this.enregistrerCrit();
			}
			else if (nom.endsWith(".xlsx")){
				this.nomFichierCrit = nom;
				this.enregistrerCrit();
			} else {
				javax.swing.JOptionPane.showMessageDialog(this.getFp(), 
						"Veuillez sélectionner un fichier excel.", 
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
			// Création du wokrbook
			XSSFWorkbook wb = new XSSFWorkbook();

			// Création de la feuille
			Sheet sheet = wb.createSheet("Feuil1");

			// Initialiser les numéros de ligne et de colonne
			int i = 0,j = 0;

			// Ecrire les en-têtes
			Row row = sheet.createRow(i++);

			row.createCell(j);
			row.getCell(j++).setCellValue("Numero");

			for (int ind = 1 ; ind <= this.nbMemo ; ind++) {
				row.createCell(j);
				row.getCell(j++).setCellValue("Memo " + ind);
			}

			for (int ind = 1 ; ind <= this.nbBalise ; ind++) {
				row.createCell(j);
				row.getCell(j++).setCellValue("Balise " + ind);
			}

			for (int ind = 1 ; ind <= this.nbBalise ; ind++) {
				row.createCell(j);
				row.getCell(j++).setCellValue("Zone maniabilité " + ind);
			}

			// Pour tous les inscrits
			for (Jeune je : this.getLesInscrits()) {
				j = 0;
				row = sheet.createRow(i++);

				// ecrire le numéro
				row.createCell(j);
				row.getCell(j++).setCellValue(je.toString());

				// Ecrire les réponses aux mémos
				for (int ind = 0 ; ind < this.nbMemo ; ind++) {
					row.createCell(j);
					row.getCell(j++).setCellValue(
							je.getLesReponses().get("memo" + ind));
				}

				// Ecrire les réponses aux balises
				for (int ind = 0 ; ind < this.nbBalise ; ind++) {
					row.createCell(j);
					row.getCell(j++).setCellValue(
							je.getLesReponses().get("balise" + ind));
				}

				// Ecrire les résultats de maniabilité
				for (int ind = 0 ; ind < this.nbBalise ; ind++) {
					row.createCell(j);
					row.getCell(j++).setCellValue(
							je.getLesReponses().get("maniabilite" + ind));
				}
			}

			// Ecriture des paramètres
			// Création de la feuille
			sheet = wb.createSheet("Feuil2");

			// Initialiser les numéros de ligne et de colonne
			i = 0;

			// Ecrire le nombre de balise
			row = sheet.createRow(i++);

			row.createCell(0);
			row.getCell(0).setCellValue("Nombre de balise");

			row.createCell(1);
			row.getCell(1).setCellValue(this.nbBalise);

			// Ecrire le nombre de mémo
			row = sheet.createRow(i++);
			row.createCell(0);
			row.getCell(0).setCellValue("Nombre de mémo");

			row.createCell(1);
			row.getCell(1).setCellValue(this.nbMemo);

			// Ecrire les points à gagner
			row = sheet.createRow(i++);
			row.createCell(0);
			row.getCell(0).setCellValue("Nombre de points gagnés");

			// Ecrire les points par balise trouvée
			row = sheet.createRow(i++);
			row.createCell(1);
			row.getCell(1).setCellValue("Balise trouvée");

			row.createCell(2);
			row.getCell(2).setCellValue(gains.get("baliseTrouvee"));

			// Ecrire les points par réponse correcte aux balises
			row = sheet.createRow(i++);
			row.createCell(1);
			row.getCell(1).setCellValue("Balise correcte");

			row.createCell(2);
			row.getCell(2).setCellValue(gains.get("baliseCorrecte"));

			// Ecrire les points par mémo trouvé 
			row = sheet.createRow(i++);
			row.createCell(1);
			row.getCell(1).setCellValue("Mémo trouvé");
			row.createCell(2);
			row.getCell(2).setCellValue(gains.get("memoTrouve"));

			// Ecrire les points par réponse correcte aux mémos
			row = sheet.createRow(i++);
			row.createCell(1);
			row.getCell(1).setCellValue("Mémo correct");

			row.createCell(2);
			row.getCell(2).setCellValue(gains.get("memoCorrect"));

			// Ecrire les points par zone de maniabilité
			row = sheet.createRow(i++);
			row.createCell(1);
			row.getCell(1).setCellValue("Zone de maniabilité réussie");

			row.createCell(2);
			row.getCell(2).setCellValue(gains.get("maniabilite"));

			// Ecrire les réponses attendues pour chaque question
			row = sheet.createRow(i++);
			row.createCell(0);
			row.getCell(0).setCellValue("Réponses aux questions");

			// Ecrire les réponses aux mémos
			for (int ind = 0 ; ind < nbMemo ; ind++) {
				row = sheet.createRow(i++);
				row.createCell(1);
				row.getCell(1).setCellValue("Mémo " + ind);

				row.createCell(2);
				row.getCell(2).setCellValue(this.reponses.get("memo" + ind));
			}

			// Ecrire les réponses aux balises
			for (int ind = 0 ; ind < nbBalise ; ind++) {
				row = sheet.createRow(i++);
				row.createCell(1);
				row.getCell(1).setCellValue("Balise " + ind);

				row.createCell(2);
				row.getCell(2).setCellValue(this.reponses.get("balise" + ind));
			}


			try {	
				FileOutputStream fos = new FileOutputStream(this.nomFichierCrit);
				wb.write(fos);

				fos.close();
				wb.close();

			} catch (Exception e) {
				javax.swing.JOptionPane.showMessageDialog(this.getFp(),
						"Erreur dans d'enregistrement des licenciés, " +
						"fermer le fichier s'il est ouvert et recommencer");
			}


			this.critEnreg = true;

			int ecart = 0;
			if (this.fp.getAdmin().isVisible())
				ecart = 250;
			else 
				ecart = 10;
			Confirmation charg = new Confirmation("Enregistrement réussi", this.fp, ecart);
			Thread thread = new Thread(charg);
			thread.start();
		}
	}

	////////////////
	///CLASSEMENT///
	////////////////

	/**
	 * Faire le classement des jeunes et l'afficher.
	 */
	public void classement() {
		if (this.lesInscrits.isEmpty()) {
			javax.swing.JOptionPane.showMessageDialog(this.fp,
					"Impossible de calculer le classement car il n'y a aucun inscrits.");
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
		fcVert.afficherCouleur(classement, ""); 

		FicheClassement fcBleu = new FicheClassement();
		fcBleu.afficherCouleur(classement, "CHEMIN / MARCASSINS");

		FicheClassement fcRouge = new FicheClassement();
		fcRouge.afficherCouleur(classement, "PISTE / RENARDS");

		FicheClassement fcNoir = new FicheClassement();
		fcNoir.afficherCouleur(classement, "ENTIER / COYOTES");

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
			exporterClassement("");
			exporterClassement("CHEMIN / MARCASSINS");
			exporterClassement("PISTE / RENARDS");
			exporterClassement("ENTIER / COYOTES");
		}
	}

	/**
	 * Remplir un fichier de classement pour une couleur donnée.
	 * @param couleur La couleur de la catégorie à écrire dans le fihcier
	 */
	public void exporterClassement(String couleur) {
		// Récupérer le fichier
		JFileChooser chooser = new JFileChooser();

		// Dossier Courant
		chooser.setCurrentDirectory(new File("." + File.separator));

		// Extension des fichiers affichés
		chooser.setFileFilter(new FileNameExtensionFilter("feuille de calcul", "xls", "xlsx", "xlsm"));

		// Sélectionner un seul fichier
		chooser.setMultiSelectionEnabled(false);

		// Le fichier d'enregistrement des classements
		File fichierClassement = null;
		if (chooser.showDialog(chooser, "Exporter les classements") == 0) {
			// Récupérer le fichier d'enregistrement
			fichierClassement = chooser.getSelectedFile();
			String nom = fichierClassement.getName();

			if (!nom.contains(".")) {
				this.nomFichierCrit = nom + ".xlsx";
				this.enregistrerCrit();
			}
		}

		try {
			fichierClassement.createNewFile();

			// Créer un workbook
			Workbook wb = WorkbookFactory.create(fichierClassement);
			Sheet sheet = wb.createSheet("Feuille1");

			Row row = sheet.getRow(0);
			row.getCell(0).setCellValue("Rang");
			row.getCell(1).setCellValue("Lettre");
			row.getCell(2).setCellValue("N°");
			row.getCell(3).setCellValue("/");
			row.getCell(4).setCellValue("N° Equipe");
			row.getCell(5).setCellValue("Nom");
			row.getCell(6).setCellValue("Prénom");
			row.getCell(7).setCellValue("N° Licence");
			row.getCell(8).setCellValue("N° Club");
			row.getCell(9).setCellValue("Nom Club");
			row.getCell(10).setCellValue("Date Naissance");
			row.getCell(11).setCellValue("Age");
			row.getCell(12).setCellValue("Catégorie");
			row.getCell(13).setCellValue("Niveau");
			row.getCell(14).setCellValue("Sexe");
			row.getCell(15).setCellValue("Points");
			row.getCell(16).setCellValue("Pastille");

			int i = 0;
			int k;
			int nbPoints = 0;
			// Remplir le fichier avec les jeunes de la couleur passée en paramèters
			for (Jeune j : classement) {
				row = sheet.getRow(i);
				if (j.getNiveau().equals(couleur)) {
					i++;
					if (nbPoints != j.getPoints())
						k = i;
					nbPoints = j.getPoints();

					row.getCell(0).setCellValue(i);
					row.getCell(1).setCellValue(j.getClub().toString());
					row.getCell(2).setCellValue(j.getNumero());
					row.getCell(3).setCellValue("/");
					row.getCell(4).setCellValue(j.getNumero());
					row.getCell(5).setCellValue(j.getNom());
					row.getCell(6).setCellValue(j.getPrenom());
					row.getCell(7).setCellValue(j.getNumLicence());
					row.getCell(8).setCellValue(j.getClub().getNum());
					row.getCell(9).setCellValue(j.getClub().getNom());
					row.getCell(10).setCellValue(j.getNaissance().toString());
					row.getCell(11).setCellValue(j.age());
					row.getCell(12).setCellValue(j.getNiveau());
					row.getCell(13).setCellValue(j.getNiveau());
					row.getCell(14).setCellValue(j.getSexe());
					row.getCell(15).setCellValue(j.getPoints());
				}
			}

			wb.close();
		} catch (Exception e) {}
	}

	////////////////
	///CHARGEMENT///
	////////////////

	/**
	 * Charger les inscrits à un critérium.
	 * @param nomFichierInscrtis   Le nom du fichier duquel on charge les inscrits
	 **/
	public void chargerCrit(String nomFichierInscrtis) {
		/*Workbook wb ;
	
		// Créer le fichier et la feuille de calcul
		wb = WorkbookFactory.create(new File(nomFichierInscrtis));
	
		Sheet sheet = wb.getSheet("Feuill1");

		int i = 0, j = 0;
		Row row = sheet.getRow(i++);

		while (row.getCell(j).getStringCellValue() != "") {

			String lettre = row.getCell(0).getStringCellValue();
			int numero = (int) row.getCell(1).getNumericCellValue();
			int equipe = (int) row.getCell(3).getNumericCellValue();
			String nom = row.getCell(4).getStringCellValue();
			String prenom = row.getCell(5).getStringCellValue();
			int licence = (int) row.getCell(6).getNumericCellValue();
			int nomClub = (int) row.getCell(7).getNumericCellValue();
			String club = row.getCell(8).getStringCellValue();
		
		
		
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
		thread.start();*/
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
		majBarreMemo(j.getNiveau());
	}

	/**
	 * Mettre à jour la barre de progression des mémos.
	 * @param couleur La couleur de la barre à mettre à jour
	 */
	public void majBarreMemo(String couleur) {
		// Calculer le nombre de jeunes dans cette catégorie
		int nb = 0;
		for (Jeune jj : revenuMemo)
			if (jj.getNiveau().equals(couleur))
				nb++;

		// Calculer le nombre total de jeunes inscrits dans cette catégorie
		int total = 0;
		for (Jeune jj : lesInscrits)
			if (jj.getNiveau().equals(couleur))
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
		majBarreBalise(j.getNiveau());
	}

	/**
	 * Mettre à jour la barre de progression.
	 * @param couleur La couleur de la barre de progression
	 */
	public void majBarreBalise(String couleur) {
		// Calculer le nombre de jeunes dans cette catégorie
		int nb = 0;
		for (Jeune jj : revenuBalises)
			if (jj.getNiveau().equals(couleur))
				nb++;

		// Calculer le nombre total de jeunes inscrits dans cette catégorie
		int total = 0;
		for (Jeune jj : lesInscrits)
			if (jj.getNiveau().equals(couleur))
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

	////////////////////////
	///METHODES STATIQUES///
	////////////////////////

	/**
	 * Récupérer un club à partir de sa lettre.
	 * @param lettre La lettre du club
	 * @return Le club correspondant
	 */
	protected static Club recupClub(String lettre) {
		switch (lettre) {
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
			System.exit(0);
			return Club.A;
		}
	}

	/**
	 * Récupérer un club à partir de son nom.
	 * @param nom Le nom du club
	 * @return Le club correspondant
	 */
	protected static Club toClub(String nom) {
		switch (nom) {
		case "C S MUNICIPAL SEYNOIS" : 
			return Club.A;
		case "UNION CYCLISTE PEDESTRE LONDAISE" : 
			return Club.B;
		case "CYCLO CLUB ARCOIS" : 
			return Club.C;
		case "CYCLO CLUB LUCOIS" : 
			return Club.D;
		case "VELO RANDONNEUR CANTONAL" : 
			return Club.E;
		case "VELO VERT FLAYOSCAIS" : 
			return Club.F;
		case "VELO SPORT CYCLO HYEROIS" : 
			return Club.G;
		case "LA VALETTE CYCLOTOURISME" : 
			return Club.H;
		case "VELO CLUB SIX-FOURS" : 
			return Club.I;
		case "CRO ROIS TEAM" : 
			return Club.J;
		case "UFOLEP VELO CLUB FARLEDOIS" : 
			return Club.K;
		case "VELO CLUB NANS LES PINS LA STE BAUME" : 
			return Club.L;
		default : 
			System.exit(0);
			return null;
		}
	}

	/**
	 * Convertir un entier en date (la date de repère est le 01/01/1900.
	 * @param i l'entier à convertir
	 * @return LA date correspondante
	 */
	protected static Date convertir(int i) {
		int reste = i;
		int a = 0;
		while (reste > 365) {
			if (a % 4 == 0)
				reste = reste - 366;
			else
				reste = reste - 365;
			a++;
		}

		a = a + 1900;

		int m = 0;
		int b = 0;
		int j = 0;
		if (a%4 == 0) 
			b = 1;

		if (reste < 32) {
			m = 1;
			j = reste;
		} else if (reste < 60 + b) {
			m = 2;
			j = reste - 31;
		} else if (reste < 91 + b) {
			m = 3;
			j = reste - 59 - b;
		} else if (reste < 121 + b) {
			m = 4;
			j = reste - 90 - b;
		} else if (reste < 152 + b) {
			m = 5;
			j = reste - 120 - b;
		} else if (reste < 182 + b) {
			m = 6;
			j = reste - 151 - b;
		} else if (reste < 213 + b) {
			m = 7;
			j = reste - 181 - b;
		} else if (reste < 244 + b) {
			m = 8;
			j = reste - 212 - b;
		} else if (reste < 274 + b) {
			m = 9;
			j = reste - 243 - b;
		} else if (reste < 305 + b) {
			m = 10;
			j = reste - 273 - b;
		} else if (reste < 335 + b) {
			m = 11;
			j = reste - 304 - b;
		} else {
			m = 12;
			j = reste - 334 - b;
		}

		return new Date(a, m, j);
	}
}

