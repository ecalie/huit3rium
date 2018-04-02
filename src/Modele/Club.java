package Modele;

/**
 * Created by Aurélie on 16/08/2017.
 */
public enum Club {
	A("C S MUNICIPAL SEYNOIS", "01085"),
	B("UNION CYCLISTE PEDESTRE LONDAISE", "01129"),
	C("OPPIDUM BIKE", "02590"),
	D("CYCLO CLUB LUCOIS", "03547"),
	E("VELO RANDONNEUR CANTONAL", "07071"),
	F("VELO VERT FLAYOSCAIS", "07981"),
    G("VELO SPORT CYCLO HYEROIS", "00789"),
	H("LA VALETTE CYCLOTOURISME", "01497"),
	I("VELO CLUB SIX-FOURS", "03756"),
	J("CRO ROIS TEAM", "07866"),
	K("UFOLEP VELO CLUB FARLEDOIS", "99999"),
	L("VELO CLUB NANS LES PINS LA STE BAUME", "08035"),
	N("LA VERDIERE VELO CLUB EVASION", "06002"),
	O("ESTEREL CLUB CYCLISTE ADRETS", "04909"),
	P("AM CARNOULES", "08135"),
	Q("VELO CLUB FARLEDOIS", "04435");
	
    private String nom;
    private String num;
    
    Club(String nom, String num) {
        this.nom = nom;
        this.num = num;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getNum() {
    	return this.num;
    }
    
    public void setNum(String num) {
    	this.num = num;
    }
}
