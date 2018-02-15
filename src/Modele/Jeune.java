package Modele;

import java.util.HashMap;

import Vue.FenetrePrincipale;
import Vue.FicheJeune;
import Vue.FicheScore;

public class Jeune {

	/** Le nom du jeune. */
	private String nom;

	/** Le prénom du jeune. */
	private String prenom;

	/** La fiche du jeune. */
	private FicheJeune fiche;

	/** Le nom du club du jeune. */
	private String club;

	/** Le numéro de plaque du jeune. */
	private String numero;

	/** La catégorie du jeune. */
	private String cate;

	/** Les réponses. */
	private HashMap<String, String> lesReponses;

	/** La fiche score du jeune. */
	private FicheScore fiche2;

	/** Le nombre de points. */
	private int points;

	///////////////////////
	///// CONSTRUCTEURS ///
	///////////////////////
	/**
	 * Créer un jeune à partir de son nom, son prénom, son club, son numéro, sa catégorit et ses fiches
	 * @param nom     Le nom du jeune
	 * @param prenom  Le prénom du jeune
	 * @param club    Le club du jeune
	 * @param numero  Le numéro de plaque du jeune
	 * @param cate    La catégorie du jeune (couleur)
	 * @param fiche   La fiche répertoriant les caractéristiques du jeune
	 * @param fiche2  La fiche répertoriant les scores du jeunes au critérium
	 */
	public Jeune(String nom, String prenom, String club, String numero, String cate,
			FicheJeune fiche, FicheScore fiche2) {
		this.nom = nom;
		this.prenom = prenom;
		this.club = club;
		this.numero = numero;
		this.cate = cate;
		this.fiche = fiche;
		this.fiche2 = fiche2;

		this.lesReponses = new HashMap<>();
	}

	/**
	 * Créer un jeune à partir de ses deux fiches
	 * @param fiche  La fiche répertoriant les caractéristiques du jeune
	 * @param fiche2 La fiche répertoriant les scores du jeune au critérium
	 */
	public Jeune(FicheJeune fiche, FicheScore fiche2) {
		this.nom = fiche.getNom2().getText();
		this.prenom = fiche.getPrenom2().getText();
		this.club = fiche.getClub2().getSelectedItem().toString();
		this.numero = fiche.getNumero2().getText();
		this.cate = fiche.getCate2().getSelectedItem().toString();
		this.fiche = fiche;
		this.fiche2 = fiche2;
		this.lesReponses = new HashMap<>();
	}

	////////////////////////
	///GETTERS ET SETTERS///
	////////////////////////
	/**
	 * Récupérer la fiche du jeune
	 * @return la fiche du jeune (nom, prénom etc.)
	 */
	public FicheJeune getFiche() {
		return fiche;
	}

	/**
	 * Modifier la fiche du jeune
	 * @param fiche  La nouvelle fiche du jeune
	 */
	public void setFiche(FicheJeune fiche) {
		this.fiche = fiche;
	}

	/**
	 * Récupérer le nom du jeune
	 * @return le nom du jeune
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Modifier le nom du jeune
	 * @param nom Le nouveau nom du jeune
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Récupérer le prénom du jeune
	 * @return le prénom du jeune
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Modifier le prénom du jeune
	 * @param prenom Le nouveau prénom du jeune
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Récupérer le club du jeune
	 * @return le club du jeune
	 */
	public String getClub() {
		return club;
	}

	/**
	 * Modifier le club du jeune
	 * @param club Le nouveau club du jeune
	 */
	public void setClub(String club) {
		this.club = club;
	}

	/**
	 * Récupérer le numéro de plaque du jeune
	 * @return le numéro de plaque du jeune
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Modifier le numéro de plqaque du jeune
	 * @param numero Le nouveau numéro de la plaque du jeune
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * Récupérer la catégorie du jeune (la couleur)
	 * @return la catégorie du jeune
	 */
	public String getCate() {
		return cate;
	}

	/**
	 * Modifier la catégorie du jeune
	 * @param cate La nouvelle catégorie du jeune (couleur)
	 */
	public void setCate(String cate) {
		this.cate = cate;
	}

	/**
	 * Récupérer la fiche score du jeune
	 * @return la fiche score du jeune
	 */
	public FicheScore getFiche2() {
		return fiche2;
	}

