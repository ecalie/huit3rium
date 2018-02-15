package Controleur.Administrateur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Modele.Projet;

public class ActionModifierFichierLicencies implements ActionListener {

	private Projet projet;
	
	public ActionModifierFichierLicencies(Projet projet) {
		this.projet = projet;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooser = new JFileChooser();

		// Dossier Courant
		chooser.setCurrentDirectory(new File("." + File.separator));

		if (chooser.showDialog(chooser, "Ouvrir") == 0) {
			// Récupérer le fichier d'enregistrement
			File fichier = chooser.getSelectedFile();

			String nom = fichier.getAbsolutePath();

			if (!nom.contains(".")) {
				this.projet.setNomFichierLicencie(nom + ".xls");
			} else if (nom.endsWith(".xls")){
				this.projet.setNomFichierLicencie(nom);
			} else {
				javax.swing.JOptionPane.showMessageDialog(this.projet.getFp(), 
						"Veuillez sélectionner un fichier excel (extension .xls).", 
						"Erreur",
						JOptionPane.ERROR_MESSAGE
						);
			}
			
			File config = new File("./fichierConfiguration.txt");
			try {
				config.createNewFile();
				FileWriter fw = new FileWriter(config);
				fw.write(this.projet.getNomFichierLicencie());
				fw.close();
			} catch (IOException e) {
				try {
					Projet.fwLog.write(e + "");
				} catch (IOException e1) {}
				}
		}
	}

	
}
