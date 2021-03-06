package modele;

import vue.FenetrePrincipale;
import vue.FicheJeune;
import vue.FicheScore;

import java.time.LocalDate;
import java.util.HashMap;

public class Jeune {

    /**
     * Le nom du jeune.
     */
    private String nom;

    /**
     * Le prénom du jeune.
     */
    private String prenom;

    /**
     * La fiche du jeune.
     */
    private FicheJeune fiche;

    /**
     * Le nom du club du jeune.
     */
    private Club club;

    /**
     * Le numéro de plaque du jeune.
     */
    private int numero;

    /**
     * Le numéro de licence du jeune.
     */
    private int numLicence;

    /**
     * Le sexe.
     */
    private char sexe;

    /**
     * Le niveau.
     */
    private Niveau niveau;

    /**
     * La date de naissance.
     */
    private Date naissance;

    /**
     * Les réponses.
     */
    private HashMap<String, String> lesReponses;

    /**
     * Le numéro du parcours d'orientation.
     */
    private int numParours;

    /**
     * La fiche score du jeune.
     */
    private FicheScore fiche2;

    /**
     * Le nombre de points.
     */
    private int points;

    /**
     * Vrai quand tous les résultats sont renseignés.
     */
    private boolean fini;


    ///////////////////////
    ///// CONSTRUCTEURS ///
    ///////////////////////

    /**
     * Créer un jeune à partir de son nom, son prénom, son club, son numéro, sa catégorit et ses fiches
     *
     * @param nom        Le nom du jeune
     * @param prenom     Le prénom du jeune
     * @param club       Le club du jeune
     * @param numero     Le numéro de plaque du jeune
     * @param naissance  La date de naissancce du jeune
     * @param numLicence Le numéro de licence du jeune
     * @param niveau     La catégorie du jeune (couleur)
     * @param sexe       Le sexe du jeune
     * @param fiche      La fiche répertoriant les caractéristiques du jeune
     * @param fiche2     La fiche répertoriant les scores du jeunes au critérium
     */
    public Jeune(String nom, String prenom, Club club, int numero,
                 Date naissance, int numLicence, Niveau niveau, char sexe,
                 FicheJeune fiche, FicheScore fiche2) {
        this.nom = nom;
        this.prenom = prenom;
        this.club = club;
        this.numero = numero;
        this.niveau = niveau;
        this.naissance = naissance;
        this.numLicence = numLicence;
        this.sexe = sexe;
        this.fiche = fiche;
        this.fiche2 = fiche2;
        this.fini = false;

        this.lesReponses = new HashMap<>();
    }

    /**
     * Créer un jeune à partir de ses deux fiches
     *
     * @param fiche  La fiche répertoriant les caractéristiques du jeune
     * @param fiche2 La fiche répertoriant les scores du jeune au critérium
     */
    public Jeune(FicheJeune fiche, FicheScore fiche2) throws MauvaisFormatNumeroException {
        try {
            this.nom = fiche.getNom2().getText();
            this.prenom = fiche.getPrenom2().getText();
            this.club = Projet.toClub(fiche.getClub2().getSelectedItem().toString());
            this.numero = Integer.parseInt(fiche.getNumero2().getText());
            this.fiche = fiche;
            this.fiche2 = fiche2;
            this.fini = false;
            this.lesReponses = new HashMap<>();
        } catch (NumberFormatException e) {
            throw new MauvaisFormatNumeroException();
        }
    }

    ////////////////////////
    ///GETTERS ET SETTERS///
    ////////////////////////

    /**
     * Récupérer la fiche du jeune
     *
     * @return la fiche du jeune (nom, prénom etc.)
     */
    public FicheJeune getFiche() {
        return fiche;
    }

    /**
     * Modifier la fiche du jeune
     *
     * @param fiche La nouvelle fiche du jeune
     */
    public void setFiche(FicheJeune fiche) {
        this.fiche = fiche;
    }

    public int getNumParours() {
        return numParours;
    }

    public void setNumParours(int numParours) {
        this.numParours = numParours;
    }

    /**
     * Récupérer le nom du jeune
     *
     * @return le nom du jeune
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifier le nom du jeune
     *
     * @param nom Le nouveau nom du jeune
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Récupérer le prénom du jeune
     *
     * @return le prénom du jeune
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Modifier le prénom du jeune
     *
     * @param prenom Le nouveau prénom du jeune
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Récupérer le club du jeune
     *
     * @return le club du jeune
     */
    public Club getClub() {
        return club;
    }

    /**
     * Modifier le club du jeune
     *
     * @param club Le nouveau club du jeune
     */
    public void setClub(Club club) {
        this.club = club;
    }

    /**
     * Récupérer le numéro de plaque du jeune
     *
     * @return le numéro de plaque du jeune
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Modifier le numéro de plqaque du jeune
     *
     * @param numero Le nouveau numéro de la plaque du jeune
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Récupérer la fiche score du jeune
     *
     * @return la fiche score du jeune
     */
    public FicheScore getFiche2() {
        return fiche2;
    }

    /**
     * Modifier la fiche score du jeune
     *
     * @param fiche2 La nouvelle fiche score
     */
    public void setFiche2(FicheScore fiche2) {
        this.fiche2 = fiche2;
    }

    /**
     * Récupérer les zones
     */
    public HashMap<String, String> getLesReponses() {
        return this.lesReponses;
    }

