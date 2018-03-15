package Modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileOutputStream;
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

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
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

	/** La fenêrte principale associée. */
	private FenetrePrincipale fp;

	/** La liste des inscrits. */
	private ArrayList<Jeune> lesInscrits;

	/** Le nom du fichier du critérium. */
	private String nomFichierCrit;

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
	
	/** Les fiches classements */
	FicheClassement fcVert, fcBleu, fcRouge, fcNoir;

	public static final HashMap<String, Integer> alphabet = new HashMap<>();

	//////////////////
	// CONSTRUCTEUR //
	//////////////////

	/**
	 * Construire un projet
	 * @param fp  La fenêtre principale 
	 */
	public Projet(FenetrePrincipale fp) {
		// Initialisation du projet
		initialiserProjet(fp);

		// Initialiser l'alphabet
		initialiserAlphabet();

		// Gérer le sud de la fenêtre : les barres de progression
		JPanel panel = this.fp.getZoneProg();

		panel.add(new JLabel("Balises : "));
		panel.add(this.nombreArriveBalise);
		panel.add(this.barreBalise);

		panel.add(new JLabel("Mémo orientation : "));
		panel.add(this.nombreArriveMemo);
		panel.add(this.barreMemo);

		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("fichier excel", "xls", "xlsx", "xlsm"));

		// Dossier 
		chooser.setCurrentDirectory(new File("C:" + File.separator + "Users" + File.separator));
		chooser.setMultiSelectionEnabled(true);

		// Si validation du fichier
		if (chooser.showDialog(chooser, "Ouvrir") == 0) {
			File files[] = chooser.getSelectedFiles();

			for (int ind = 0 ; ind < files.length ; ind++) {
				File f = files[ind];
				inscription(f);
			}
			this.critEnreg = true;
		} else {
			System.exit(0);
		}
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
	 * Récupérer toutes les fiches classements
	 * @return Les fiches classements de chaque couleurs
	 */
	public ArrayList<FicheClassement> getfc() {
		ArrayList<FicheClassement> jifs = new ArrayList<>();
		jifs.add(fcVert);
		jifs.add(fcBleu);
		jifs.add(fcRouge);
		jifs.add(fcNoir);
		return jifs;
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
		Niveau niveau = Niveau.V;

		// Pour chaque couleur
		for (int i = 0; i < 4; i++) {
			JPanel grille = new JPanel((new FlowLayout()));
			ArrayList<JCheckBox> list = new ArrayList<>();

			// Initialiser la couleur
			switch (i) {
			case 0:
				niveau = Niveau.V;
				break;
			case 1:
				niveau = Niveau.B;
				break;
			case 2:
				niveau = Niveau.R;
				break;
			case 3:
				niveau = Niveau.N;
				break;
			}

			// Créer la fenêtre interne
			JInternalFrame jif = new JInternalFrame(
					"Sélectionner les participants " + niveau.getNom(), true, false, false);

			// Afficher tous les numéros de la couleur
			for (Jeune licencie : this.lesInscrits) {
				if (licencie.getNiveau() == niveau) {
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
		if (this.lesInscrits.size() > 0) {
			// Créer la fenêtre interne
			JInternalFrame jif = new JInternalFrame("Supprimer des licenciés");
			this.fp.getDesktop().add(jif);
			jif.getContentPane().setLayout(new BorderLayout());

			JPanel panel1 = new JPanel(new FlowLayout());
			JPanel panel2 = new JPanel(new FlowLayout());

			// Afficher les numéros de tous les licenciés
			ArrayList<JCheckBox> list = new ArrayList<>();
			for (Jeune licencie : lesInscrits) {
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
		for (Jeune licencie : this.lesInscrits) {
			if (licencie.getNiveau() == Niveau.V) {
				JButton btn = new JButton(licencie.toString());
				btn.addActionListener(new ActionModifierFiche(licencie));
				btn.setBackground(new Color(14, 106, 0));
				btn.setForeground(new Color(255, 255, 255));
				this.fp.getGrilleLicencies().add(btn);
			}
		}

		for (Jeune licencie : this.lesInscrits) {
			if (licencie.getNiveau() == Niveau.B) {
				JButton btn = new JButton(licencie.toString());
				btn.addActionListener(new ActionModifierFiche(licencie));
				btn.setBackground(new Color(0, 0, 110));
				btn.setForeground(new Color(255, 255, 255));
				this.fp.getGrilleLicencies().add(btn);
			}
		}

		for (Jeune licencie : this.lesInscrits) {
			if (licencie.getNiveau() == Niveau.R) {
				JButton btn = new JButton(licencie.toString());
				btn.addActionListener(new ActionModifierFiche(licencie));
				btn.setBackground(new Color(175, 0, 0));
				btn.setForeground(new Color(255, 255, 255));
				this.fp.getGrilleLicencies().add(btn);
			}
		}

		for (Jeune licencie : this.lesInscrits) {
			if (licencie.getNiveau() == Niveau.N){
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
	public void affichageSelectionnes(Niveau niveau) {
		ArrayList<Jeune> inscrits = new ArrayList<Jeune>();
		
		System.out.println(lesInscrits.size());
		
		for (Jeune licencie : this.getLesInscrits()) {
			System.out.println(licencie);
			if (licencie.getNiveau() == niveau) {
				inscrits.add(licencie);
			}
		}

		if (inscrits.isEmpty()) {
			javax.swing.JOptionPane.showMessageDialog(this.fp,
					"Aucun inscrit dans cette catégorieeeee.");
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

			switch(niveau) {
			case V :
				btn.setBackground(Color.GREEN);
				break;
			case B :
				btn.setBackground(Color.BLUE);
				break;
			case R :
				btn.setBackground(Color.RED);
				break;
			case N :
				btn.setBackground(Color.BLACK);
				btn.setForeground(Color.WHITE);
				break;
			}

			this.getFp().getGrilleInscrits().add(btn);
		}
		this.getFp().getGrilleInscrits().setVisible(true);

		// Afficher les barres de progression
		this.fp.getZoneProg().setVisible(true);
		this.majBarreBalise(niveau);
		this.majBarreMemo(niveau);

		this.getFp().repaint();
	}

	///////////////////////////////////////
	///GESTION DES LICENCIES ET INSCRITS///
	///////////////////////////////////////

	/**
	 * Ajouter un inscrit à la liste triée.
	 * @param Le licencié que l'on ajoute 
	 **/
	public void ajouterInscrit(Jeune licencie) {
		// Si le jeune est déjà dans la liste, rien à faire
		if (this.dejaIscrit(licencie))
			return;
		
		// Si c'est le permier, pas besoin de trier
		if (this.lesInscrits.size() == 0) {
			this.lesInscrits.add(licencie);
		} else {
			int i;
			for (i = 0; i < this.lesInscrits.size(); i++) {
				// Chercher l'emplacement
				if (licencie.inferieur(this.lesInscrits.get(i))) {
					// Faire une place
					this.decaler(this.lesInscrits, i);
					// Ajouter
					this.lesInscrits.set(i, licencie);
					break;
				}
			}
			// Ou l'ajouter à la fin
			if (i == this.lesInscrits.size()) {
				this.lesInscrits.add(licencie);
			}
		}
		this.critEnreg = false;
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
	public void supprimerInscrit(int numero) {
		for (Jeune licencie : this.lesInscrits) {
			if (licencie.getNumero() == numero) {
				this.lesInscrits.remove(licencie);
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
		for (Jeune licencie : this.lesInscrits) {
			if (licencie.toString().equals(numero)) {
				res = licencie;
				break;
			}
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
				this.supprimerInscrit(Integer.parseInt(checkBox.getText()));
				this.critEnreg = false;
			}
		}
		jif.dispose();
		this.affichage();
	}


	////////////////////
	///ENREGSITREMENT///
	////////////////////

	/**
	 * Enregistrer l'ensemble des inscrits dans un fichier excel.
	 */
	public void enregistrerInscrit() {
		// Création du wokrbook
		XSSFWorkbook wb = new XSSFWorkbook();

		// Création de la feuille
		Sheet sheet = wb.createSheet("Feuil1");

		// Initialiser le style du titre
		final CellStyle style1 = wb.createCellStyle();
		// couleur de fond
	    style1.setFillForegroundColor(IndexedColors.GOLD.getIndex());
	    style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    // alignement
	    style1.setAlignment(HorizontalAlignment.CENTER);
	    style1.setVerticalAlignment(VerticalAlignment.CENTER);
	    // bordure
	    style1.setBorderLeft(BorderStyle.THIN);
	    style1.setBorderRight(BorderStyle.THIN);
	    style1.setBorderTop(BorderStyle.THIN);
	    style1.setBorderBottom(BorderStyle.THIN);
	    // police
	    XSSFFont font = wb.createFont();
	    font.setFontHeightInPoints((short) 18);
	    font.setBold(true);
	    style1.setFont(font);
	    
	    // Initialiser le style en-tête
	    final CellStyle style2 = wb.createCellStyle();
	    style2.setFillForegroundColor(IndexedColors.GOLD.getIndex());
	    style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    // alignement
	    style2.setAlignment(HorizontalAlignment.CENTER);
	    style2.setVerticalAlignment(VerticalAlignment.CENTER);
	    // police
	    XSSFFont font2 = wb.createFont();
	    font2.setBold(true);
	    style2.setFont(font2);
	    // bordure
	    style2.setBorderLeft(BorderStyle.THIN);
	    style2.setBorderRight(BorderStyle.THIN);
	    style2.setBorderTop(BorderStyle.THIN);
	    style2.setBorderBottom(BorderStyle.THIN);

	    // Initialiser le style couleur
	    final CellStyle style3 = wb.createCellStyle();
	    style3.setFillForegroundColor(IndexedColors.GOLD.getIndex());
	    style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    // alignement
	    style3.setAlignment(HorizontalAlignment.CENTER);
	    style3.setVerticalAlignment(VerticalAlignment.CENTER);
	    // bordure
	    style3.setBorderLeft(BorderStyle.THIN);
	    style3.setBorderRight(BorderStyle.THIN);
	    style3.setBorderTop(BorderStyle.THIN);
	    style3.setBorderBottom(BorderStyle.THIN);
	    
	    // Initialiser le style sans couleur
	    final CellStyle style4 = wb.createCellStyle();
	    style4.setFillForegroundColor(IndexedColors.WHITE.getIndex());
	    style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    // alignement
	    style4.setAlignment(HorizontalAlignment.CENTER);
	    style4.setVerticalAlignment(VerticalAlignment.CENTER);
	    // bordure
	    style4.setBorderLeft(BorderStyle.THIN);
	    style4.setBorderRight(BorderStyle.THIN);
	    style4.setBorderTop(BorderStyle.THIN);
	    style4.setBorderBottom(BorderStyle.THIN);

	    // Fusionner la cellules des 3 première lignes
	    CellRangeAddress cra = new CellRangeAddress(0, 2, 0, 14);
	    sheet.addMergedRegion(cra);
	    
	    // Ecrire les titre de la feuille
		Row row = sheet.createRow(0);
		row.createCell(0);
	    row.getCell(0).setCellValue("TABLEAU INSCRIPTIONS");
	    row.getCell(0).setCellStyle(style1);
		row.createCell(14);
		
		// Tracer la bordure droite sur la  première case
		row.createCell(14);		
	    row.getCell(14).setCellStyle(style1);
	    row = sheet.createRow(1);
		row.createCell(14);		
	    row.getCell(14).setCellStyle(style1);
	    row = sheet.createRow(2);
		row.createCell(14);		
	    row.getCell(14).setCellStyle(style1);

		// Ecrire les en-têtes
	    row  = sheet.createRow(3);
	    
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
		row.createCell(14);
		row.getCell(14).setCellValue("Pastille");
		
		// Pour itérer sur les lignes
		int ligne = 4;
		int colonne = 0;
		
		// Ecrire pour chaque jeune, une ligne dans le fichier
		for (Jeune j : this.getLesInscrits()) {

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
			row.getCell(colonne++).setCellValue(j.getNiveau().getNom());

			// Ecrire le sexe
			row.createCell(colonne);
			row.getCell(colonne++).setCellValue("" + j.getSexe());

			// Afficher une pastille
			row.createCell(colonne);
			final CellStyle pastille = wb.createCellStyle();
			// couleur de fond
		    pastille.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    pastille.setBorderLeft(BorderStyle.THIN);
		    pastille.setBorderRight(BorderStyle.THIN);
		    pastille.setBorderTop(BorderStyle.THIN);
		    pastille.setBorderBottom(BorderStyle.THIN);
		    
			switch (j.getNiveau()) {
			case V : 
			    pastille.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			    break;
			case B : 
			    pastille.setFillForegroundColor(IndexedColors.BLUE.getIndex());
			    break;
			case R :
			    pastille.setFillForegroundColor(IndexedColors.RED.getIndex());
			    break;
			case N : 
			    pastille.setFillForegroundColor(IndexedColors.BLACK.getIndex());
			    break;
			}
				row.getCell(colonne++).setCellStyle(pastille);
		}

		// Ajouter le style des cellules
		// La première ligne
		row = sheet.getRow(3);
		for (int ind = 0 ; ind < 15 ; ind++) {
			row.getCell(ind).setCellStyle(style2);
		}
		
		// Les colonnes 1, 2, 3, 9, 11, 12
		for (int ind = 4; ind < this.lesInscrits.size() + 4 ; ind++) {
			row = sheet.getRow(ind);
			row.getCell(0).setCellStyle(style3);
			row.getCell(1).setCellStyle(style3);
			row.getCell(2).setCellStyle(style3);
			row.getCell(8).setCellStyle(style3);
			row.getCell(10).setCellStyle(style3);
			row.getCell(11).setCellStyle(style3);
		}
		
		// Les autres colonnes
		for (int ind = 4; ind < this.lesInscrits.size() + 4 ; ind++) {
			row = sheet.getRow(ind);
			row.getCell(3).setCellStyle(style4);
			row.getCell(4).setCellStyle(style4);
			row.getCell(5).setCellStyle(style4);
			row.getCell(6).setCellStyle(style4);
			row.getCell(7).setCellStyle(style4);
			row.getCell(9).setCellStyle(style4);
			row.getCell(12).setCellStyle(style4);
			row.getCell(13).setCellStyle(style4);
		}
		
		// Ajuster la taille des colonnes
		for (int ind = 0 ; ind < 14 ; ind++) {
			sheet.autoSizeColumn(ind, true);
		}
		
		// Vrai si pas de problème faux sinon
		boolean ok = true;
		
		// Ecrire le fichier excel
		try {
			FileOutputStream fos = new FileOutputStream(this.nomFichierCrit);
			wb.write(fos);

			fos.close();

			wb.close();
		} catch (Exception e) {
			ok = false;
			javax.swing.JOptionPane.showMessageDialog(this.getFp(),
					"Erreur dans d'enregistrement des licenciés, " +
					"fermer le fichier s'il est ouvert et recommencer");
		}
		
		if (ok) {
			this.critEnreg = true;

			int ecart = 0;
			if (this.fp.getAdmin().isVisible())
				ecart = 250;
			else
				ecart = 10;
			Confirmation charg = new Confirmation("Enregistrement réussi" , this.fp, ecart);
			Thread thread = new Thread(charg);
			thread.start();
		}
	}

	/**
	 * Enregistrer l'ensemble des licenciées dans un fichier excel dans un nouveau fichier.
	 */
	public void enregistrerSousInscrits() {
		JFileChooser chooser = new JFileChooser();

		// Dossier Courant
		chooser.setCurrentDirectory(new File("." + File.separator));

		if (chooser.showDialog(chooser, "Ouvrir") == 0) {
			// Récupérer le fichier d'enregistrement
			File fichier = chooser.getSelectedFile();

			String nom = fichier.getAbsolutePath();

			if (!nom.contains(".")) {
				this.nomFichierCrit = nom + ".xls";
				this.enregistrerInscrit();
			} else if (nom.endsWith(".xls") || nom.endsWith(".xlsx")){
				this.nomFichierCrit = nom;
				this.enregistrerInscrit();
			} else {
				javax.swing.JOptionPane.showMessageDialog(this.fp,
						"Veuillez sélectionner un fichier excel (extension .xls).",
						"Erreur",
						JOptionPane.ERROR_MESSAGE
						);
				this.enregistrerSousInscrits();
			}
		}

		File config = new File("./fichierConfiguration.txt");
		try {
			config.createNewFile();
			FileWriter fw = new FileWriter(config);
			fw.write(this.nomFichierCrit);
			fw.close();
		} catch (IOException e) {}
	}

	/**
	 * Enregistrer les inscrits; les scores et les paramètres dans deux fichiers.
	 */
	public void enregistrerSousResultats() {
		JFileChooser chooser = new JFileChooser();

		// Dossier Courant
		chooser.setCurrentDirectory(new File("." + File.separator));

		if (chooser.showDialog(chooser, "Enregistrer le critérium") == 0) {
			// Récupérer le fichier d'enregistrement
			File fichierSelectionnes = chooser.getSelectedFile();

			String nom = fichierSelectionnes.getAbsolutePath();

			if (!nom.contains(".")) {
				this.nomFichierCrit = nom + ".xlsx";
				this.enregistrerResultats();
			}
			else if (nom.endsWith(".xlsx")){
				this.nomFichierCrit = nom;
				this.enregistrerResultats();
			} else {
				javax.swing.JOptionPane.showMessageDialog(this.getFp(),
						"Veuillez sélectionner un fichier excel.",
						"Erreur",
						JOptionPane.ERROR_MESSAGE
						);
				this.enregistrerSousResultats();
			}
		}
	}

	/**
	 * Enregistrer le critérium avec le nom de fichier en attribut.
	 */
	public void enregistrerResultats() {
		if (this.nomFichierCrit == null) {
			this.enregistrerSousResultats();
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
			for (int ind = 1 ; ind <= nbMemo ; ind++) {
				row = sheet.createRow(i++);
				row.createCell(1);
				row.getCell(1).setCellValue("Mémo " + ind);

				row.createCell(2);
				int num = ind - 1;
				row.getCell(2).setCellValue(this.reponses.get("memo" + num));
			}

			// Ecrire les réponses aux balises
			for (int ind = 1 ; ind <= nbBalise ; ind++) {
				row = sheet.createRow(i++);
				row.createCell(1);
				row.getCell(1).setCellValue("Balise " + ind);

				int num = ind - 1;
				row.createCell(2);
				row.getCell(2).setCellValue(this.reponses.get("balise" + num));
			}


			// Vrai si pas de problème faux sinon
			boolean ok = true;
			
			// Ecriture dans le fichier
			try {
				ok = false;
				FileOutputStream fos = new FileOutputStream(this.nomFichierCrit);
				wb.write(fos);

				fos.close();
				wb.close();

			} catch (Exception e) {
				javax.swing.JOptionPane.showMessageDialog(this.getFp(),
						"Erreur dans d'enregistrement des licenciés, " +
						"fermer le fichier s'il est ouvert et recommencer");
			}

			if (ok) {
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

		// Vérifier que les fiches scores des jeunes sont complémtement remplies
		boolean ok = true;
		for (Jeune j : this.lesInscrits) {
			ok = ok && j.getFiche2().verifScore();
		}
		
		if (!ok)
			return;
		
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
		fcNoir = new FicheClassement(this);
		fcRouge = new FicheClassement(this);
		fcBleu = new FicheClassement(this);
		fcVert = new FicheClassement(this);

		fcVert.afficherCouleur(classement, Niveau.V);
		fcBleu.afficherCouleur(classement, Niveau.B);
		fcRouge.afficherCouleur(classement, Niveau.R);
		fcNoir.afficherCouleur(classement, Niveau.N);

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
				this.enregistrerResultats();
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
					row.getCell(12).setCellValue(j.getNiveau().getNom());
					row.getCell(13).setCellValue(j.getNiveau().getNom());
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
		Workbook wb = null;

		// Créer le fichier et la feuille de calcul
		try {
			wb = WorkbookFactory.create(new File(nomFichierInscrtis));
		} catch (Exception e) {
			return;
		}
		Sheet sheet = wb.getSheet("Feuil1");

		// Initialiser les indices et ligne et de colonne
		int i = 1;
		Row row = sheet.getRow(i++);

		while (row != null) {

			// Récupérer les inscrits
			String id = row.getCell(0).getStringCellValue();

			// Créer la fiche du jeune
			FicheJeune fj = new FicheJeune(this);

			// Récupérer le jeune avec son numéro
			Jeune je = this.recupJeune(id);
			
			// Maj de la fiche du jeune
			fj.setTitle(je.toString());
			fj.setLicencie(je);
			fj.invaliderModif();

			// Récupérer les réponses dans le fichier
			recupReponses(je, row);
			
			// Création et maj de la fiche score du jeune
			FicheScore fs = new FicheScore(this.getFp());
			fs.setLicencie(je);;
			fs.setTitle(je.toString());
			fs.invaliderModif();
			
			je.setFiche2(fs);
			
			// Ajouter le jeune à la liste des inscrits
			this.ajouterInscrit(je);

			this.fp.getDesktop().add(fj);
			fj.hide();
			row = sheet.getRow(i++);
		}
		// Mettre à jour les paramètres du critérium
		sheet = wb.getSheet("Feuil2");

		// Récupérer le nombre de balises
		this.nbBalise = (int) sheet.getRow(0).getCell(1).getNumericCellValue();

		// Récupérer le nombre de mémo
		this.nbMemo = (int) sheet.getRow(1).getCell(1).getNumericCellValue();

		// Récupérer les points pour une balise trouvée
		this.gains.put("baliseTrouvee", (int) sheet.getRow(3).getCell(2).getNumericCellValue());

		// Récupérer les points pour une réponse correcte à une balise
		this.gains.put("baliseCorrecte", (int) sheet.getRow(4).getCell(2).getNumericCellValue());

		// Récupérer les points pour un mémo trouvé
		this.gains.put("memoTrouve", (int) sheet.getRow(5).getCell(2).getNumericCellValue());

		// Récupérer les points pour une question correcte à un mémo
		this.gains.put("memoCorrect", (int) sheet.getRow(6).getCell(2).getNumericCellValue());

		// Récupérer les points pour une zone de manabilité réussie
		this.gains.put("maniabilite", (int) sheet.getRow(7).getCell(2).getNumericCellValue());

		// Récupérer les réponses aux mémos orientations
		for (int ind = 0; ind < nbMemo; ind++) {
			this.reponses.put("memo" + ind,
					sheet.getRow(9 + ind).getCell(2).getStringCellValue());
		}

		// Récupérer les réponses aux balises
		for (int ind = 0; ind < nbBalise; ind++) {
			this.reponses.put("balise" + ind,
					sheet.getRow(9 + ind + nbMemo).getCell(2).getStringCellValue());
		}

		// Créer des fiches avec les bons nombre de mémo / balises
		this.ficheReponse = new FicheReponse(this);
		this.ficheGains = new FicheGains(this);

		this.fp.getDesktop().add(this.ficheReponse);
		this.fp.getDesktop().add(this.ficheGains);

		// Remplir les fiches de points et de réponses
		this.ficheReponse.invaliderModif();

		this.ficheGains.invaliderModif();

		// Mettre à jour le nombre des jeunes sur chaque circuit
		for (Jeune je : this.lesInscrits) {
			Boolean finiMemo = true;
			Boolean baliseFinie = true;

			for (int ind = 0; ind < this.nbMemo; ind++) {
				if (je.getLesReponses().get("memo" + ind).equals("")) {
					finiMemo = false;
					break;
				}
			}

			if (finiMemo)
				this.fp.getProjet().arriveMemo(je);

			for (int ind = 0; ind < this.nbBalise; ind++) {
				if (je.getLesReponses().get("balise" + ind).equals("")) {
					baliseFinie = false;
					break;
				}
			}

			if (baliseFinie)
				this.fp.getProjet().arriveBalise(je);

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
		
	}

	/**
	 * Récupérer les jeunes inscrits dans le fichier.
	 * @param f Le fichier d'inscription d'un club
	 */
	public void inscription(File f) {

		Workbook wb ;
		
		try {
			// Créer le fichier et la feuille de calcul
			wb = WorkbookFactory.create(f);

			Sheet sheet = wb.getSheet("Inscriptions");

			int index = 4;
			Row row = sheet.getRow(index++);

			while (row != null) {

				String lettre = row.getCell(0).getStringCellValue();
				int numero = (int) row.getCell(1).getNumericCellValue();
			//	int equipe = (int) row.getCell(3).getNumericCellValue();
				String nom = row.getCell(4).getStringCellValue();
				String prenom = row.getCell(5).getStringCellValue();
				int licence = (int) row.getCell(6).getNumericCellValue();
				int naissance = (int) row.getCell(9).getNumericCellValue();
				Niveau niveau = Niveau.get(row.getCell(12).getStringCellValue());
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

				this.ajouterInscrit(j);

				this.fp.getDesktop().add(fj);
				fj.hide();
			}

		} catch (Exception e) {
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(this.getFp(),
					"Erreur dans l'ouverture du fichier des licenciés.");
		}
	}

	/**
	 * Dmeander quels sont à charger et les charger.
	 */
	public void chargerFichierInscription() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("fichier excel", "xls", "xlsx", "xlsm"));

		// Dossier 
		chooser.setCurrentDirectory(new File("C:" + File.separator + "Users" + File.separator));
		chooser.setMultiSelectionEnabled(true);

		// Si validation du fichier
		if (chooser.showDialog(chooser, "Ouvrir") == 0) {
			File files[] = chooser.getSelectedFiles();

			for (int ind = 0 ; ind < files.length ; ind++) {
				File f = files[ind];
				inscription(f);
			}
			
			this.affichage();
			this.critEnreg = true;
		} else {
			this.fp.dispose();
			System.exit(0);
		}
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
	public void majBarreMemo(Niveau niveau) {
		// Calculer le nombre de jeunes dans cette catégorie
		int nb = 0;
		for (Jeune jj : revenuMemo)
			if (jj.getNiveau() == niveau)
				nb++;

		// Calculer le nombre total de jeunes inscrits dans cette catégorie
		int total = 0;
		for (Jeune jj : lesInscrits)
			if (jj.getNiveau() == niveau)
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
	public void majBarreBalise(Niveau niveau) {
		// Calculer le nombre de jeunes dans cette catégorie
		int nb = 0;
		for (Jeune jj : revenuBalises)
			if (jj.getNiveau() == niveau)
				nb++;

		// Calculer le nombre total de jeunes inscrits dans cette catégorie
		int total = 0;
		for (Jeune jj : lesInscrits)
			if (jj.getNiveau() == niveau)
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

	/**
	 * Récupérer les réponses inscrites dans une ligne de fichier et 
	 * les affectées à un jeune.
	 * @param j Le jeune dont ce sont les réponses
	 * @param row La ligne du fichier excel qui contient les réponses
	 */
	private void recupReponses(Jeune j, Row row) {
		for (int i = 0 ; i < this.nbMemo ; i++) {
			String rep = row.getCell(i+1).getStringCellValue();
			j.getLesReponses().put("memo"  + i, rep);
		}
		
		for (int i = 0 ; i < this.nbBalise ; i++) {
			String rep = row.getCell(i+1+this.nbMemo).getStringCellValue();
			j.getLesReponses().put("balise" + i, rep);
		}
		
		for (int i = 0 ; i < this.nbBalise ; i++) {
			String rep = row.getCell(i+1+this.nbMemo+this.nbBalise).getStringCellValue();
			j.getLesReponses().put("maniabilite" + i, rep);
		}
	}
	
	/**
	 * Initialiser un projet.
	 * @param fp La fenêtre principale
	 */
	private void initialiserProjet(FenetrePrincipale fp) {
		this.fp = fp;
		this.nbBalise = 4;
		this.nbMemo = 2;
		this.reponses = new HashMap<>();
		this.lesInscrits = new ArrayList<>();
		this.gains = new HashMap<>();
		this.ficheGains = new FicheGains(this);
		this.ficheParametre = new FicheParam(this);
		this.ficheReponse = new FicheReponse(this);
		this.conforme = true;
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
	}
	
	/**
	 * Initialiser la HashMap alphabet.
	 */
	private void initialiserAlphabet() {
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
	}
	
	private boolean dejaIscrit(Jeune j) {
		for (Jeune je : this.lesInscrits) {
			if (je.getNumero() == j.getNumero())
				return true;
		}
		return false;
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

	/**
	 * Convertir une date (chaine de caractères) en date.
	 * @param date La date à convertir.
	 * @return
	 */
	protected static Date convertir(String date) {
		String[] param = date.split("/");
		return new Date(
				Integer.parseInt(param[2]),
				Integer.parseInt(param[1]),
				Integer.parseInt(param[0]));
	}
	
	

}

