package modele;

import controleur.ajouter.ActionModifierFiche;
import controleur.classement.ActionAfficherCouleur;
import controleur.classement.ActionExporterCouleur;
import controleur.commencer.ActionValiderDemarrer;
import controleur.scores.ActionModifierScore;
import controleur.suppr.ActionAnnulerSuppr;
import controleur.suppr.ActionValiderSuppr;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vue.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;

public class Projet {

    public static final HashMap<String, Integer> alphabet = new HashMap<>();
    /**
     * La fenêrte principale associée.
     */
    private FenetrePrincipale fp;
    /**
     * La liste des inscrits.
     */
    private ArrayList<Jeune> lesInscrits;
    /**
     * Le nom du fichier du critérium.
     */
    private String nomFichierCrit;
    /**
     * La liste des gains pour chaque épreuve.
     */
    private HashMap<String, Integer> gains;
    /**
     * La liste des réponses.
     */
    private HashMap<Niveau, Reponses> reponses;
    /**
     * Le nombre de balises.
     */
    private int nbBalise;
    /**
     * Le nombre de mémo orinatation.
     */
    private int nbMemo;
    /**
     * Le nombre de segments par zone de maniabilité.
     */
    private int nbSegment;
    /**
     * Le nombre de crircuit différent d'orientation.
     */
    private int nbCircuit;
    /**
     * Le nombre de balises orinatation.
     */
    private int nbOrientation;
    /**
     * La fenêtre des points gagnés par épreuve.
     */
    private FicheGains ficheGains;
    /**
     * Fiche paramètre.
     */
    private FicheParam ficheParametre;
    /**
     * Fiche réponse.
     */
    private HashMap<Niveau, FicheReponse> ficheReponse;
    /**
     * Vrai tant que le critérium n'a pas été modifié.
     */
    private Boolean critEnreg;
    /**
     * Nombre de jeunes revenus des mémos.
     */
    private ArrayList<Jeune> revenuMemo;
    private JLabel nombreArriveMemo;
    /**
     * Nombre de jeunes revenus des balises.
     */
    private ArrayList<Jeune> revenuBalises;
    private JLabel nombreArriveBalise;
    /**
     * ProgressBar des jeunes revenus.
     */
    private JProgressBar barreMemo;
    private JProgressBar barreBalise;
    /**
     * Classement du critérium.
     */
    private HashMap<Niveau, SortedSet<Jeune>> classement;
    /**
     * Les fiches classements
     */
    private FicheClassement fcVert, fcBleu, fcRouge, fcNoir;
    private String dossier;

    //////////////////
    // CONSTRUCTEUR //
    //////////////////

    /**
     * Construire un projet.
     *
     * @param fp La fenêtre principale
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
    }

    ////////////////////////
    ///METHODES STATIQUES///
    ////////////////////////

    /**
     * Récupérer un club à partir de sa lettre.
     *
     * @param lettre La lettre du club
     * @return Le club correspondant
     */
    private static Club recupClub(String lettre) throws ClubInconnuException {
        switch (lettre) {
            case "A":
                return Club.A;
            case "B":
                return Club.B;
            case "C":
                return Club.C;
            case "D":
                return Club.D;
            case "E":
                return Club.E;
            case "F":
                return Club.F;
            case "G":
                return Club.G;
            case "H":
                return Club.H;
            case "I":
                return Club.I;
            case "J":
                return Club.J;
            case "K":
                return Club.K;
            case "L":
                return Club.L;
            case "N":
                return Club.N;
            case "O":
                return Club.O;
            case "P":
                return Club.P;
            case "Q":
                return Club.Q;
            default:
                throw new ClubInconnuException();
        }
    }

    /**
     * Récupérer un club à partir de son nom.
     *
     * @param nom Le nom du club
     * @return Le club correspondant
     */
    public static Club toClub(String nom) {
        switch (nom) {
            case "C S MUNICIPAL SEYNOIS":
                return Club.A;
            case "UNION CYCLISTE PEDESTRE LONDAISE":
                return Club.B;
            case "CYCLO CLUB ARCOIS":
                return Club.C;
            case "CYCLO CLUB LUCOIS":
                return Club.D;
            case "VELO RANDONNEUR CANTONAL":
                return Club.E;
            case "VELO VERT FLAYOSCAIS":
                return Club.F;
            case "VELO SPORT CYCLO HYEROIS":
                return Club.G;
            case "LA VALETTE CYCLOTOURISME":
                return Club.H;
            case "VELO CLUB SIX-FOURS":
                return Club.I;
            case "CRO ROIS TEAM":
                return Club.J;
            case "UFOLEP VELO CLUB FARLEDOIS":
                return Club.K;
            case "VELO CLUB NANS LES PINS LA STE BAUME":
                return Club.L;
            default:
                System.exit(0);
                return null;
        }
    }

    /**
     * Convertir un entier en date (la date de repère est le 01/01/1900.
     *
     * @param i l'entier à convertir
     * @return La date correspondante
     */
    private static Date convertir(int i) {
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

        int m;
        int b = 0;
        int j;
        if (a % 4 == 0)
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
     *
     * @param date La date à convertir.
     * @return La date correpondante à la chaîne de caractères
     */
    public static Date convertir(String date) throws MauvaisFormatDateException {
        try {
            String[] param = date.split("/");
            return new Date(
                    Integer.parseInt(param[2]),
                    Integer.parseInt(param[1]),
                    Integer.parseInt(param[0]));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new MauvaisFormatDateException();
        }
    }

    /**
     * Vérifier su'un fichier est un fichier excel.
     *
     * @param nomFic Le nom du fichier
     * @return Vrai si le fichier est un fichier excel, faux sinon
     */
    private static boolean fichierExcel(String nomFic) {
        return (nomFic.endsWith(".xls") || nomFic.endsWith(".xlsx") || nomFic.endsWith(".xlsm") ||
                nomFic.endsWith(".xlt") || nomFic.endsWith(".xltm") || nomFic.endsWith(".xla") ||
                nomFic.endsWith(".xlam"));
    }

    ////////////////////////
    // GETTERS ET SETTERS //
    ////////////////////////

    /**
     * Récupérer le nombre de balises.
     *
     * @return Le nombre de balises
     */
    public int getNbBalise() {
        return this.nbBalise;
    }

    /**
     * Modifier le nombre de balises.
     *
     * @param balise Le nouveau nombre de balises
     */
    public void setNbBalise(int balise) {
        this.nbBalise = balise;
    }

    /**
     * Récupérer le nombre de mémos.
     *
     * @return Le nombre de mémos
     */
    public int getNbMemo() {
        return this.nbMemo;
    }

    /**
     * Modifier le nombre de mémos.
     *
     * @param memo Le nouveau nombre de mémo
     */
    public void setNbMemo(int memo) {
        this.nbMemo = memo;
    }

    /**
     * Récupérer la fenêtre principale.
     *
     * @return La fenêtre principale
     **/
    public FenetrePrincipale getFp() {
        return fp;
    }

    /**
     * Récupérer la liste des inscrits.
     *
     * @return La liste des inscrits
     **/
    public ArrayList<Jeune> getLesInscrits() {
        return lesInscrits;
    }

    /**
     * Récupérer la liste des points à gagner.
     *
     * @return La liste des points pour chaque épreuve
     */
    public HashMap<String, Integer> getGains() {
        return gains;
    }

    /**
     * Récupérer la liste des réponses.
     *
     * @return La liste des réponses pour chaque question
     */
    public HashMap<Niveau, Reponses> getReponses() {
        return reponses;
    }

    /**
     * Récupérer la fiche des réponses.
     *
     * @return La fiche des réponses
     */
    public HashMap<Niveau, FicheReponse> getFicheReponse() {
        return ficheReponse;
    }

    /**
     * Récupérer le bouléen d'enregistrement du crit
     *
     * @return Le bouléen d'enregistrement du crit
     */
    public Boolean getCritEnreg() {
        return this.critEnreg;
    }

    /**
     * Modifier le bouléen d'enregistrement du crit.
     *
     * @param enreg Le nouveau bouléen d'enregistrement du crit
     */
    public void setCritEnreg(Boolean enreg) {
        this.critEnreg = enreg;
    }

    /**
     * Récupérer le nombre de balises d'orientation.
     *
     * @return Le nombre de balises d'orienttaion
     */
    public int getNbOrientation() {
        return this.nbOrientation;
    }

    /**
     * Modifier le nombre de balises d'orientation.
     *
     * @param nbOrientation Le nouveau nombre de mémo orientation
     */
    public void setNbOrientation(int nbOrientation) {
        this.nbOrientation = nbOrientation;
    }

    /**
     * Récupérer le nombre de segments par zone de maniabilité.
     *
     * @return Le nombre de segments par zone de maniabilité
     */
    public int getNbSegment() {
        return nbSegment;
    }

    /**
     * Récupérer toutes les fiches classements
     *
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

    /**
     * Récupérer le nombre de crcuits différents pour l'orientation.
     *
     * @return Le nombre de circuits différents pour l'orientation
     */
    public int getNbCircuit() {
        return this.nbCircuit;
    }

    /**
     * Modifier le nombre de circuits différents pour l'orientation.
     *
     * @param c Le noveau nombre de circuit différents pour l'orientation
     */
    public void setNbCircuit(int c) {
        this.nbCircuit = c;
    }

    public HashMap<Niveau, SortedSet<Jeune>> getClassement() {
        return classement;
    }
/////////////////////////////////////////
    ///AFFICHER LES FENETRES DE PARAMETRES///
    /////////////////////////////////////////

    /**
     * Afficher la fiche des paramètres.
     */
    public void afficherFicheParametre() {
        this.ficheParametre.show();
        //	this.ficheParametre.setNbBalises("" + this.nbBalise);
        //	this.ficheParametre.setNbMemos("" + this.nbMemo);
    }

    /**
     * Afficher la fiche pour définir les points gagnés.
     */
    public void afficherFicheGains() {
        this.ficheGains.show();
    }

    /**
     * Afficher la fiche des réponses attendues.
     */
    public void afficherFenetreReponses() {
        for (Niveau niv : Niveau.values()) {
            if (ficheReponse.get(niv) == null) {
                FicheReponse fr = new FicheReponse(this, niv);
                this.getFp().getDesktop().add(fr);
                this.ficheReponse.put(niv, fr);
                fr.invaliderModif();
            }
            ficheReponse.get(niv).invaliderModif();
            ficheReponse.get(niv).setVisible(true);
        }
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
            if (licencie.getNiveau() == Niveau.N) {
                JButton btn = new JButton(licencie.toString());
                btn.addActionListener(new ActionModifierFiche(licencie));
                btn.setBackground(new Color(0, 0, 0));
                btn.setForeground(new Color(255, 255, 255));
                this.fp.getGrilleLicencies().add(btn);
            }
        }

        this.fp.fermerModeAdmin();
        this.fp.ouvrirModeAdmin();
    }

