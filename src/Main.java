import Vue.FenetrePrincipale;

/**
 * Created by Aurélie.
 */
public class Main {
	public static void main(String[] args) {
		Thread th = new Thread(new FenetreDemarrage());
		th.run();

		new FenetrePrincipale();
	}
}