	/**
	 * Modifier la fiche score du jeune
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

	////////////////////////
	///METHODES PUBLIQUES///
	////////////////////////
	/**
	 * Transformer un jeune en String (numéro du jeune)
	 */
	public String toString() {
		String res;
		String[] l = this.getNumero().split("");
		if (l[1].equals("0")) {
			res = l[0] + l[2];
		} else {
			res = this.getNumero();
		}
		return res;
	}

	/**
	 * Mettre à jour les attributs d'un jeune en fonction des données lues dans le 
	 * fichier d'enregistrement.
	 * @param donnees Le données lues dans le fichier d'enregistrememnt
	 */
	public void setScore(String[] donnees, int nbBalises, int nbMemos) {
		for (int i = 0 ; i < nbBalises ; i++) {
			this.lesReponses.put("balise" + i, donnees[i + nbMemos + 1]);
			this.lesReponses.put("maniabilite" + i, donnees[i + nbMemos + nbBalises + 1]);
		}

		for (int i = 0 ; i < nbMemos ; i++) {
			this.lesReponses.put("memo" + i,  donnees[i + 1]);
		}

	}

	/**
	 * Comparer deux jeunes par leur numéro.
	 * @param j Le jeune à comparer
	 * @return vrai si j à un numéro plus petit
	 */
	public boolean inferieur(Jeune j) {
		return (this.numero.compareTo(j.numero) < 0);
	}

	/**
	 * Renvoyer une chaine de caracteres pour l'enregistrement.
	 * @return une chaîne de caractères avec tous les attributs d'un jeune
	 */
	public String toStringEnreg(){
		String res =  String.valueOf(this.getNom()) + "\t" +
				String.valueOf(this.getPrenom()) + "\t" +
				String.valueOf(this.getNumero()) + "\t" +
				String.valueOf(this.getClub()) + "\t" +
				String.valueOf(this.getCate()) + "\t\n";

		return res;
	}

	/**
	 * Mettre à jour le score d'un jeune.
	 * @param reponses   Les réponses attendues
	 * @param nbBalises  Le nombre de balises
	 * @param nbMemos    Le nombre de mémo
	 * @param points     Les points gagnés pour chaque épreuves
	 * @param fp         La fenêtre principale
	 */
	public void modifierPoints (HashMap<String, String> reponses, int nbBalises, 
			int nbMemos, HashMap<String, Integer> points, FenetrePrincipale fp) {

		if (points.get("baliseTrouvee") == null && nbBalises > 0) {
			javax.swing.JOptionPane.showMessageDialog(fp,
					"Veuillez donner le nombre de points gagnés en trouvant une balise.");
			return;
		}

		if (points.get("baliseCorrecte") == null && nbBalises > 0) {
			javax.swing.JOptionPane.showMessageDialog(fp,
					"Veuillez donner le nombre de points gagnés en répondant à une question balise.");
			return;
		}

		if (points.get("memoTrouve") == null  && nbMemos > 0) {
			javax.swing.JOptionPane.showMessageDialog(fp,
					"Veuillez donner le nombre de points gagnés en trouvant un mémo.");
			return;
		}

		if (points.get("memoCorrect") == null && nbMemos > 0) {
			javax.swing.JOptionPane.showMessageDialog(fp,
					"Veuillez donner le nombre de points gagnés en répondant à une question mémo.");
			return;
		}

		if (points.get("baliseTrouvee") == null && nbBalises > 0) {
			javax.swing.JOptionPane.showMessageDialog(fp,
					"Veuillez donner le nombre de points gagnés en trouvant une balise.");
			return;
		}

		// réinitialiser le score
		this.points = 0;
		
		// ajouter les points pour chaque balise
		for (int i = 0 ; i < nbBalises ; i++) {
			if (reponses.get("balise" + i).equals(this.lesReponses.get("balise" + i)))
				this.points += points.get("baliseCorrecte");

			if (!this.lesReponses.get("balise" + i).equals("XX")) 
				this.points += points.get("baliseTrouvee");

			if (this.lesReponses.get("maniabilite" + i).equals("O")) 
				this.points += points.get("maniabilite");
		}

		// ajouter les points pour chaque mémo
		for (int i = 0 ; i < nbMemos ; i++) {
			if (reponses.get("memo" + i).equals(this.lesReponses.get("memo" + i)))
				this.points += points.get("memoCorrect");

			if (!this.lesReponses.get("memo" + i).equals("XX"))
				this.points += points.get("memoTrouve");
		}

	}
}