package Modele;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Vue.FenetrePrincipale;

public class Confirmation extends JInternalFrame implements Runnable {

	private FenetrePrincipale fp;

	public Confirmation(String texte, FenetrePrincipale fp, int ecart) {
		super("", false, false, false, false);
		this.fp = fp;

		JPanel panel = new JPanel(new FlowLayout());
		JLabel label = new JLabel(texte);
		Font font = new Font("Arial",Font.BOLD,15);
		label.setFont(font);
		
		panel.add(label);
		this.getContentPane().add(panel);
		
		panel.setBackground(Color.GREEN);
		
		this.pack();
		this.setLocation(new Point(this.fp.getWidth() - this.getWidth() - ecart,0));
		this.setVisible(true);
		this.fp.getDesktop().add(this);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {}
		this.dispose();
	}
}
