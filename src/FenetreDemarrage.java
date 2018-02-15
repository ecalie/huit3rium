
public class FenetreDemarrage implements Runnable {

	@Override
	public void run() {
		System.out.println("image");
		new SplashScreen(5000);
	}
}
