package application.aeroport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import application.utils.ToolBox;

/**
 * La classe ChargerAeroport.
 * 
 * @author Killian
 * @author Farouk
 * @author Mohammed Belkhatir
 */
public class ChargerAeroport {

	/**
	 * La map.
	 */
	private Map<String, String[]> m;

	/**
	 * Le fichier sélectionné.
	 */
	private File selectedFile;

	/** Si ChargerAeroport est valide. */
	private boolean valid;

	/**
	 * Instancie ChargerAeroport.
	 *
	 * @param file le fichier
	 * 
	 * 
	 * 
	 * 
	 */
	public ChargerAeroport(File file) {
		this.selectedFile = file;
		m = new HashMap<>();
		valid = true;
		readFile();
	}

	/**
	 * Read file.
	 * 
	 * 
	 * 
	 * 
	 */
	private void readFile() {
		BufferedReader reader;
		Scanner ent;
		try {
			FileReader fr = new FileReader(selectedFile);
			reader = new BufferedReader(fr);
			ent = new Scanner(fr);

			while (ent.hasNextLine()) {
				String[] res = ent.nextLine().split(";");
				if (res.length != 10) {
					ToolBox.sendErrorMessage(
							"Erreur lors de la lecture des aéroports :\r\n Il n'y a pas le bon nombre d'informations par ligne.");
					valid = false;
					return;
				}
				m.put(res[0], res);
			}
			reader.close();
			fr.close();
			ent.close();
		} catch (IOException ex) {
			ToolBox.sendErrorMessage("Erreur lors de la lecture des aéroports :\r\n Le fichier n'existe pas.");
			valid = false;
		}
	}

	/**
	 * Récupère la map aéroport.
	 *
	 * @return la map aéroport
	 * 
	 * 
	 * 
	 * 
	 */
	public Map<String, String[]> getMapAero() {
		return m;
	}

	/**
	 * Si ChargerAeroport est valide.
	 *
	 * @return true si valide
	 * 
	 * 
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Crée les objets Aeroport à partir des données chargées.
	 *
	 * @return un tableau d'objets Aeroport
	 * 
	 * 
	 */
	public Aeroport[] creationAeroports() {
		Aeroport[] aeroports = new Aeroport[m.size()];
		int index = 0;
		for (Map.Entry<String, String[]> entry : m.entrySet()) {
			String[] data = entry.getValue();
			String code = data[0];

			double latitude = convertCoordinates(data[2], data[3], data[4], data[5]);
			double longitude = convertCoordinates(data[6], data[7], data[8], data[9]);

			// Vérification de la validité des coordonnées
			if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
				System.out.println("Coordonnées invalides pour l'aéroport: " + code);
				continue;
			}

			Aeroport aeroport = new Aeroport(code, latitude, longitude);
			aeroports[index++] = aeroport;
		}

		return aeroports;
	}

	/**
	 * Convertit les coordonnées de degrés/minutes/secondes en degrés décimaux.
	 *
	 * @param deg         degrés
	 * @param min         minutes
	 * @param sec         secondes
	 * @param orientation N, S, E, O(ou W)
	 * @return les coordonnées en degrés décimaux
	 * 
	 * 
	 * 
	 */
	private double convertCoordinates(String deg, String min, String sec, String orientation) {
		double degrees = Double.parseDouble(deg);
		double minutes = Double.parseDouble(min);
		double seconds = Double.parseDouble(sec);
		double decimal = degrees + (minutes / 60.0) + (seconds / 3600.0);
		if (orientation.matches("[SWOswo]")) {
			decimal = -decimal;
		}
		return decimal;
	}

}