    ///////////////////////////////////////
    ///GESTION DES LICENCIES ET INSCRITS///
    ///////////////////////////////////////

    /**
     * Ajouter un inscrit à la liste triée.
     *
     * @param licencie Le licencié que l'on ajoute
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
     * Supprimer un licencié.
     *
     * @param numero Le numéro du jeune à supprimer
     **/
    public void supprimerInscrit(String numero) {
        for (Jeune licencie : this.lesInscrits) {
            if (licencie.toString().equals(numero)) {
                this.lesInscrits.remove(licencie);
                break;
            }
        }
    }

    /**
     * Valider la suppression des licenciés.
     *
     * @param list La liste des boutons
     * @param jif  La fiche de suppression d'une couleur donnée
     */
    public void validerSuppression(ArrayList<JCheckBox> list, JInternalFrame jif) {
        for (JCheckBox checkBox : list) {
            if (checkBox.isSelected()) {
                this.supprimerInscrit(checkBox.getText());
                this.critEnreg = false;
            }
        }
        jif.dispose();
        this.affichage();
    }

    /**
     * Afficher la fenêtre de suppression des inscrits.
     */
    public void afficherFenetreSuppression() {
        if (this.lesInscrits.size() > 0) {
            // Créer la fenêtre interne
            JInternalFrame jif = new JInternalFrame("Supprimer un / des inscrit(s)");
            this.fp.getDesktop().add(jif);
            jif.getContentPane().setLayout(new BorderLayout());

            JPanel panel1 = new JPanel(new GridLayout(20, 10));
            JPanel panel2 = new JPanel(new FlowLayout());

            // Afficher les numéros de tous les licenciés
            ArrayList<JCheckBox> list = new ArrayList<>();
            for (Jeune licencie : lesInscrits) {
                JCheckBox btn = new JCheckBox(licencie.toString());
                panel1.add(btn);
                list.add(btn);
            }

            // Ajouter les boutons
            JButton btnValider = new JButton("Valider");
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
     * Afficher la grille des sélectionnés.
     *
     * @param niveau La couleur séletionnée à afficher
     **/
    public void affichageSelectionnes(Niveau niveau) {
        // Masquer toutes les fenêtres internes (si changement de couleur par exemple)
        JInternalFrame[] jifs = this.getFp().getDesktop().getAllFrames();
        for (JInternalFrame jif : jifs)
            jif.hide();

        // Récupérer les inscrits du niveau souhaité
        ArrayList<Jeune> inscrits = new ArrayList<>();
        for (Jeune licencie : this.getLesInscrits())
            if (licencie.getNiveau() == niveau)
                inscrits.add(licencie);

        if (inscrits.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this.fp,
                    "Aucun inscrit dans cette catégorie.");
            return;
        }

        // Afficher le bouton d'enregistrement
        this.getFp().getBtnsCrit().setVisible(true);

        // Vider la grille dse sélectionnés
        this.fp.getGrilleInscrits().removeAll();

        // Afficher la grille des sélectionnés
        for (Jeune licencie : inscrits) {
            JButton btn = new JButton(licencie.toString());
            btn.addActionListener(new ActionModifierScore(licencie, this));
            btn.setBackground(niveau.getColor());
            btn.setForeground(Color.WHITE);

            this.getFp().getGrilleInscrits().add(btn);
        }
        this.getFp().getGrilleInscrits().setVisible(true);

        // Afficher les barres de progression
        this.fp.getZoneProg().setVisible(true);
        this.majBarreBalise(niveau);
        this.majBarreMemo(niveau);

        this.getFp().repaint();
    }

    ////////////////////
    ///ENREGSITREMENT///
    ////////////////////

    /**
     * Enregistrer l'ensemble des inscrits dans un fichier excel.
     */
    public void enregistrer() {
        if (this.nomFichierCrit == null) {
            this.enregistrerSous();
            return;
        }

        // Création du wokrbook
        XSSFWorkbook wb = new XSSFWorkbook();

        // Création de la feuille
        Sheet sheet = wb.createSheet("Inscriptions");

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


        //////////////////////////////
        ///FEUILLE 1 : INSCRIPTIONS///
        //////////////////////////////

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
        row = sheet.createRow(3);

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
        int colonne;

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
                case V:
                    pastille.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                    break;
                case B:
                    pastille.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                    break;
                case R:
                    pastille.setFillForegroundColor(IndexedColors.RED.getIndex());
                    break;
                case N:
                    pastille.setFillForegroundColor(IndexedColors.BLACK.getIndex());
                    break;
            }
            row.getCell(colonne++).setCellStyle(pastille);
        }

        // Ajouter le style des cellules
        // La première ligne
        row = sheet.getRow(3);
        for (int ind = 0; ind < 15; ind++) {
            row.getCell(ind).setCellStyle(style2);
        }

        // Les colonnes 1, 2, 3, 9, 11, 12
        for (int ind = 4; ind < this.lesInscrits.size() + 4; ind++) {
            row = sheet.getRow(ind);
            row.getCell(0).setCellStyle(style3);
            row.getCell(1).setCellStyle(style3);
            row.getCell(2).setCellStyle(style3);
            row.getCell(8).setCellStyle(style3);
            row.getCell(10).setCellStyle(style3);
            row.getCell(11).setCellStyle(style3);
        }

