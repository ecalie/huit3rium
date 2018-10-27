package Modele;

public class MauvaisFormatDateException extends Exception {
    public MauvaisFormatDateException() {
        super("Le format de la date doit être jj/mm/aaa");
    }
}