    /**
     * Modifier les réponses.
     */
    public void setLesReponses(HashMap<String, String> reponses) {
        this.lesReponses = reponses;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getNumLicence() {
        return numLicence;
    }

    public void setNumLicence(int numLicence) {
        this.numLicence = numLicence;
    }

    public Date getNaissance() {
        return naissance;
    }

    public void setNaissance(Date naissance) {
        this.naissance = naissance;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public char getSexe() {
        return sexe;
    }

    public void setSexe(char sexe) {
        this.sexe = sexe;
    }

    public boolean getFini() {
        return this.fini;
    }

    public void setFini(boolean fini) {
        this.fini = fini;
    }

    ////////////////////////
    ///METHODES PUBLIQUES///
    ////////////////////////

    /**
     * Calculer l'age du jeune (qu'il a ou qu'il aura cette année).
     *
     * @return l'age
     */
    public int age() {
        int d = LocalDate.now().getDayOfMonth();
        int m = LocalDate.now().getMonthValue();
        int y = LocalDate.now().getYear();
        Date auj = new Date(y, m, d);
        return this.getNaissance().diff(auj);
    }

    /**
     * Calculer la catégorie du jeune en fonction de son age.
     *
     * @return La catégorie
     */
    public String cate() {
        if (this.age() < 13)
            return "PUPILLE";
        else if (this.age() < 15)
            return "MINIME";
        else if (this.age() < 17)
            return "CADET";
        else if (this.age() < 19)
            return "JUNIOR";
        else
            return "SENIOR";
    }

    /**
     * Transformer un jeune en String (numéro du jeune)
     */
    public String toString() {
        return this.club.toString() + this.numero;
    }

    /**
     * Comparer deux jeunes par leur numéro.
     *
     * @param j Le jeune à comparer
     * @return vrai si j à un numéro plus petit
     */
    public boolean inferieur(Jeune j) {
        if (this.club == j.getClub())
            return this.numero < j.getNumero();
        else
            return (this.club.toString().compareTo(j.getClub().toString()) == 0);
    }

    /**
     * Mettre à jour le score d'un jeune.
     *
     * @param reponses  Les réponses attendues
     * @param nbBalises Le nombre de balises
     * @param nbMemos   Le nombre de mémo
     * @param points    Les points gagnés pour chaque épreuves
     * @param fp        La fenêtre principale
     */
    public void modifierPoints(HashMap<String, String> reponses, int nbBalises, int nbOrientation,
                               int nbMemos, int nbSegments, HashMap<String, Integer> points, FenetrePrincipale fp) {

        // réinitialiser le score
        this.points = 0;

        // ajouter les points pour chaque balise
        for (int i = 0; i < nbBalises; i++) {
            if (reponses.get("balise" + i).equals(this.lesReponses.get("balise" + i)))
                this.points += points.get("baliseCorrecte");

            if (!this.lesReponses.get("balise" + i).equals("NT"))
                this.points += points.get("baliseTrouvee");

            for (int ii = 1; ii <= nbSegments; ii++)
                if (this.lesReponses.get("maniabilite" + i + "_" + ii).equals("O"))
                    this.points += points.get("maniabilite");
        }

        // ajouter les points pour chaque mémo
        for (int i = 0; i < nbMemos - 1; i++) {
            if (reponses.get("memo" + i).equals(this.lesReponses.get("memo" + i)))
                this.points += points.get("memoCorrect");

            if (!this.lesReponses.get("memo" + i).equals("NT"))
                this.points += points.get("memoTrouve");
        }

        int nb = nbMemos - 1;
        if (reponses.get("memo" + nb).equals(this.lesReponses.get("memo" + nb)))
            this.points += points.get("mecanique");
        if (!this.lesReponses.get("memo" + nb).equals("NT"))
            this.points += points.get("memoTrouve");


        // ajouter les points du parcours d'orientation
        for (int i = 0; i < nbOrientation; i++) {
            if (reponses.get("orientation" + this.numParours + "_" + (i + 1)).equals(this.lesReponses.get("orientation" + i))) {
                this.points += points.get("orientation");
            }

        }

        // ajouter les points de la trousse mécanique
        if (this.lesReponses.get("chaine").equals("O"))
            this.points += points.get("chaine");
        if (this.lesReponses.get("clefs").equals("O"))
            this.points += points.get("clefs");
        if (this.lesReponses.get("cables").equals("O"))
            this.points += points.get("cables");
        if (this.lesReponses.get("reparation").equals("O"))
            this.points += points.get("reparation");

        // Appliquer le facteur multiplicatif
        int pts = 15;
        int fact = 1;
        while (pts < this.points) {
            fact++;
            pts += 5;
        }

        this.points *= fact;
    }

    /**
     * Initialiser les réponses.
     *
     * @param nbBalise Le nombre de balises
     * @param nbMemo   Le nombre de mémo
     */
    public void initialiserReponses(int nbBalise, int nbMemo, int nbOrientation) {
        for (int i = 0; i < nbBalise; i++) {
            this.lesReponses.put("balise" + i, "");
            this.lesReponses.put("maniabilite" + i + "_1", "");
            this.lesReponses.put("maniabilite" + i + "_2", "");
        }

        for (int i = 0; i < nbMemo; i++)
            this.lesReponses.put("memo" + i, "");

        for (int i = 0; i < nbOrientation; i++)
            this.lesReponses.put("orientation" + i, "");
    }
}