        // Les autres colonnes
        for (int ind = 4; ind < this.lesInscrits.size() + 4; ind++) {
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
        for (int ind = 0; ind < 14; ind++) {
            sheet.autoSizeColumn(ind, true);
        }

        ///////////////////////////
        ///FEUILLE 2 : RESULTATS///
        ///////////////////////////

        int indColMax = 6 + this.nbMemo + this.nbBalise * (1 + this.nbSegment) + this.nbOrientation;
        // Ecrire les réponses de chaque inscrit
        sheet = wb.createSheet("Résultats");

        // Fusionner la cellules des 3 première lignes
        cra = new CellRangeAddress(0, 2, 0, indColMax);
        sheet.addMergedRegion(cra);

        // Ecrire les titre de la feuille
        row = sheet.createRow(0);
        row.createCell(0);
        row.getCell(0).setCellValue("TABLEAU RESULTATS");
        row.getCell(0).setCellStyle(style1);

        // Tracer la bordure droite sur la  première case
        row.createCell(indColMax);
        row.getCell(indColMax).setCellStyle(style1);
        row = sheet.createRow(1);
        row.createCell(indColMax);
        row.getCell(indColMax).setCellStyle(style1);
        row = sheet.createRow(2);
        row.createCell(indColMax);
        row.getCell(indColMax).setCellStyle(style1);

        ligne = 3;
        colonne = 0;

        // Ecrire les en-têtes
        row = sheet.createRow(ligne);
        cra = new CellRangeAddress(ligne, ligne + 1, colonne, colonne);
        sheet.addMergedRegion(cra);
        row.createCell(colonne);
        row.getCell(colonne++).setCellValue("Numéro");
        cra = new CellRangeAddress(ligne, ligne + 1, colonne, colonne);
        sheet.addMergedRegion(cra);
        row.createCell(colonne);
        row.getCell(colonne++).setCellValue("/");
        for (int ind = 1; ind <= this.nbMemo; ind++) {
            cra = new CellRangeAddress(ligne, ligne + 1, colonne, colonne);
            sheet.addMergedRegion(cra);
            row.createCell(colonne);
            row.getCell(colonne++).setCellValue("Mémo" + ind);
        }
        for (int ind = 1; ind <= this.nbBalise; ind++) {
            cra = new CellRangeAddress(ligne, ligne + 1, colonne, colonne);
            sheet.addMergedRegion(cra);
            row.createCell(colonne);
            row.getCell(colonne++).setCellValue("Balise" + ind);
        }
        for (int ind = 1; ind <= this.nbBalise; ind++) {
            cra = new CellRangeAddress(ligne, ligne, colonne, colonne + this.nbSegment - 1);
            sheet.addMergedRegion(cra);
            row.createCell(colonne);
            row.getCell(colonne++).setCellValue("Balise" + ind);
            row.createCell(colonne++);
        }
        cra = new CellRangeAddress(ligne, ligne, colonne, colonne + 3);
        sheet.addMergedRegion(cra);
        row.createCell(colonne);
        row.getCell(colonne++).setCellValue("Trousse mécanique");
        row.createCell(colonne++);
        row.createCell(colonne++);
        row.createCell(colonne++);

        for (int ind = 1; ind <= this.nbOrientation; ind++) {
            cra = new CellRangeAddress(ligne, ligne + 1, colonne, colonne);
            sheet.addMergedRegion(cra);
            row.createCell(colonne);
            row.getCell(colonne++).setCellValue("Orientation" + ind);
        }
        cra = new CellRangeAddress(ligne, ligne + 1, colonne, colonne);
        sheet.addMergedRegion(cra);
        row.createCell(colonne);
        row.getCell(colonne++).setCellValue("N° Parcours");

        ligne++;
        colonne = 0;
        row = sheet.createRow(ligne);

        // Créer les cellules vides
        for (int ind = 0; ind < 2 + this.nbMemo + this.nbBalise; ind++)
            row.createCell(colonne++);

        // ?????????????????
        ligne++;
        for (int ind = 1; ind <= this.nbBalise; ind++)
            for (int in = 1; in <= this.nbSegment; in++) {
                row.createCell(colonne);
                row.getCell(colonne++).setCellValue("Segment" + in);
            }

        row.createCell(colonne);
        row.getCell(colonne++).setCellValue("Dérive chaîne");
        row.createCell(colonne);
        row.getCell(colonne++).setCellValue("Jeu de clés");
        row.createCell(colonne);
        row.getCell(colonne++).setCellValue("Réparation crevaison");
        row.createCell(colonne);
        row.getCell(colonne++).setCellValue("Câbles");

        // Créer les cellules vides
        for (int ind = 0; ind < indColMax; ind++)
            row.createCell(colonne++);

        row = sheet.createRow(ligne++);
        // Pour chaque inscrit, écrire les réponses
        for (Jeune je : this.lesInscrits) {
            colonne = 0;
            row.createCell(colonne);
            row.getCell(colonne++).setCellValue(je.toString());
            row.createCell(colonne);
            row.getCell(colonne++).setCellValue("/");
            for (int ind = 0; ind < this.nbMemo; ind++) {
                row.createCell(colonne);
                row.getCell(colonne++).setCellValue(je.getLesReponses().get("memo" + ind));
            }
            for (int ind = 0; ind < this.nbBalise; ind++) {
                row.createCell(colonne);
                row.getCell(colonne++).setCellValue(je.getLesReponses().get("balise" + ind));
            }
            for (int ind = 0; ind < this.nbBalise; ind++) {
                for (int in = 1; in <= this.nbSegment; in++) {
                    row.createCell(colonne);
                    row.getCell(colonne++).setCellValue(je.getLesReponses().get("maniabilite" + ind + "_" + in));
                }
            }
            row.createCell(colonne);
            row.getCell(colonne++).setCellValue(je.getLesReponses().get("chaine"));
            row.createCell(colonne);
            row.getCell(colonne++).setCellValue(je.getLesReponses().get("clefs"));
            row.createCell(colonne);
            row.getCell(colonne++).setCellValue(je.getLesReponses().get("reparation"));
            row.createCell(colonne);
            row.getCell(colonne++).setCellValue(je.getLesReponses().get("cables"));

            for (int ind = 0; ind < this.nbOrientation; ind++) {
                row.createCell(colonne);
                row.getCell(colonne++).setCellValue(je.getLesReponses().get("orientation" + ind));
            }
            row.createCell(colonne);
            row.getCell(colonne++).setCellValue(je.getNumParours());

            row = sheet.createRow(ligne++);
        }

        // Ajuster la largeur des colonnes
        for (int ind = 0; ind < indColMax; ind++) {
            sheet.autoSizeColumn(ind, true);
        }

        sheet.setColumnWidth(indColMax, sheet.getColumnWidth(indColMax - 1));

        // Styles de la feuille des résultats
        ligne = 3;
        // La ligne des en-têtes
        row = sheet.getRow(ligne++);
        for (int ind = 0; ind <= indColMax; ind++) {
            row.getCell(ind).setCellStyle(style2);
        }
        row = sheet.getRow(ligne++);
        for (int ind = 0; ind <= indColMax; ind++) {
            row.getCell(ind).setCellStyle(style2);
        }

        // Toutes les colonnes
        for (int ind = 4; ind < this.lesInscrits.size() + 5; ind++) {
            row = sheet.getRow(ind);
            for (int col = 0; col <= indColMax; col++)
                row.getCell(col).setCellStyle(style4);
        }

        // Les colonnes 1, 2, 4, 8, 10, 12, 14, 16
        for (int ind = 4; ind < this.lesInscrits.size() + 5; ind++) {
            int col = 0;
            row = sheet.getRow(ind);
            row.getCell(col++).setCellStyle(style3);
            row.getCell(col++).setCellStyle(style3);
            row.getCell(col).setCellStyle(style3);
            col += this.nbMemo;
            row.getCell(col).setCellStyle(style3);
            col += this.nbBalise;
            for (int ii = 0; ii < this.nbBalise; ii++) {
                row.getCell(col).setCellStyle(style3);
                col += this.nbSegment;
            }
            col += 4;
            row.getCell(col).setCellStyle(style3);
        }

        ////////////////////////////
        ///FEUILLE 3 : PARAMETRES///
        ////////////////////////////

        // Création de la feuille
        sheet = wb.createSheet("Paramètres");

        // Initialiser les numéros de ligne et de colonne
        ligne = 0;

        // Fusionner la cellules des 3 première lignes
        cra = new CellRangeAddress(0, 1, 0, 5);
        sheet.addMergedRegion(cra);

        // Ecrire les titre de la feuille
        row = sheet.createRow(0);
        row.createCell(0);
        row.getCell(0).setCellValue("PARAMETRES");
        row.getCell(0).setCellStyle(style1);

        // Tracer la bordure droite sur la  première case
        row.createCell(3);
        row.getCell(3).setCellStyle(style1);
        row = sheet.createRow(1);
        row.createCell(3);
        row.getCell(3).setCellStyle(style1);
        row = sheet.createRow(2);
        row.createCell(3);
        row.getCell(3).setCellStyle(style1);

        ligne = 2;
        // Ecrire le nombre de balise
        row = sheet.createRow(ligne++);

        row.createCell(0);
        row.getCell(0).setCellValue("Nombre de balise");
        row.getCell(0).setCellStyle(style2);

        row.createCell(1);
        row.getCell(1).setCellValue(this.nbBalise);
        row.getCell(1).setCellStyle(style4);

        // Ecrire le nombre de mémo
        row = sheet.createRow(ligne++);
        row.createCell(0);
        row.getCell(0).setCellValue("Nombre de mémo");
        row.getCell(0).setCellStyle(style2);

        row.createCell(1);
        row.getCell(1).setCellValue(this.nbMemo);
        row.getCell(1).setCellStyle(style4);

        // Ecrire le nombre de segments
        row = sheet.createRow(ligne++);
        row.createCell(0);
        row.getCell(0).setCellValue("Nombre de segments");
        row.getCell(0).setCellStyle(style2);

        row.createCell(1);
        row.getCell(1).setCellValue(this.nbSegment);
        row.getCell(1).setCellStyle(style4);

        // Ecrire le nombre de balises d'orientation par circuits
        row = sheet.createRow(ligne++);
        row.createCell(0);
        row.getCell(0).setCellValue("Nombre d'orientation");
        row.getCell(0).setCellStyle(style2);

        row.createCell(1);
        row.getCell(1).setCellValue(this.nbOrientation);
        row.getCell(1).setCellStyle(style4);

        // Ecrire le nombre de circuits d'orientation
        row = sheet.createRow(ligne++);
        row.createCell(0);
        row.getCell(0).setCellValue("Nombre de circuits");
        row.getCell(0).setCellStyle(style2);

        row.createCell(1);
        row.getCell(1).setCellValue(this.nbCircuit);
        row.getCell(1).setCellStyle(style4);

        // Ecrire les points à gagner
        row = sheet.createRow(ligne++);
        row.createCell(0);
        row.getCell(0).setCellValue("Nombre de points gagnés");
        row.getCell(0).setCellStyle(style2);

        // Ecrire les points par balise trouvée
        row = sheet.createRow(ligne++);
        row.createCell(1);
        row.getCell(1).setCellValue("Balise");
        row.getCell(1).setCellStyle(style3);

        row.createCell(2);
        row.getCell(2).setCellValue(gains.get("baliseTrouvee"));
        row.getCell(2).setCellStyle(style4);

        // Ecrire les points par réponse correcte aux balises
        row = sheet.createRow(ligne++);
        row.createCell(1);
        row.getCell(1).setCellValue("Question balise");
        row.getCell(1).setCellStyle(style3);

        row.createCell(2);
        row.getCell(2).setCellValue(gains.get("baliseCorrecte"));
        row.getCell(2).setCellStyle(style4);

        // Ecrire les points par mémo trouvé
        row = sheet.createRow(ligne++);
        row.createCell(1);
        row.getCell(1).setCellValue("Mémo");
        row.getCell(1).setCellStyle(style3);
        row.createCell(2);
        row.getCell(2).setCellValue(gains.get("memoTrouve"));
        row.getCell(2).setCellStyle(style4);

        // Ecrire les points par réponse correcte aux mémos
        row = sheet.createRow(ligne++);
        row.createCell(1);
        row.getCell(1).setCellValue("Question mémo");
        row.getCell(1).setCellStyle(style3);

        row.createCell(2);
        row.getCell(2).setCellValue(gains.get("memoCorrect"));
        row.getCell(2).setCellStyle(style4);

        // Ecrire les points par réponse correcte aux balises d'orientation
        row = sheet.createRow(ligne++);
        row.createCell(1);
        row.getCell(1).setCellValue("Balise orientation");
        row.getCell(1).setCellStyle(style3);

        row.createCell(2);
        row.getCell(2).setCellValue(gains.get("orientation"));
        row.getCell(2).setCellStyle(style4);

        // Ecrire les points par zone de maniabilité
        row = sheet.createRow(ligne++);
        row.createCell(1);
        row.getCell(1).setCellValue("Maniabilité");
        row.getCell(1).setCellStyle(style3);

        row.createCell(2);
        row.getCell(2).setCellValue(gains.get("maniabilite"));
        row.getCell(2).setCellStyle(style4);

        // Ecrire les points pour la question mécanique
        row = sheet.createRow(ligne++);
        row.createCell(1);
        row.getCell(1).setCellValue("Question mécanique");
        row.getCell(1).setCellStyle(style3);

        row.createCell(2);
        row.getCell(2).setCellValue(gains.get("mecanique"));
        row.getCell(2).setCellStyle(style4);

        // Ecrire les points pour la trousse
        row = sheet.createRow(ligne++);
        row.createCell(1);
        row.getCell(1).setCellValue("Trousse mécanique");
        row.getCell(1).setCellStyle(style3);

        // Points pour le dérinve chaine
        row.createCell(2);
        row.getCell(2).setCellValue("Dérive chaîne");
        row.getCell(2).setCellStyle(style3);

        row.createCell(3);
        row.getCell(3).setCellValue(gains.get("chaine"));
        row.getCell(3).setCellStyle(style4);

        // Points pour les câbles
        row = sheet.createRow(ligne++);
        row.createCell(2);
        row.getCell(2).setCellValue("Câbles");
        row.getCell(2).setCellStyle(style3);

        row.createCell(3);
        row.getCell(3).setCellValue(gains.get("cables"));
        row.getCell(3).setCellStyle(style4);

        // Points pour les clés
        row = sheet.createRow(ligne++);
        row.createCell(2);
        row.getCell(2).setCellValue("Jeu de clés");
        row.getCell(2).setCellStyle(style3);

        row.createCell(3);
        row.getCell(3).setCellValue(gains.get("clefs"));
        row.getCell(3).setCellStyle(style4);

        // Points pour le nécéssaire de réparation
        row = sheet.createRow(ligne++);
        row.createCell(2);
        row.getCell(2).setCellValue("Réparation");
        row.getCell(2).setCellStyle(style3);

        row.createCell(3);
        row.getCell(3).setCellValue(gains.get("reparation"));
        row.getCell(3).setCellStyle(style4);

        // Ecrire les réponses attendues pour chaque question
        row = sheet.createRow(ligne++);
        row.createCell(0);
        row.getCell(0).setCellValue("Réponses aux questions");
        row.getCell(0).setCellStyle(style2);

        int col = 2;
        for (Niveau niv : Niveau.values()) {
            row.createCell(col);
            row.getCell(col).setCellValue("" + niv.getCouleur());
            row.getCell(col).setCellStyle(style2);
            col++;
        }


        // Ecrire les réponses aux mémos de chaque niveau
        for (int ind = 1; ind <= nbMemo; ind++) {
            row = sheet.createRow(ligne++);
            row.createCell(1);
            row.getCell(1).setCellValue("Mémo " + ind);
            row.getCell(1).setCellStyle(style3);

            int num = ind - 1;
            col = 2;
            for (Niveau niv : Niveau.values()) {
                row.createCell(col);
                row.getCell(col).setCellValue(this.reponses.get(niv).get("memo" + num));
                row.getCell(col).setCellStyle(style4);
                col++;
            }
        }

        // Ecrire les réponses aux balises de chaque niveau
        for (int ind = 1; ind <= nbBalise; ind++) {
            row = sheet.createRow(ligne++);
            row.createCell(1);
            row.getCell(1).setCellValue("Balise " + ind);
            row.getCell(1).setCellStyle(style3);


            int num = ind - 1;
            col = 2;
            for (Niveau niv : Niveau.values()) {
                row.createCell(col);
                row.getCell(col).setCellValue(this.reponses.get(niv).get("balise" + num));
                row.getCell(col).setCellStyle(style4);
                col++;
            }
        }

        row = sheet.createRow(ligne++);
        // Ecrire les circuits d'orientation de chaque niveau
        for (int ind = 1; ind <= nbCircuit; ind++) {
            row.createCell(1);
            row.getCell(1).setCellValue("Circuit " + ind);
            row.getCell(1).setCellStyle(style3);

            for (int ii = 1; ii <= this.nbOrientation; ii++) {
                col = 2;
                for (Niveau niv : Niveau.values()) {
                    row.createCell(col);
                    row.getCell(col).setCellValue(this.reponses.get(niv).get("orientation" + ind + "_" + ii));
                    row.getCell(col).setCellStyle(style4);
                    col++;
                }

                row = sheet.createRow(ligne++);
            }
        }

        // Ajuster la largeur des colonnes
        for (int ind = 0; ind < 3; ind++) {
            sheet.autoSizeColumn(ind, true);
        }

        // Ecrire le fichier excel
        try {
            FileOutputStream fos = new FileOutputStream(this.nomFichierCrit);
            wb.write(fos);
            fos.close();
            wb.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this.getFp(),
                    "Erreur dans d'enregistrement des licenciés, " +
                            "fermer le fichier s'il est ouvert et recommencer");
            return;
        }

