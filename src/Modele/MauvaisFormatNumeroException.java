package Modele;

public class MauvaisFormatNumeroException extends Exception {
    public MauvaisFormatNumeroException() {
        super("Le numéro doit être un chiffre (sans la lattre du club)");
    }
}
