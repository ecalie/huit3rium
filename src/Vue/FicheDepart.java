package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import java.awt.event.WindowEvent;

import Controleur.Depart.ActionValiderDepart;
import Modele.Jeune;
import Modele.Niveau;
import Modele.Projet;

public class FicheDepart extends JInternalFrame {

	private ArrayList<Jeune> ordre;
	private Projet projet;
	
	public FicheDepart(ArrayList<Jeune> o, Projet projet) {
		super(o.get(0).toString(), false, true, false, false);
		
		this.ordre = o;
		this.projet = projet;
		
		this.getContentPane().setLayout(new BorderLayout());
		
		// La zone de saisie du parcours d'orientation
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		
		JLabel saisie = new JLabel(o.get(0).getPrenom()  + " " + o.get(0).getNom());
		
		panel1.add(saisie);
		
		// La zone du bouton Suivant
		JPanel panel2 = new JPanel();
		
		JButton btn = new JButton("Suivant");
		btn.addActionListener(new ActionValiderDepart(this));
		
		panel2.add(btn);
		
		this.add(panel1, BorderLayout.CENTER);
		this.add(panel2, BorderLayout.SOUTH);
		
		
		this.setLocation(new Point(0, 220));
		
		this.addInternalFrameListener(new InternalFrameListener() {
			@Override
			public void internalFrameActivated(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameClosed(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				fermer();
			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {
			}

			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {
			}
		});
		
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.projet.getFp().getDesktop().add(this);
	}
	
	public void valider() {
		Niveau n = this.ordre.get(0).getNiveau();
		
		// Supprimer le jeune de la liste des prochains départs
		this.ordre.remove(0);
		
		// Afficher la liste mise à jour
		this.projet.getFp().afficherOrdre(this.ordre, n);
		
		if (this.ordre.size() > 0) {
			// Ouvrir une fiche du prochain départ
			new FicheDepart(this.ordre, this.projet);
			ordre.get(0).getFiche2().setVisible(true);
		}
		
		// Fermer la fenêtre
		super.dispose();
	}
	
	public void fermer() {
		int confirm = javax.swing.JOptionPane.showConfirmDialog(this.projet.getFp(),
				"Si vous fermer cette fenêtre, vous stopperez l'appel des jeunes pour le départ. \n Voulez-vous vraiment arrêter ?",
				"Confirmer",
				JOptionPane.OK_CANCEL_OPTION);
		
		if (confirm == JOptionPane.OK_OPTION) {
			this.projet.getFp().getOrdreDepart().setVisible(false);
			super.dispose();
		} else if (confirm == JOptionPane.CANCEL_OPTION) {
		}
	}
}