        this.critEnreg = true;

        JButton button = new JButton("Enregistrement réussi");
        button.setBackground(Color.GREEN);
        PopupFactory factory = PopupFactory.getSharedInstance();
        final Popup popup = factory.getPopup(this.fp, button,
                (int) this.fp.getLocation().getX() + this.fp.getWidth() / 2,
                (int) this.fp.getLocation().getY() + this.fp.getHeight() / 2);
        popup.show();
        ActionListener hider = ((ActionEvent e) -> popup.hide());
        // Masquer le pop up après 3 seconces
        Timer timer = new Timer(3000, hider);
        timer.start();
    }

    /**
     * Enregistrer l'ensemble des licenciées dans un fichier excel dans un nouveau fichier.
     */
    public void enregistrerSous() {
        JFileChooser chooser = new JFileChooser();
        this.choisirDossier(chooser);
        chooser.setFileFilter(new FileNameExtensionFilter("fichier excel", "xls", "xlsx", "xlsm", "xltx", "xlt", "xltm", "xla", "xlam"));

        if (chooser.showDialog(chooser, "Ouvrir") == 0) {
            // Récupérer le fichier d'enregistrement
            File fichier = chooser.getSelectedFile();

            String nom = fichier.getAbsolutePath();

            if (!nom.contains(".")) {
                this.nomFichierCrit = nom + ".xlsx";
                this.enregistrer();
            } else if (fichierExcel(nom)) {
                this.nomFichierCrit = nom;
                this.enregistrer();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this.fp,
                        "Veuillez sélectionner un fichier excel.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
                this.enregistrerSous();
            }
        }
    }

    ////////////////
    ///CLASSEMENT///
    ////////////////

    /**
     * Demander quels classements.
     */
    public void afficherQuelClassement() {
        JInternalFrame jif = new JInternalFrame("Classement", false, true, false, true);

        // Mise en forme de la fenetre
        jif.getContentPane().setLayout(new BorderLayout());

        // Check box
        JCheckBox checkVert = new JCheckBox("Vert");
        JCheckBox checkBleu = new JCheckBox("Bleu");
        JCheckBox checkRouge = new JCheckBox("Rouge");
        JCheckBox checkNoir = new JCheckBox("Noir");

        // Bouton
        JButton btnValider = new JButton("Valider");
        btnValider.addActionListener(new ActionAfficherCouleur(checkVert, checkBleu, checkRouge, checkNoir, this, jif));

        // Le panel des checkBox
        JPanel panel1 = new JPanel(new FlowLayout());
        // Le panel des boutons
        JPanel panel2 = new JPanel(new FlowLayout());

        // Ajouter les checkBox
        panel1.add(checkVert);
        panel1.add(checkBleu);
        panel1.add(checkRouge);
        panel1.add(checkNoir);

        // Ajouter les boutons
        panel2.add(btnValider);

        // Ajouter les panels à la fenetre
        jif.getContentPane().add(panel1, BorderLayout.CENTER);
        jif.getContentPane().add(panel2, BorderLayout.SOUTH);

        // Ajouter la fenetre à la fenetre prinicpale
        this.fp.getDesktop().add(jif);
        jif.setVisible(true);
        jif.pack();
    }

    /**
     * Demander quels classmeent.
     */
    public void exporterQuelClassement() {
        JInternalFrame jif = new JInternalFrame("Exporter classement", false, true, false, true);

        // Mise en forme de la fenetre
        jif.getContentPane().setLayout(new BorderLayout());

        // Check box
        JCheckBox checkVert = new JCheckBox("Vert");
        JCheckBox checkBleu = new JCheckBox("Bleu");
        JCheckBox checkRouge = new JCheckBox("Rouge");
        JCheckBox checkNoir = new JCheckBox("Noir");

        // Bouton
        JButton btnValider = new JButton("Valider");
        btnValider.addActionListener(new ActionExporterCouleur(checkVert, checkBleu, checkRouge, checkNoir, this, jif));

        // Le panel des checkBox
        JPanel panel1 = new JPanel(new FlowLayout());
        // Le panel des boutons
        JPanel panel2 = new JPanel(new FlowLayout());

        // Ajouter les checkBox
        panel1.add(checkVert);
        panel1.add(checkBleu);
        panel1.add(checkRouge);
        panel1.add(checkNoir);

        // Ajouter les boutons
        panel2.add(btnValider);

        // Ajouter les panels à la fenetre
        jif.getContentPane().add(panel1, BorderLayout.CENTER);
        jif.getContentPane().add(panel2, BorderLayout.SOUTH);

        // Ajouter la fenetre à la fenetre prinicpale
        this.fp.getDesktop().add(jif);
        jif.setVisible(true);
        jif.pack();
    }

    /**
     * Afficher le classement d'un niveau.
     *
     * @param niveau Le niveau dont on affiche le classement
     */
    public void classer(Niveau niveau) {
        // On vérifie que les fiches sont correctement remplies
        boolean ok = true;
        for (Jeune j : this.lesInscrits)
            if (j.getNiveau() == niveau) {
                ok = ok && j.getFiche2().toutRempli();
                if (!ok)
                    return;
            }
        // Calcul des scores et tri
        TreeSet<Jeune> set = new TreeSet<>((Jeune j1, Jeune j2) ->
                ((j2.getPoints() - j1.getPoints()) == 0) ? 1 : j2.getPoints() - j1.getPoints());
            this.classement.put(niveau, set);
        for (Jeune j : this.lesInscrits)
            if (j.getNiveau() == niveau) {
                j.modifierPoints(this.reponses.get(j.getNiveau()), this.nbBalise, this.nbOrientation,
                        this.nbMemo, this.nbSegment, this.gains, this.fp);
                classement.get(niveau).add(j);
            }

        // regarder s'il y a des égalités sur le podium
        List<Jeune> tmp = new ArrayList<>();
        for (Jeune j : classement.get(niveau))
                tmp.add(j);

        int position = 1;
        boolean egalite = false;
        for (int i = 0; i < tmp.size() - 1; i++)
            if (position > 3)
                break;
            else if (tmp.get(i).getPoints() == tmp.get(i + 1).getPoints()) {
                    new FicheBonus(this, tmp.get(i).getPoints(), classement.get(niveau), position, niveau);
                    egalite = true;
                    break;
                } else
                    position++;
        if (!egalite)
            this.afficherClassement(niveau, tmp);
    }

    public void afficherClassement(Niveau niveau, List<Jeune> classement) {
        // Afficher le classement
        switch (niveau) {
            case V:
              /*  if (fcVert != null)
                    fcVert.dispose();*/
                fcVert = new FicheClassement(this);
                fcVert.afficherCouleur(classement, Niveau.V);
                break;
            case B:
                /*if (fcBleu != null)
                    fcBleu.dispose();*/
                fcBleu = new FicheClassement(this);
                fcBleu.afficherCouleur(classement, Niveau.B);
                break;
            case R:
               /* if (fcRouge != null)
                    fcRouge.dispose();*/
                fcRouge = new FicheClassement(this);
                fcRouge.afficherCouleur(classement, Niveau.R);
                break;
            case N:
               /* if (fcNoir != null)
                    fcNoir.dispose();*/
                fcNoir = new FicheClassement(this);
                fcNoir.afficherCouleur(classement, Niveau.N);
                break;
        }
    }

    /**
     * Remplir un fichier de classement pour une couleur donnée.
     *
     * @param niveau La couleur de la catégorie à écrire dans le fihcier
     */
    public void exporterClassement(Niveau niveau) {
        // On vérifie que les fiches sont correctement remplies
        /*boolean ok = true;

        List<Jeune> tmp = new ArrayList<>();
        for (Jeune j : classement)
            if (j.getNiveau() == niveau)
                tmp.add(j);

        for (Jeune j : tmp)
            if (j.getNiveau() == niveau) {
                ok = ok && j.getFiche2().toutRempli();
                if (!ok) {
                    this.fp.getGrilleLicencies().setVisible(false);
                    return;
                }
            }
*/

        // Récupérer le fichier
        JFileChooser chooser = new JFileChooser();
        this.choisirDossier(chooser);
        chooser.setFileFilter(new FileNameExtensionFilter("fichier excel", "xls", "xlsx", "xlsm", "xltx", "xlt", "xltm", "xla", "xlam"));

        // Sélectionner un seul fichier
        chooser.setMultiSelectionEnabled(false);

        // Le fichier d'enregistrement des classements
        String nom;
        if (chooser.showDialog(chooser, "Exporter les classements") == 0) {
            // Récupérer le fichier d'enregistrement
            nom = chooser.getSelectedFile().getAbsolutePath();

            if (!fichierExcel(nom) && nom.contains(".")) {
                javax.swing.JOptionPane.showMessageDialog(this.getFp(),
                        "Veuillez sélectionner un fichier excel.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
                this.exporterClassement(niveau);
            }
            if (!nom.contains("."))
                nom += ".xlsx";
        } else {
            return;
        }

        try {

            XSSFWorkbook wb = new XSSFWorkbook();

            // Créer un workbook
            Sheet sheet = wb.createSheet("Classement général");

            // Initialiser le style du titre 1
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

            // Initialiser le style en-tête 1
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

            // Initialiser le style sans couleur
            final CellStyle style3 = wb.createCellStyle();
            style3.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // alignement
            style3.setAlignment(HorizontalAlignment.CENTER);
            style3.setVerticalAlignment(VerticalAlignment.CENTER);
            // bordure
            style3.setBorderLeft(BorderStyle.THIN);
            style3.setBorderRight(BorderStyle.THIN);
            style3.setBorderTop(BorderStyle.THIN);
            style3.setBorderBottom(BorderStyle.THIN);

            // rouge //
            // Initialiser le style du titre 2
            final CellStyle style4 = wb.createCellStyle();
            // couleur de fond
            style4.setFillForegroundColor(IndexedColors.RED.getIndex());
            style4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // alignement
            style4.setAlignment(HorizontalAlignment.CENTER);
            style4.setVerticalAlignment(VerticalAlignment.CENTER);
            // bordure
            style4.setBorderLeft(BorderStyle.THIN);
            style4.setBorderRight(BorderStyle.THIN);
            style4.setBorderTop(BorderStyle.THIN);
            style4.setBorderBottom(BorderStyle.THIN);
            // police
            style4.setFont(font);

            // Initialiser le style en-tête 2
            final CellStyle style5 = wb.createCellStyle();
            style5.setFillForegroundColor(IndexedColors.RED.getIndex());
            style5.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // alignement
            style5.setAlignment(HorizontalAlignment.CENTER);
            style5.setVerticalAlignment(VerticalAlignment.CENTER);
            // police
            style5.setFont(font2);
            // bodure
            style5.setBorderLeft(BorderStyle.THIN);
            style5.setBorderRight(BorderStyle.THIN);
            style5.setBorderTop(BorderStyle.THIN);
            style5.setBorderBottom(BorderStyle.THIN);

            // bleu //
            // Initialiser le style du titre 3
            final CellStyle style7 = wb.createCellStyle();
            // couleur de fond
            style7.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            style7.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // alignement
            style7.setAlignment(HorizontalAlignment.CENTER);
            style7.setVerticalAlignment(VerticalAlignment.CENTER);
            // bordure
            style7.setBorderLeft(BorderStyle.THIN);
            style7.setBorderRight(BorderStyle.THIN);
            style7.setBorderTop(BorderStyle.THIN);
            style7.setBorderBottom(BorderStyle.THIN);
            // police
            style7.setFont(font);

            // Initialiser le style en-tête 3
            final CellStyle style8 = wb.createCellStyle();
            style8.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            style8.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // alignement
            style8.setAlignment(HorizontalAlignment.CENTER);
            style8.setVerticalAlignment(VerticalAlignment.CENTER);
            // police
            style5.setFont(font2);
            // bodure
            style8.setBorderLeft(BorderStyle.THIN);
            style8.setBorderRight(BorderStyle.THIN);
            style8.setBorderTop(BorderStyle.THIN);
            style8.setBorderBottom(BorderStyle.THIN);

            ////////////////////////////////////
            ///FEUILLE 1 : CLASSEMENT GENERAL///
            ////////////////////////////////////

            int nbCol = 9;
            // Fusionner la cellules des 3 première lignes
            CellRangeAddress cra = new CellRangeAddress(0, 2, 0, nbCol - 1);
            sheet.addMergedRegion(cra);

            // Ecrire les titre de la feuille
            Row row = sheet.createRow(0);
            row.createCell(0);
            row.getCell(0).setCellValue("CLASSEMENT GENERAL");
            row.getCell(0).setCellStyle(style1);
            row.createCell(nbCol - 1);

            // Tracer la bordure droite sur la  première case
            row.createCell(nbCol - 1);
            row.getCell(nbCol - 1).setCellStyle(style1);
            row = sheet.createRow(1);
            row.createCell(nbCol - 1);
            row.getCell(nbCol - 1).setCellStyle(style1);
            row = sheet.createRow(2);
            row.createCell(nbCol - 1);
            row.getCell(nbCol - 1).setCellStyle(style1);

            int i = 3;
            // Ecrire les en-têtes
            row = sheet.createRow(i++);
            row.createCell(0).setCellValue("Lettre");
            row.createCell(1).setCellValue("N°");
            row.createCell(2).setCellValue("Nom");
            row.createCell(3).setCellValue("Prénom");
            row.createCell(4).setCellValue("N° Licence");
            row.createCell(5).setCellValue("N° Club");
            row.createCell(6).setCellValue("Club");
            row.createCell(7).setCellValue("Classement");
            row.createCell(8).setCellValue("Points");

            // Appliquer le style sur les en-têtes
            for (int ii = 0; ii < nbCol; ii++) {
                row.getCell(ii).setCellStyle(style2);
            }

            int k = 1;
            int nbPoints = 0;
           /* // Remplir le fichier avec les jeunes de la couleur passée en paramèters
            classement = new TreeSet<>(Comparator.comparingInt(Jeune::getPoints));

            // calculer le classement
            for (Jeune j : this.lesInscrits)
                if (j.getNiveau() == niveau)
                    classement.add(j);
            */
            // ecrire le classement
            int c = 0;
            for (Jeune j : classement.get(niveau)) {
                row = sheet.createRow(i);
                i++;
                c++;
                if (nbPoints != j.getPoints())
                    k = c;
                nbPoints = j.getPoints();

                row.createCell(0).setCellValue(j.getClub().toString());
                row.createCell(1).setCellValue(j.getNumero());
                row.createCell(2).setCellValue(j.getNom());
                row.createCell(3).setCellValue(j.getPrenom());
                row.createCell(4).setCellValue(j.getNumLicence());
                row.createCell(5).setCellValue(j.getClub().getNum());
                row.createCell(6).setCellValue(j.getClub().getNom());
                row.createCell(7).setCellValue(k);
                row.createCell(8).setCellValue(j.getPoints());

                for (int ind = 0; ind < nbCol; ind++) {
                    row.getCell(ind).setCellStyle(style3);
                }
            }

            // Ajuster la taille des colonnes
            for (int ind = 0; ind < nbCol - 1; ind++) {
                sheet.autoSizeColumn(ind, true);
            }

            ///////////////////////////////////
            ///FEUILLE 2 : CLASSEMENT FILLES///
            ///////////////////////////////////

            sheet = wb.createSheet("Classement filles");
            nbCol = 9;
            // Fusionner la cellules des 3 première lignes
            cra = new CellRangeAddress(0, 2, 0, nbCol - 1);
            sheet.addMergedRegion(cra);

            // Ecrire les titre de la feuille
            row = sheet.createRow(0);
            row.createCell(0);
            row.getCell(0).setCellValue("CLASSEMENT FILLES");
            row.getCell(0).setCellStyle(style4);
            row.createCell(nbCol - 1);

            // Tracer la bordure droite sur la  première case
            row.createCell(nbCol - 1);
            row.getCell(nbCol - 1).setCellStyle(style4);
            row = sheet.createRow(1);
            row.createCell(nbCol - 1);
            row.getCell(nbCol - 1).setCellStyle(style4);
            row = sheet.createRow(2);
            row.createCell(nbCol - 1);
            row.getCell(nbCol - 1).setCellStyle(style4);

            i = 3;
            // Ecrire les en-têtes
            row = sheet.createRow(i++);
            row.createCell(0).setCellValue("Lettre");
            row.createCell(1).setCellValue("N°");
            row.createCell(2).setCellValue("Nom");
            row.createCell(3).setCellValue("Prénom");
            row.createCell(4).setCellValue("N° Licence");
            row.createCell(5).setCellValue("N° Club");
            row.createCell(6).setCellValue("Club");
            row.createCell(7).setCellValue("Classement");
            row.createCell(8).setCellValue("Points");

            // Appliquer le style sur les en-têtes
            for (int ii = 0; ii < nbCol; ii++) {
                row.getCell(ii).setCellStyle(style5);
            }

            k = 1;
            nbPoints = 0;

            // calculer le classement
            c = 0;
            for (Jeune j : classement.get(niveau)) {
                row = sheet.createRow(i);
                if (j.getNiveau() == niveau && j.getSexe() == 'F') {
                    i++;
                    c++;
                    if (nbPoints != j.getPoints())
                        k = c;
                    nbPoints = j.getPoints();

                    row.createCell(0).setCellValue(j.getClub().toString());
                    row.createCell(1).setCellValue(j.getNumero());
                    row.createCell(2).setCellValue(j.getNom());
                    row.createCell(3).setCellValue(j.getPrenom());
                    row.createCell(4).setCellValue(j.getNumLicence());
                    row.createCell(5).setCellValue(j.getClub().getNum());
                    row.createCell(6).setCellValue(j.getClub().getNom());
                    row.createCell(7).setCellValue(k);
                    row.createCell(8).setCellValue(j.getPoints());

                    for (int ind = 0; ind < nbCol; ind++) {
                        row.getCell(ind).setCellStyle(style3);
                    }
                }
            }

            // Ajuster la taille des colonnes
            for (int ind = 0; ind < nbCol - 1; ind++) {
                sheet.autoSizeColumn(ind, true);
            }

            ////////////////////////////////////
            ///FEUILLE 2 : CLASSEMENT GARCONS///
            ////////////////////////////////////

            sheet = wb.createSheet("Classement garçons");
            nbCol = 9;
            // Fusionner la cellules des 3 première lignes
            cra = new CellRangeAddress(0, 2, 0, nbCol - 1);
            sheet.addMergedRegion(cra);

            // Ecrire les titre de la feuille
            row = sheet.createRow(0);
            row.createCell(0);
            row.getCell(0).setCellValue("CLASSEMENT GARCONS");
            row.getCell(0).setCellStyle(style7);
            row.createCell(nbCol - 1);

            // Tracer la bordure droite sur la  première case
            row.createCell(nbCol - 1);
            row.getCell(nbCol - 1).setCellStyle(style7);
            row = sheet.createRow(1);
            row.createCell(nbCol - 1);
            row.getCell(nbCol - 1).setCellStyle(style7);
            row = sheet.createRow(2);
            row.createCell(nbCol - 1);
            row.getCell(nbCol - 1).setCellStyle(style7);

            i = 3;
            // Ecrire les en-têtes
            row = sheet.createRow(i++);
            row.createCell(0).setCellValue("Lettre");
            row.createCell(1).setCellValue("N°");
            row.createCell(2).setCellValue("Nom");
            row.createCell(3).setCellValue("Prénom");
            row.createCell(4).setCellValue("N° Licence");
            row.createCell(5).setCellValue("N° Club");
            row.createCell(6).setCellValue("Club");
            row.createCell(7).setCellValue("Classement");
            row.createCell(8).setCellValue("Points");

            // Appliquer le style sur les en-têtes
            for (int ii = 0; ii < nbCol; ii++) {
                row.getCell(ii).setCellStyle(style8);
            }

            k = 1;
            nbPoints = 0;

            c = 0;
            for (Jeune j : classement.get(niveau)) {
                row = sheet.createRow(i);
                if (j.getNiveau() == niveau && j.getSexe() == 'G') {
                    i++;
                    c++;
                    if (nbPoints != j.getPoints())
                        k = c;
                    nbPoints = j.getPoints();

                    row.createCell(0).setCellValue(j.getClub().toString());
                    row.createCell(1).setCellValue(j.getNumero());
                    row.createCell(2).setCellValue(j.getNom());
                    row.createCell(3).setCellValue(j.getPrenom());
                    row.createCell(4).setCellValue(j.getNumLicence());
                    row.createCell(5).setCellValue(j.getClub().getNum());
                    row.createCell(6).setCellValue(j.getClub().getNom());
                    row.createCell(7).setCellValue(k);
                    row.createCell(8).setCellValue(j.getPoints());

                    for (int ind = 0; ind < nbCol; ind++) {
                        row.getCell(ind).setCellStyle(style3);
                    }
                }
            }

            // Ajuster la taille des colonnes
            for (int ind = 0; ind < nbCol - 1; ind++) {
                sheet.autoSizeColumn(ind, true);
            }

            FileOutputStream fos = new FileOutputStream(new File(nom));
            wb.write(fos);
            fos.close();
            wb.close();


            JButton button = new JButton("Export réussi");
            button.setBackground(Color.GREEN);
            PopupFactory factory = PopupFactory.getSharedInstance();
            final Popup popup = factory.getPopup(this.fp, button,
                    (int) this.fp.getLocation().getX() + this.fp.getWidth() / 2,
                    (int) this.fp.getLocation().getY() + this.fp.getHeight() / 2);
            popup.show();
            ActionListener hider = ((ActionEvent e) -> popup.hide());
            // Masquer le pop up après 3 seconces
            Timer timer = new Timer(3000, hider);
            timer.start();


        } catch (FileNotFoundException e) {
            javax.swing.JOptionPane.showMessageDialog(this.getFp(),
                    "Erreur dans l'exportation du classement des " + niveau + ". \n S'il est ouvert dans excel, le fermer et réessayer.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ////////////////
    ///CHARGEMENT///
    ////////////////

    /**
     * Demander quels fichiers sont à charger et les charger.
     */
    public void chargerFichierInscription() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("fichier excel", "xls", "xlsx", "xlsm", "xltx", "xlt", "xltm", "xla", "xlam"));
        choisirDossier(chooser);

        chooser.setMultiSelectionEnabled(true);

        // Si validation du fichier
        if (chooser.showDialog(chooser, "Ouvrir") == 0) {
            File files[] = chooser.getSelectedFiles();

            for (int ind = 0; ind < files.length; ind++) {
                File f = files[ind];
                inscription(f);
            }

            this.affichage();
            this.critEnreg = false;
        }
    }

    /**
     * Charger les inscrits à un critérium.
     **/
    public void chargerCrit() {
        // demander le fichier à ouvrir
        JFileChooser chooser = new JFileChooser();
        choisirDossier(chooser);
        chooser.setFileFilter(new FileNameExtensionFilter("fichier excel", "xls", "xlsx", "xlsm", "xltx", "xlt", "xltm", "xla", "xlam"));

        File f;
        // si validation de l'ouverture
        if (chooser.showDialog(chooser, "Ouvrir") == 0)
            f = chooser.getSelectedFile();
        else
            return;

        // enregistrer le nom du fichier
        this.nomFichierCrit = f.getName();

        Workbook wb;

        // Créer le fichier et la feuille de calcul
        try {
            wb = WorkbookFactory.create(f);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this.getFp(),
                    "Erreur dans l'ouverture du fichier des licenciés. \n S'il est ouvert dans excel, le fermer et réessayer.");
            return;
        }

        // Charger les inscriptions
        inscription(f);

        // Charger les résultats déjà saisis
        Sheet sheet = wb.getSheet("Résultats");

        // Initialiser les indices et ligne
        int i = 5;
        Row row = sheet.getRow(i++);

        while (row != null && row.getCell(0) != null && !row.getCell(0).getStringCellValue().equals("")) {
            // Récupérer les inscrits
            String id = row.getCell(0).getStringCellValue();

            // Récupérer le jeune avec son numéro
            Jeune je = this.recupJeune(id);

            // Récupérer la fiche du jeune
            FicheJeune fj = je.getFiche();

            // Maj de la fiche du jeune
            fj.invaliderModif();

            // Récupérer les réponses dans le fichier
            recupReponses(je, row);

            // Création et maj de la fiche score du jeune
            FicheScore fs = je.getFiche2();
            fs.invaliderModif();

            row = sheet.getRow(i++);
        }

        // Mettre à jour les paramètres du critérium
        sheet = wb.getSheet("Paramètres");

        int ligne = 2;

        // Récupérer le nombre de balises
        this.nbBalise = (int) sheet.getRow(ligne++).getCell(1).getNumericCellValue();

        // Récupérer le nombre de mémo
        this.nbMemo = (int) sheet.getRow(ligne++).getCell(1).getNumericCellValue();

        // Récupérer le nombre de segments
        this.nbSegment = (int) sheet.getRow(ligne++).getCell(1).getNumericCellValue();

        // Récupérer le nombre de balises d'orientation
        this.nbOrientation = (int) sheet.getRow(ligne++).getCell(1).getNumericCellValue();

        // Récupérer le nombre de circuits d'orientation
        this.nbCircuit = (int) sheet.getRow(ligne++).getCell(1).getNumericCellValue();

        ligne++;
        // Récupérer les points pour une balise trouvée
        this.gains.put("baliseTrouvee", (int) sheet.getRow(ligne++).getCell(2).getNumericCellValue());

        // Récupérer les points pour une réponse correcte à une balise
        this.gains.put("baliseCorrecte", (int) sheet.getRow(ligne++).getCell(2).getNumericCellValue());

        // Récupérer les points pour un mémo trouvé
        this.gains.put("memoTrouve", (int) sheet.getRow(ligne++).getCell(2).getNumericCellValue());

        // Récupérer les points pour une question correcte à un mémo
        this.gains.put("memoCorrect", (int) sheet.getRow(ligne++).getCell(2).getNumericCellValue());

        // Récupérer les points pour une question correcte à un mémo
        this.gains.put("orientation", (int) sheet.getRow(ligne++).getCell(2).getNumericCellValue());

        // Récupérer les points pour une zone de manabilité réussie
        this.gains.put("maniabilite", (int) sheet.getRow(ligne++).getCell(2).getNumericCellValue());

        // Récupérer les points pour la question mécanique
        this.gains.put("mecanique", (int) sheet.getRow(ligne++).getCell(2).getNumericCellValue());

        // Récupérer les points pour la trousse de mécanique
        // Le dérive chaîne
        this.gains.put("chaine", (int) sheet.getRow(ligne++).getCell(3).getNumericCellValue());

        // Les câbles
        this.gains.put("cables", (int) sheet.getRow(ligne++).getCell(3).getNumericCellValue());

        // Les clés
        this.gains.put("clefs", (int) sheet.getRow(ligne++).getCell(3).getNumericCellValue());

        // Le nécéssaire de réparation
        this.gains.put("reparation", (int) sheet.getRow(ligne++).getCell(3).getNumericCellValue());

        ligne++;

        int lig = ligne;
        // Pour chaque niveau
        int col = 2;
        for (Niveau niv : Niveau.values()) {
            ligne = lig;
            this.reponses.put(niv, new Reponses());

            // Récupérer les réponses aux mémos orientations
            for (int ind = 0; ind < nbMemo; ind++) {
                this.reponses.get(niv).put("memo" + ind,
                        sheet.getRow(ligne++).getCell(col).getStringCellValue());
            }

            // Récupérer les réponses aux balises
            for (int ind = 0; ind < nbBalise; ind++) {
                this.reponses.get(niv).put("balise" + ind,
                        sheet.getRow(ligne++).getCell(col).getStringCellValue());
            }

            // Récupérer les solutions des circuits d'orientation
            for (int ind = 1; ind <= nbCircuit; ind++) {
                for (int ii = 1; ii <= this.nbOrientation; ii++) {
                    try {
                        this.reponses.get(niv).put("orientation" + ind + "_" + ii, "" + sheet.getRow(ligne).getCell(col).getStringCellValue());
                    } catch (IllegalStateException e) {
                        this.reponses.get(niv).put("orientation" + ind + "_" + ii, "" + sheet.getRow(ligne).getCell(col).getNumericCellValue());
                    }
                    ligne++;
                }
            }

            col++;
        }
        // Créer des fiches avec les bons nombre de mémo / balises
        this.ficheReponse = new HashMap<>();
        for (Niveau niv : Niveau.values()) {
            FicheReponse fr = new FicheReponse(this, niv);
            this.ficheReponse.put(niv, fr);
            this.fp.getDesktop().add(fr);
            fr.invaliderModif();
        }
        this.ficheGains = new FicheGains(this);

        this.fp.getDesktop().add(this.ficheGains);

        // Remplir les fiches de points
        this.ficheGains.invaliderModif();

        // Mettre à jour le nombre des jeunes sur chaque circuit
        for (Jeune je : this.lesInscrits) {
            boolean finiMemo = true;
            boolean baliseFinie = true;

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

            // Afficher la grille des licenciés
            this.affichage();

            JButton button = new JButton("Chargement réussi");
            button.setBackground(Color.GREEN);
            PopupFactory factory = PopupFactory.getSharedInstance();
            final Popup popup = factory.getPopup(this.fp, button,
                    (int) this.fp.getLocation().getX() + this.fp.getWidth() / 2,
                    (int) this.fp.getLocation().getY() + this.fp.getHeight() / 2);
            popup.show();
            ActionListener hider = ((ActionEvent e) -> popup.hide());

            // Masquer le pop up
            Timer timer = new Timer(1500, hider);
            timer.start();
        }
    }

    /**
     * Récupérer les jeunes inscrits dans le fichier.
     *
     * @param f Le fichier d'inscription d'un club
     */
    public void inscription(File f) {
        this.dossier = f.getAbsolutePath();
        this.nomFichierCrit = f.getName();
        Workbook wb;

        String lettre = "";
        try {
            // Créer le fichier et la feuille de calcul
            wb = WorkbookFactory.create(f);

            Sheet sheet = wb.getSheet("Inscriptions");

            int index = 4;
            Row row = sheet.getRow(index++);

            while (row != null && !row.getCell(0).getStringCellValue().equals("")) {
                lettre = row.getCell(0).getStringCellValue();
                int numero = (int) row.getCell(1).getNumericCellValue();
                //	int equipe = (int) row.getCell(3).getNumericCellValue();
                String nom = row.getCell(4).getStringCellValue();
                String prenom = row.getCell(5).getStringCellValue();
                int licence = (int) row.getCell(6).getNumericCellValue();
                Niveau niveau = Niveau.get(row.getCell(12).getStringCellValue());
                char sexe = row.getCell(13).getStringCellValue().charAt(0);
                Jeune j;
                FicheJeune fj = new FicheJeune(this);
                FicheScore fs = new FicheScore(this);
                try {
                    int naissance = (int) row.getCell(9).getNumericCellValue();
                    j = new Jeune(nom, prenom, recupClub(lettre), numero,
                            convertir(naissance), licence, niveau, sexe, fj, fs);
                } catch (Exception e) {
                    String naissance = row.getCell(9).getStringCellValue();
                    j = new Jeune(nom, prenom, recupClub(lettre), numero,
                            convertir(naissance), licence, niveau, sexe, fj, fs);
                }

                fj.setTitle(j.toString());
                fj.setLicencie(j);
                fj.invaliderModif();

                row = sheet.getRow(index++);

                // Initialiser les réponses
                j.initialiserReponses(this.nbBalise, this.nbMemo, this.nbOrientation);

                // Maj de la fiche score du jeune
                fs.setLicencie(j);
                fs.setTitle(j.toString());

                j.setFiche2(fs);

                this.ajouterInscrit(j);

                this.fp.getDesktop().add(fj);
                fj.hide();
            }

        } catch (ClubInconnuException e) {
            javax.swing.JOptionPane.showMessageDialog(this.getFp(),
                    "Le club avec la lettre " + lettre + " est inconnu.");
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this.getFp(),
                    "Erreur dans l'ouverture du fichier des licenciés. \n S'il est ouvert dans excel, le fermer et réessayer.");
        }
    }

    /////////////////////
    ///ORDRE DE DEPART///
    /////////////////////

    /**
     * Demander l'ordre de départ de quel niveau ?
     */
    public void demarrer() {
        JInternalFrame jif = new JInternalFrame("Démarrer", false, true, false, true);

        // Mise en forme de la fenetre
        jif.getContentPane().setLayout(new BorderLayout());

        // Check box
        JRadioButton checkVert = new JRadioButton("Vert");
        JRadioButton checkBleu = new JRadioButton("Bleu");
        JRadioButton checkRouge = new JRadioButton("Rouge");
        JRadioButton checkNoir = new JRadioButton("Noir");

        // Bouton
        JButton btnValider = new JButton("Valider");
        btnValider.addActionListener(new ActionValiderDemarrer(checkVert, checkBleu, checkRouge, checkNoir, this, jif));

        // Le panel des checkBox
        JPanel panel1 = new JPanel(new FlowLayout());
        // Le panel des boutons
        JPanel panel2 = new JPanel(new FlowLayout());

        // Ajouter les JRadioButton
        panel1.add(checkVert);
        panel1.add(checkBleu);
        panel1.add(checkRouge);
        panel1.add(checkNoir);

        // Ajouter les boutons
        panel2.add(btnValider);

        // Ajouter les panels à la fenetre
        jif.getContentPane().add(panel1, BorderLayout.CENTER);
        jif.getContentPane().add(panel2, BorderLayout.SOUTH);

        // Ajouter la fenetre à la fenetre prinicpale
        this.fp.getDesktop().add(jif);
        jif.setVisible(true);
        jif.pack();
    }

    /**
     * Donner l'ordre de départ d'un niveau.
     *
     * @param niveau Le niveau dont l'ordre de départ est demandé
     */
    public void demarrer(Niveau niveau) {
        // Classer les jeunes sans que deux jeunes du même club ne se suivent
        ArrayList<Jeune> ordre = this.ordreDepart(niveau);

        // Afficher la liste des jeunes dans l'ordre de départ
        this.fp.afficherOrdre(ordre, niveau);

        // Afficher une fiche pour renseigner le numéro de son parcours d'orientation
        new FicheDepart(ordre, this);
        ordre.get(0).getFiche2().setVisible(true);
    }

    /////////////////////////////////////
    ///GERER LES BARRES DE PROGRESSION///
    /////////////////////////////////////

    /**
     * Signaler qu'un jeune est revenu des mémos.
     *
     * @param j Le jeune qui est revenu des mémos
     */
    public void arriveMemo(Jeune j) {
        if (!this.revenuMemo.contains(j))
            this.revenuMemo.add(j);
        majBarreMemo(j.getNiveau());
    }

    /**
     * Mettre à jour la barre de progression des mémos.
     *
     * @param niveau La couleur de la barre à mettre à jour
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
        this.nombreArriveMemo.setText(nb + "/" + total);
        this.barreMemo.setMaximum(total);
        this.fp.repaint();
    }

    /**
     * Signaler qu'un jeune est revenu des balises.
     *
     * @param j Le jeune qui est revenu des balises
     */
    public void arriveBalise(Jeune j) {
        if (!this.revenuBalises.contains(j))
            this.revenuBalises.add(j);
        majBarreBalise(j.getNiveau());
    }

    /**
     * Mettre à jour la barre de progression.
     *
     * @param niveau La couleur de la barre de progression
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
        this.nombreArriveBalise.setText(nb + "/" + total);
        this.barreBalise.setMaximum(total);
        this.fp.repaint();
    }

    //////////////////////
    ///METHODES PRIVEES///
    //////////////////////

    /**
     * Calculer un ordre de départ sans que deuxjeunes du même club ne se suivent.
     *
     * @param niveau Le niveau dont on veut l'ordre de départ
     */
    private ArrayList<Jeune> ordreDepart(Niveau niveau) {
        ArrayList<Jeune> ordre = new ArrayList<>();
        ArrayList<Jeune> numeros = new ArrayList<>();

        // Initialiser la listes des jeunes du bon niveau
        for (Jeune j : this.lesInscrits)
            if (j.getNiveau() == niveau)
                numeros.add(j);

        Club club = Club.A;
        Club club2 = Club.A;
        Club club3 = Club.A;
        // Tant qu'il reste des jeunes à ajouter
        while (numeros.size() > 0) {
            // Choisir un indice au hasars dans la liste
            int max = numeros.size();
            int ind = (int) (Math.random() * max);

            // Récupérer le jeune correspond à cet indice
            Jeune j = numeros.get(ind);

            // Copier la liste des numéros non encore ajoutés
            ArrayList<Jeune> numeros2 = new ArrayList<>(numeros);

            // Choisir un nouveau numéro jusqu'à ce qu'il convienne
            while (j.getClub() == club && numeros2.size() > 0) {
                numeros2.remove(ind);
                if (numeros2.size() > 0) {
                    ind = (int) (Math.random() * numeros2.size());
                    j = numeros2.get(ind);
                }
            }

            // Si aucun numéro ne convient
            if (numeros2.size() == 0) {
                // Récupérer les clubs des deux premiers de la liste de départ
                int indDecalage = 0;
                Club c1 = ordre.get(0).getClub();
                Club c2 = null;

                // Tant qu'on ne peut pas insérer le jeune en traitement
                while (c1 == j.getClub() || c2 == j.getClub()) {
                    c1 = ordre.get(indDecalage + 1).getClub();
                    c2 = ordre.get(indDecalage + 2).getClub();

                    // Si on n'a pas trouvé de places pour l'ajouter
                    // Tous les jeunes non encore ajoutés sont du même club que le dernier ajouté
                    // Un club est trop représenté pour respecter les conditions
                    if (indDecalage == ordre.size() - 3) {
                        // On ajoute tous les jeunes à la fin de la liste
                        ordre.addAll(numeros);

                        // on renvoie la liste
                        return ordre;
                    }
                    indDecalage++;
                }

                // On a trouvé une place pour le jeune
                // Décaler les jeunes suivants
                ordre.add(j);
                for (int indBoucle = ordre.size() - 1; indBoucle > indDecalage; indBoucle--) {
                    ordre.set(indBoucle, ordre.get(indBoucle - 1));
                }

                // Insérer le jeune
                ordre.set(indDecalage, j);

            } else {
                // sinon un jeune convient, on l'ajoute à la fin de la liste
                ordre.add(j);
            }

            // On met à jour le club du dernier jeune ajouté
            club = club2;
            club2 = club3;
            club3 = j.getClub();

            // On retire l'indice du jeune ajouté de la liste des indices
            numeros.remove(j);
        }

        // On renvoie la liste
        return ordre;
    }

    /**
     * Décaler les éléments d'une liste.
     *
     * @param liste  La liste
     * @param indice L'indice du premier élément à décaler
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
     *
     * @param j   Le jeune dont ce sont les réponses
     * @param row La ligne du fichier excel qui contient les réponses
     */
    private void recupReponses(Jeune j, Row row) {
        int colonne = 2;
        for (int i = 0; i < this.nbMemo; i++) {
            String rep = row.getCell(colonne++).getStringCellValue();
            j.getLesReponses().put("memo" + i, rep);
        }

        for (int i = 0; i < this.nbBalise; i++) {
            String rep = row.getCell(colonne++).getStringCellValue();
            j.getLesReponses().put("balise" + i, rep);
        }

        for (int i = 0; i < this.nbBalise; i++) {
            for (int ii = 1; ii <= this.nbSegment; ii++) {
                String rep = row.getCell(colonne++).getStringCellValue();
                j.getLesReponses().put("maniabilite" + i + "_" + ii, rep);
            }
        }

        String rep = row.getCell(colonne++).getStringCellValue();
        j.getLesReponses().put("chaine", rep);

        rep = row.getCell(colonne++).getStringCellValue();
        j.getLesReponses().put("clefs", rep);

        rep = row.getCell(colonne++).getStringCellValue();
        j.getLesReponses().put("reparation", rep);

        rep = row.getCell(colonne++).getStringCellValue();
        j.getLesReponses().put("cables", rep);

        for (int i = 0; i < this.nbOrientation; i++) {
            String reponse = row.getCell(colonne++).getStringCellValue();
            j.getLesReponses().put("orientation" + i, reponse);
        }

        int num = (int) row.getCell(colonne++).getNumericCellValue();
        j.setNumParours(num);

    }

    /**
     * Initialiser un projet.
     *
     * @param fp La fenêtre principale
     */
    private void initialiserProjet(FenetrePrincipale fp) {
        this.fp = fp;
        this.nbOrientation = 4;
        this.nbBalise = 4;
        this.nbMemo = 2;
        this.nbSegment = 2;
        this.nbCircuit = 4;
        this.reponses = new HashMap<>();
        for (Niveau niv : Niveau.values()) {
            reponses.put(niv, new Reponses());
        }
        this.classement = new HashMap<>();
        this.lesInscrits = new ArrayList<>();
        this.gains = new HashMap<>();
        this.gains.put("baliseTrouvee", 2);
        this.gains.put("baliseCorrecte", 3);
        this.gains.put("memoTrouve", 1);
        this.gains.put("memoCorrect", 2);
        this.gains.put("mecanique", 3);
        this.gains.put("maniabilite", 1);
        this.gains.put("orientation", 4);
        this.gains.put("chaine", 2);
        this.gains.put("reparation", 1);
        this.gains.put("cables", 1);
        this.gains.put("clefs", 1);

        this.ficheGains = new FicheGains(this);
        this.ficheGains.invaliderModif();

        this.ficheParametre = new FicheParam(this);
        this.ficheParametre.annulerModifier();

        fcVert = new FicheClassement(this);
        fcBleu = new FicheClassement(this);
        fcRouge = new FicheClassement(this);
        fcNoir = new FicheClassement(this);

        this.ficheReponse = new HashMap<>();
        this.critEnreg = true;
        this.barreMemo = new JProgressBar();
        this.barreBalise = new JProgressBar();
        this.revenuMemo = new ArrayList<>();
        this.revenuBalises = new ArrayList<>();
        this.nombreArriveBalise = new JLabel("");
        this.nombreArriveMemo = new JLabel("");

        this.fp.getDesktop().add(this.ficheGains);
        this.fp.getDesktop().add(this.ficheParametre);
        //	this.fp.getDesktop().add(this.ficheReponse);
    }

    /**
     * Initialiser la HashMap alphabet.
     */
    private void initialiserAlphabet() {
        alphabet.put("", -1);
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

    /**
     * Regarder si un jeune est déjà inscrit ou non.
     *
     * @param j Le jeune
     * @return Vrai s'il est déjà inscrit, faux sinon
     */
    private boolean dejaIscrit(Jeune j) {
        for (Jeune je : this.lesInscrits) {
            if (je.toString().equals(j.toString()))
                return true;
        }
        return false;
    }

    ////////////////////////
    ///METHODES PUBLIQUES///
    ////////////////////////

    /**
     * Si possible, possitionner le fileChooser dans le dossier "Documents".
     *
     * @param chooser Le FileChooser
     */
    public void choisirDossier(JFileChooser chooser) {
        String chemin = "";
        if (this.dossier != null) {
            String[] path = this.dossier.split("\\\\");
            for (String aPath : path) {
                chemin += aPath + File.separator;
                if (aPath.equals("Documents")) {
                    chooser.setCurrentDirectory(new File(chemin));
                    return;
                }
            }
        }
        chooser.setCurrentDirectory(new File("C:" + File.separator + "Users" + File.separator));
    }

    /**
     * Récupérer un jeune grâce à son numéro.
     *
     * @param numero Le numéro du jeune à récupérer
     * @return Le jeune correspondant au numéro
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

}

